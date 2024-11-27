package df.base.common.extensions.hibernate.generator;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class PrefixedTableSequenceGenerator implements IdentifierGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrefixedTableSequenceGenerator.class);

    private final PrefixedId                         annotation;
    private final IdPrefixGenerator                  generator;
    private final Class<? extends IdPrefixGenerator> prefixGenerator;
    private       SqlStatementLogger                 logger;
    private       ConnectionProvider                 connectionProvider;

    public PrefixedTableSequenceGenerator(PrefixedId annotation) {
        this.annotation = annotation;
        this.prefixGenerator = annotation.prefixGenerator();
        this.generator = createIdPrefixGeneratorInstance();
    }

    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        logger = serviceRegistry.getService(SqlStatementLogger.class);
        connectionProvider = serviceRegistry.getService(ConnectionProvider.class);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        try {
            Connection       connection = connectionProvider.getConnection();
            GeneratorContext context    = GeneratorContext.create(annotation);

            generator.configure(context, object);

            ensureSequenceTableExists(context);

            LOGGER.info("Fetching next value from sequence table: {}", context.tableName());

            String            select    = getSelectQuery(context);
            PreparedStatement statement = connection.prepareStatement(select);
            long              id;

            statement.setString(1, context.sequenceName);
            logger.logStatement(select, FormatStyle.BASIC.getFormatter());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong(1);
                LOGGER.info("Current value for sequence name {}: {}", context.sequenceName, id);

                String update = getUpdateQuery(context);
                logger.logStatement(update, FormatStyle.BASIC.getFormatter());

                statement = connection.prepareStatement(update);
                statement.setLong(1, id + context.incrementBy);
                statement.setLong(2, id);
                statement.setString(3, context.sequenceName);
                statement.executeUpdate();

                LOGGER.debug("Updated next value for sequence {} to {}", context.sequenceName, id + context.incrementBy);

            } else {
                LOGGER.info("Initializing sequence {} with value {}", context.sequenceName, context.initialValue);

                String insert = getInsertQuery(context);
                logger.logStatement(insert, FormatStyle.BASIC.getFormatter());

                statement = connection.prepareStatement(insert);
                statement.setString(1, context.sequenceName);
                statement.setLong(2, context.initialValue + context.incrementBy);
                statement.executeUpdate();

                id = context.initialValue;
            }

            String prefixedId = generator.generate(id, annotation, object);

            LOGGER.info("New ID generated {} using {} generator", prefixedId, prefixGenerator.getName());

            return prefixedId;

        } catch (SQLException e) {
            throw new IdentifierGenerationException("Unable to generate ID", e);
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

    private void ensureSequenceTableExists(GeneratorContext context) throws SQLException {
        Connection       connection = connectionProvider.getConnection();
        DatabaseMetaData meta       = connection.getMetaData();
        ResultSet        tables     = meta.getTables(null, null, context.tableName(), new String[]{"TABLE"});

        if (!tables.next()) {
            String sql = "CREATE TABLE %s (%s VARCHAR(50) NOT NULL, %s BIGINT NOT NULL, PRIMARY KEY (%s))"
                    .formatted(context.tableName, context.keyColumnName, context.valueColumnName, context.keyColumnName);
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
                logger.logStatement(sql, FormatStyle.DDL.getFormatter());
                LOGGER.info("Created table {}.", context.tableName);
            }
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
