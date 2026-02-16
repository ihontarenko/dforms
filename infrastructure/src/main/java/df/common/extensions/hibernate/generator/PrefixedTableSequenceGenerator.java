package df.common.extensions.hibernate.generator;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.jdbc.spi.ResultSetReturn;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.engine.jdbc.spi.StatementPreparer;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * PrefixedTableSequenceGenerator is an implementation of the Hibernate {@link IdentifierGenerator} interface.
 * It generates unique identifiers with a customizable prefix using a sequence table.
 *
 * <p>This generator can be configured via a {@link PrefixedId} annotation, which specifies table name, sequence name,
 * column names, initial value, and increment size for the sequence.</p>
 *
 * <p>The class supports table creation for sequences, fetching the next value, and updating the sequence value
 * in the database. Additionally, it allows prefix customization through a user-defined {@link IdPrefixGenerator} implementation.</p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *   <li>Automatic sequence table creation if it does not exist.</li>
 *   <li>Support for prefix generation using a custom {@link IdPrefixGenerator} implementation.</li>
 *   <li>Configurable sequence properties such as initial value and increment size.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * public class MyEntity {
 *     @Id
 *     @PrefixedId(
 *          tableName = "SEQUENCE_TABLE",
 *          sequenceName = "ENTITY_SEQUENCE",
 *          keyColumnName = "SEQUENCE_NAME",
 *          valueColumnName = "NEXT_VAL",
 *          initialValue = 1,
 *          incrementBy = 1,
 *          prefixGenerator = CustomPrefixGenerator.class
 *      )
 *     @Column(name = "ID")
 *     private String id;
 * }
 * }</pre>
 *
 * @see IdentifierGenerator
 * @see PrefixedId
 * @see IdPrefixGenerator
 */
