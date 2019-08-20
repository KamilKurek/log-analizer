package log.analyzer.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import log.analyzer.dto.LogEventDTO;

public class DatabaseService {

    private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getCanonicalName());

    private static final String INSERT_QUERY = " insert into LOG_EVENT (id, duration, host, type, alert)"
            + " values (?, ?, ?, ?, ?)";

    public DatabaseService() throws SQLException {
        createTablesIfNotExists();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
//        return DriverManager.getConnection("jdbc:hsqldb:mem:.", "sa", "");
    }

    private void createTablesIfNotExists() throws SQLException {
        try (Connection connection = getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            try (ResultSet res = meta.getTables(null, null, "LOG_EVENT", new String[] { "TABLE" })) {
                if (!res.next()) {
                    createLogEventTable();
                }
            }
        }
    }

    private void createLogEventTable() throws SQLException {
        LOGGER.info("Creating database table LOG_EVENT");
        try (Connection connection = getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "CREATE TABLE LOG_EVENT (id VARCHAR(255) not NULL, duration BIGINT, "
                        + " host VARCHAR(255),  type VARCHAR(30), alert BOOLEAN,  PRIMARY KEY ( id ))";

                stmt.executeUpdate(sql);
            }
        }
    }

    public void addLog(LogEventDTO logEvent) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStmt = connection.prepareStatement(INSERT_QUERY)) {
                preparedStmt.setString(1, logEvent.getId());
                preparedStmt.setLong(2, logEvent.getDuration());
                preparedStmt.setString(3, logEvent.getHost());
                preparedStmt.setString(4, logEvent.getType());
                preparedStmt.setBoolean(5, logEvent.alert());
                preparedStmt.executeUpdate();
            }
        }
    }

}
