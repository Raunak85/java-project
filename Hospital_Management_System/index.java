package com.Hospital_Management_System;

import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class index {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn = createDatabaseConnection();
            if (conn != null) {
                // open the LoginPage first
                LoginPage loginPage = new LoginPage(conn);
                loginPage.setVisible(true);
                
            } else {
                System.out.println("Failed to connect to the database.");
            }
        });
    }

    private static Connection createDatabaseConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
