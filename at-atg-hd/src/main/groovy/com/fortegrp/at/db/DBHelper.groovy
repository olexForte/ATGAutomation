package com.fortegrp.at.db

import com.fortegrp.at.env.Environment
import com.microsoft.sqlserver.jdbc.SQLServerDriver

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Utility class that performs DB communication work
 */

public final class DBHelper {
    private static DBHelper instance = null
    private String connectionUrl
    private Connection connection
    private String SQL_CONNECTION_STRING_NAMED_INSTANCE = "jdbc:sqlserver://%s\\%s;user=%s;password=%s;database=%s"
    private String SQL_CONNECTION_STRING_PORT_NUMBER = "jdbc:sqlserver://%s:%s;user=%s;password=%s;database=%s"

    public static synchronized DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper(
                    Environment.getTestEnv().dbHost ?: Environment.getTestEnv().dbHost,
                    Environment.getTestEnv().dbPort ?: "",
                    Environment.getTestEnv().dbInstance ?: "",
                    Environment.getTestEnv().dbName,
                    Environment.getTestEnv().dbUser,
                    Environment.getTestEnv().dbPassword)
        }
        return instance
    }

    public DBHelper(String serverName, String port, String instanceName, String dbName, String username, String password) {
        if (instanceName.isEmpty()) {
            connectionUrl = String.format(SQL_CONNECTION_STRING_PORT_NUMBER, serverName, port, username, password, dbName)
        }else{
            connectionUrl = String.format(SQL_CONNECTION_STRING_NAMED_INSTANCE, serverName, instanceName, username, password, dbName)
        }
        try {
            DriverManager.registerDriver(new SQLServerDriver())
        } catch (SQLException e) {
            throw new RuntimeException("Unable to register SQL connection", e)
        }
    }

    public Connection getConnection() {
        if (connection != null)
            try {
                if (!connection.isClosed())
                    return connection
            } catch (SQLException e) {
                throw new RuntimeException("Unable get SQL connection state", e)
            }
        try {
            connection = DriverManager.getConnection(connectionUrl)
//            log.info("DB connection established.");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot establish db connection. Connection url: " + connectionUrl, e)
        }
        return connection
    }

    public void closeConnection() {
        if (connection == null) return
        try {
            connection.close()
            com.fortegrp.at.common.utils.LogHelper.logInfo("DB connection closed.")
        } catch (SQLException e) {
            com.fortegrp.at.common.utils.LogHelper.logInfo("Cannot close DB connection.")
        }
    }
}
