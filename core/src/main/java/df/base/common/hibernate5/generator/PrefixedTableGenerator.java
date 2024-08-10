package df.base.common.hibernate5.generator;

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

import java.sql.*;
import java.util.Locale;
import java.util.Properties;

public class PrefixedTableGenerator implements IdentifierGenerator {

    private static final Logger LOGGER            = LoggerFactory.getLogger(PrefixedTableGenerator.class);

    private final String             tableName;
    private final String             prefixValue;
    private final String             prefixSeparator;
    private final String             numberFormat;
    private final String             sequenceName;
    private final String             keyColumnName;
    private final String             valueColumnName;
    private final int                initialValue;
    private final int                incrementBy;
    private       SqlStatementLogger logger;
    private       ConnectionProvider connectionProvider;

    public PrefixedTableGenerator(PrefixedId annotation) {
        this.tableName = annotation.tableName().toUpperCase(Locale.ROOT);
        this.prefixSeparator = annotation.prefixSeparator();
        this.prefixValue = annotation.prefixValue().toUpperCase(Locale.ROOT);
        this.numberFormat = annotation.numberFormat();
        this.sequenceName = annotation.sequenceName().toUpperCase(Locale.ROOT);
        this.keyColumnName = annotation.keyColumnName().toUpperCase(Locale.ROOT);
        this.valueColumnName = annotation.valueColumnName().toUpperCase(Locale.ROOT);
        this.initialValue = annotation.initialValue();
        this.incrementBy = annotation.incrementBy();
    }

    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        logger = serviceRegistry.getService(SqlStatementLogger.class);
        connectionProvider = serviceRegistry.getService(ConnectionProvider.class);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        try {
            Connection connection = connectionProvider.getConnection();

            ensureSequenceTableExists();

            LOGGER.info("Fetching next value from sequence table: {}", tableName);

            String            select    = getSelectQuery();
            PreparedStatement statement = connection.prepareStatement(select);
            long              id;

            statement.setString(1, sequenceName);
            logger.logStatement(select, FormatStyle.BASIC.getFormatter());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong(1);
                LOGGER.info("Current value for sequence name {}: {}", sequenceName, id);

                String update = getUpdateQuery();
                logger.logStatement(update, FormatStyle.BASIC.getFormatter());

                statement = connection.prepareStatement(update);
                statement.setLong(1, id + incrementBy);
                statement.setLong(2, id);
                statement.setString(3, sequenceName);
                statement.executeUpdate();

                LOGGER.debug("Updated next value for sequence {} to {}", sequenceName, id + incrementBy);

            } else {
                LOGGER.info("Initializing sequence {} with value {}", sequenceName, initialValue);

                String insert = getInsertQuery();
                logger.logStatement(insert, FormatStyle.BASIC.getFormatter());

                statement = connection.prepareStatement(insert);
                statement.setString(1, sequenceName);
                statement.setLong(2, initialValue + incrementBy);
                statement.executeUpdate();

                id = initialValue;
            }

            String prefixedId = "%s%s%s".formatted(prefixValue, prefixSeparator, numberFormat.formatted(id));

            LOGGER.info("New ID generated {}.", prefixedId);

            return prefixedId;

        } catch (SQLException e) {
            throw new IdentifierGenerationException("Unable to generate ID", e);
        }
    }

    private void ensureSequenceTableExists() throws SQLException {
        Connection       connection = connectionProvider.getConnection();
        DatabaseMetaData meta       = connection.getMetaData();
        ResultSet        tables     = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        if (!tables.next()) {
            String sql = "CREATE TABLE %s (%s VARCHAR(50) NOT NULL, %s BIGINT NOT NULL, PRIMARY KEY (%s))"
                    .formatted(tableName, keyColumnName, valueColumnName, keyColumnName);
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
                logger.logStatement(sql, FormatStyle.DDL.getFormatter());
                LOGGER.info("Created table {}.", tableName);
            }
        }
    }

    protected String getSelectQuery() {
        return "SELECT %s FROM %s WHERE %s=?".formatted(valueColumnName, tableName, keyColumnName);
    }

    protected String getUpdateQuery() {
        return "UPDATE %s SET %s=? WHERE %s=? AND %s=?".formatted(tableName, valueColumnName, valueColumnName, keyColumnName);
    }

    protected String getInsertQuery() {
        return "INSERT INTO %s (%s, %s) VALUES (?,?)".formatted(tableName, keyColumnName, valueColumnName);
    }

}
