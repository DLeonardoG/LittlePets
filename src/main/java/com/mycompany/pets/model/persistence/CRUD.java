
package com.mycompany.pets.model.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CRUD {
    
    public static Connection con;
    public static Statement stmt = null;
    public static ResultSet rs = null;

    public static Connection setConnection(Connection connection) {
        CRUD.con = connection;
        return connection;
    }

    //Get the connection
    public static Connection getConnection() {
        return con;
    }

    //Close the connection
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean setAutoCommitDB(boolean parametro) {
        try {
            con.setAutoCommit(parametro);
        } catch (SQLException sqlex) {
            System.out.println("Error configuring autoCommit " + sqlex.getMessage());
            return false;
        }
        return true;
    }

    public static void closeCon() {
        closeConnection(con);
    }

    public static boolean commitDB() {
        try {
            con.commit();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error while committing " + sqlex.getMessage());
            return false;
        }
    }

    public static boolean rollbackDB() {
        try {
            con.rollback();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Rollback error" + sqlex.getMessage());
            return false;
        }
    }

    // CRUD
    public static boolean insertDB(String sql, List<Object> parameters) {
        try (PreparedStatement prepared = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepared.setObject(i + 1, parameters.get(i));
            }
            int rowsAffected = prepared.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR IN INSERTDB: " + sqlex.getMessage());
            return false;
        }
    }

    public static boolean deleteDB(String sql, List<Object> parameters) {
        try (PreparedStatement prepared = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepared.setObject(i + 1, parameters.get(i));
            }
            int rowsAffected = prepared.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR EN BORRARDB: " + sqlex.getMessage());
            return false;
        }
    }

    public static ResultSet consultDB(String sql, List<Object> parameters) {
        try {
            PreparedStatement prepared = con.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                prepared.setObject(i + 1, parameters.get(i));
            }
            return prepared.executeQuery();
        } catch (SQLException sqlex) {
            System.out.println("ERROR IN CONSULTDB: " + sqlex.getMessage());
            return null;
        }
    }

    public static boolean updateDB(String sql, List<Object> parameters) {
        try (PreparedStatement prepared = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepared.setObject(i + 1, parameters.get(i));
            }
            int rowsAffected = prepared.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR IN UPDATEDB: " + sqlex.getMessage());
            return false;
        }
    }
    public static int queryInt(String query, List<Object> parameters) {
    int result = 0;
    try (PreparedStatement stmt = con.prepareStatement(query)) {
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }
        }
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                result = rs.getInt(1); // Devuelve el primer valor de la consulta
            }
        }
    } catch (SQLException e) {
        System.err.println("Error executing queryInt: " + e.getMessage());
    }
    return result;
}

}
