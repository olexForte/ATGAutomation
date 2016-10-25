package com.fortegrp.at.db

import com.microsoft.sqlserver.jdbc.SQLServerException
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

import java.sql.*

/**
 * Created by yhraichonak
 *
 */
public class BaseDataLoadHelper {
    //TODO: implement using of stored procedures
    static int CHANGES_TIMEOUT = 500
    static dbConnection = DBHelper.getInstance().getConnection()
    static dateCreated
    static dateUpdated

    static getDateCreated() {
        dateCreated = new Date(System.currentTimeMillis())
    }

    static getDateUpdated() {
        dateUpdated = new Date(System.currentTimeMillis())
    }

    public static prepareStatement(PreparedStatement ps, Object... args) throws SQLException {
        int i = 1
        for (Object arg : args) {
            try {
                if (arg instanceof Date) {
                    ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()))
                } else if (arg instanceof Integer) {
                    ps.setInt(i++, (Integer) arg)
                } else if (arg instanceof Long) {
                    ps.setLong(i++, (Long) arg)
                } else if (arg instanceof Double) {
                    ps.setDouble(i++, (Double) arg)
                } else if (arg instanceof Float) {
                    ps.setFloat(i++, (Float) arg)
                } else if (arg instanceof Boolean) {
                    ps.setBoolean(i++, (Boolean) arg)
                } else {
                    ps.setString(i++, (String) arg)
                }
            } catch (GroovyCastException ex) {
                throw new RuntimeException("Unable process SQL argument number " + i + " with value " + arg, ex)
            }
        }
    }

    public static prepareAndExecuteStatement(PreparedStatement ps, Object... args) {
        def result = null
        //Protection from deadlocks
        def attempts = 3
        prepareStatement(ps, args)
        for (int i = 0; i < attempts; i++) {
            try {
                ps.executeUpdate()
            } catch (SQLServerException ex) {
                if (ex.message.contains("deadlocked")) {
                    continue
                } else{
                    throw new RuntimeException("SQL Exception occurs:", ex)
                }
            }
            break;
        }
        try {
            result = ps.getGeneratedKeys()
        }
        catch (SQLServerException) {
        }
        result
    }

    static executeSelectQuery(stmt) {
        def result
        try {
            ResultSet rs = stmt.executeQuery()
            while (rs.next()) {
                try {
                    result = rs.getInt(1)
                } catch (NumberFormatException) {
                    result = rs.getString(1)
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during the SQL Query execution", e)
        }
        result
    }

    static executeMultiSelectQuery(PreparedStatement stmt) {
        def result = []
        try {
            ResultSet rs = stmt.executeQuery()
            result = resultSetToArrayList(rs)
        } catch (SQLException e) {
            throw new RuntimeException("Error during the SQL Query execution", e)
        }
        result
    }

    static executeMultiSelectQuery(String sqlQuery, targetDBConnection=dbConnection) {
        executeMultiSelectQuery(targetDBConnection.prepareStatement(sqlQuery))
    }


    static List resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()) {
            HashMap row = new HashMap(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    static getFirstResultFromResultSet(ResultSet result) {
        result.next()
        result.getString(1)
    }}
