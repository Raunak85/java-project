package com.Hospital_Management_System;

//LoginPage.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
 
 private static final long serialVersionUID = 1L;
 private JTextField userNameField;
 private JPasswordField passwordField;
 private JButton loginButton, registerButton;
 private Connection conn;

 public LoginPage(Connection conn) {
     this.conn = conn;

     setTitle("Login Page");
     setSize(400, 250);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new GridBagLayout());

     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(10, 10, 10, 10);
     gbc.fill = GridBagConstraints.HORIZONTAL;

     JLabel userNameLabel = new JLabel("Username:");
     gbc.gridx = 0;
     gbc.gridy = 0;
     add(userNameLabel, gbc);

     userNameField = new JTextField(20);
     gbc.gridx = 1;
     gbc.gridy = 0;
     add(userNameField, gbc);

     JLabel passwordLabel = new JLabel("Password:");
     gbc.gridx = 0;
     gbc.gridy = 1;
     add(passwordLabel, gbc);

     passwordField = new JPasswordField(20);
     gbc.gridx = 1;
     gbc.gridy = 1;
     add(passwordField, gbc);

     loginButton = new JButton("Login");
     gbc.gridx = 0;
     gbc.gridy = 2;
     add(loginButton, gbc);

     registerButton = new JButton("Register");
     gbc.gridx = 1;
     gbc.gridy = 2;
     add(registerButton, gbc);

     // Event Handlers
     loginButton.addActionListener(new LoginListener());
     registerButton.addActionListener(new RegisterListener());
 }

 private class LoginListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         String userName = userNameField.getText();
         String password = new String(passwordField.getPassword());

         if (userName.isEmpty() || password.isEmpty()) {
             JOptionPane.showMessageDialog(LoginPage.this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }

         try {
             String query = "SELECT * FROM users WHERE userName = ? AND password = ?";
             PreparedStatement pst = conn.prepareStatement(query);
             pst.setString(1, userName);
             pst.setString(2, password);
             ResultSet rs = pst.executeQuery();

             if (rs.next()) {
                 JOptionPane.showMessageDialog(LoginPage.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                 Main mainPage = new Main(conn);
                 mainPage.setVisible(true);
                 dispose();  // Close the login page
             } else {
                 JOptionPane.showMessageDialog(LoginPage.this, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
     }
 }

 private class RegisterListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         String userName = userNameField.getText();
         String password = new String(passwordField.getPassword());

         if (userName.isEmpty() || password.isEmpty()) {
             JOptionPane.showMessageDialog(LoginPage.this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }

         try {
             String query = "INSERT INTO users (userName, password) VALUES (?, ?)";
             PreparedStatement pst = conn.prepareStatement(query);
             pst.setString(1, userName);
             pst.setString(2, password);
             pst.executeUpdate();

             JOptionPane.showMessageDialog(LoginPage.this, "Registration Successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
         } catch (SQLException ex) {
             if (ex.getErrorCode() == 1062) {  // Duplicate entry for username
                 JOptionPane.showMessageDialog(LoginPage.this, "Username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
             } else {
                 ex.printStackTrace();
             }
         }
     }
 }
}