public class PrefixedTableSequenceGenerator implements IdentifierGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrefixedTableSequenceGenerator.class);

    private final PrefixedId                         annotation;
    private final IdPrefixGenerator                  generator;
    private final Class<? extends IdPrefixGenerator> prefixGenerator;
    private       SqlStatementLogger                 logger;

    public PrefixedTableSequenceGenerator(PrefixedId annotation) {
        this.annotation = annotation;
        this.prefixGenerator = annotation.prefixGenerator();
        this.generator = createIdPrefixGeneratorInstance();
    }

    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        logger = serviceRegistry.getService(SqlStatementLogger.class);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        try {
            long              incrementedID;
            GeneratorContext  context         = GeneratorContext.create(annotation);
            JdbcCoordinator   jdbcCoordinator = session.getJdbcCoordinator();
            StatementPreparer preparer        = jdbcCoordinator.getStatementPreparer();
            ResultSetReturn   resultSetReturn = jdbcCoordinator.getResultSetReturn();

            generator.configure(context, object);

            String            select    = getSelectQuery(context);
            ResultSet         resultSet = null;
            PreparedStatement statement;

            try {
                statement = preparer.prepareStatement(select);

                statement.setString(1, context.sequenceName);
                logger.logStatement(select, FormatStyle.BASIC.getFormatter());

                resultSet = resultSetReturn.extract(statement, select);

                LOGGER.info("Fetching next value from sequence table: '{}'", context.tableName());
            } catch (SQLGrammarException sqlGrammarException) {
                LOGGER.warn("Create sequence table '{}'", context.tableName());
                createSequenceTableExists(session, context);
            }

            if (resultSet!= null && resultSet.next()) {
                incrementedID = resultSet.getLong(1);
                LOGGER.info("Current value for sequence name {}: {}", context.sequenceName, incrementedID);

                String update = getUpdateQuery(context);
                logger.logStatement(update, FormatStyle.BASIC.getFormatter());

                statement = preparer.prepareStatement(update);
                statement.setLong(1, incrementedID + context.incrementBy);
                statement.setLong(2, incrementedID);
                statement.setString(3, context.sequenceName);
                statement.executeUpdate();

                LOGGER.debug("Updated next value for sequence {} to {}", context.sequenceName, incrementedID + context.incrementBy);

            } else {
                LOGGER.info("Initializing sequence {} with value {}", context.sequenceName, context.initialValue);

                String insert = getInsertQuery(context);
                logger.logStatement(insert, FormatStyle.BASIC.getFormatter());

                statement = preparer.prepareStatement(insert);
                statement.setString(1, context.sequenceName);
                statement.setLong(2, context.initialValue + context.incrementBy);
                statement.executeUpdate();

                incrementedID = context.initialValue;
            }

            String generatedId = generator.generate(incrementedID, annotation, object);

            LOGGER.info("NEW ID GENERATED '{}'", generatedId);

            return generatedId;

        } catch (Exception e) {
            throw new IdentifierGenerationException("ID generator failed by cause: [%s]".formatted(e.getMessage()), e);
        }
    }

    private IdPrefixGenerator createIdPrefixGeneratorInstance() {
        try {
            return prefixGenerator.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IdentifierGenerationException("Could not instantiate generator of type %s"
                    .formatted(prefixGenerator.getName()), e);
        }
    }

    protected String getSelectQuery(GeneratorContext context) {
        return "SELECT %s FROM %s WHERE %s=?"
                .formatted(context.valueColumnName, context.tableName, context.keyColumnName);
    }

    protected String getUpdateQuery(GeneratorContext context) {
        return "UPDATE %s SET %s=? WHERE %s=? AND %s=?"
                .formatted(context.tableName, context.valueColumnName, context.valueColumnName, context.keyColumnName);
    }

    protected String getInsertQuery(GeneratorContext context) {
        return "INSERT INTO %s (%s, %s) VALUES (?, ?)"
                .formatted(context.tableName, context.keyColumnName, context.valueColumnName);
    }

    protected String getCreateTableQuery(GeneratorContext context) {
        return "CREATE TABLE %s (%s VARCHAR(50) NOT NULL, %s BIGINT NOT NULL, PRIMARY KEY (%s))"
                .formatted(context.tableName, context.keyColumnName, context.valueColumnName, context.keyColumnName);
    }

    protected void createSequenceTableExists(
            SharedSessionContractImplementor session, GeneratorContext context) throws SQLException {
        String            createTableSql    = getCreateTableQuery(context);
        JdbcCoordinator   jdbcCoordinator   = session.getJdbcCoordinator();
        PreparedStatement preparedStatement = jdbcCoordinator.getStatementPreparer().prepareStatement(createTableSql);

        preparedStatement.execute();

        logger.logStatement(createTableSql, FormatStyle.DDL.getFormatter());
        LOGGER.info("Sequence table '{}' created.", context.tableName);
    }

    @SuppressWarnings({"unused"})
    public static class GeneratorContext {

        private String tableName;
        private String sequenceName;
        private String keyColumnName;
        private String valueColumnName;
        private int    initialValue;
        private int    incrementBy;

        public GeneratorContext(String tableName, String sequenceName, String keyColumnName, String valueColumnName, int initialValue, int incrementBy) {
            this.tableName = tableName;
            this.sequenceName = sequenceName;
            this.keyColumnName = keyColumnName;
            this.valueColumnName = valueColumnName;
            this.initialValue = initialValue;
            this.incrementBy = incrementBy;
        }

        public static GeneratorContext create(PrefixedId annotation) {
            return new GeneratorContext(
                    annotation.tableName().toUpperCase(), annotation.sequenceName().toUpperCase(),
                    annotation.keyColumnName().toUpperCase(), annotation.valueColumnName().toUpperCase(),
                    annotation.initialValue(), annotation.incrementBy()
            );
        }

        public String tableName() {
            return tableName;
        }

        public GeneratorContext tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public String sequenceName() {
            return sequenceName;
        }

        public GeneratorContext sequenceName(String sequenceName) {
            this.sequenceName = sequenceName;
            return this;
        }

        public String keyColumnName() {
            return keyColumnName;
        }

        public GeneratorContext keyColumnName(String keyColumnName) {
            this.keyColumnName = keyColumnName;
            return this;
        }

        public String valueColumnName() {
            return valueColumnName;
        }

        public GeneratorContext valueColumnName(String valueColumnName) {
            this.valueColumnName = valueColumnName;
            return this;
        }

        public int initialValue() {
            return initialValue;
        }

        public GeneratorContext initialValue(int initialValue) {
            this.initialValue = initialValue;
            return this;
        }

        public int incrementBy() {
            return incrementBy;
        }

        public GeneratorContext incrementBy(int incrementBy) {
            this.incrementBy = incrementBy;
            return this;
        }
    }

}
