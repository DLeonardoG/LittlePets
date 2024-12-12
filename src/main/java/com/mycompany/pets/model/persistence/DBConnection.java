
package com.mycompany.pets.model.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnection {
    private static String url = "";
    public static Connection con = null;
    private static String user = "campus2023";
    private static String password = "campus2023";
    
        public static Connection connectionDB() {

        url = "jdbc:mysql://localhost:3306/SaveYourPet";

        try {
            // Make the connection
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("Connected database " + meta.getDriverName());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return con;
    }
}
