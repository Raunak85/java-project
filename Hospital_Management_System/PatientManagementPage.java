package com.Hospital_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManagementPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private Connection conn;

    public PatientManagementPage(Connection conn) {
        this.conn = conn; // Initialize the connection

        setTitle("Patient Management");
        setSize(400, 300); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window, not the entire application
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set frame background color
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray

        // Heading Panel
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(102, 178, 255)); // Slightly darker blue
        headingPanel.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel headingLabel = new JLabel("Patient Management");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.WHITE);
        headingPanel.add(headingLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton addButton = new JButton("Add Patient");
        JButton viewButton = new JButton("View Patients");

        // Set button size, background color, and text color
        Dimension buttonSize = new Dimension(150, 40);
        addButton.setPreferredSize(buttonSize);
        addButton.setBackground(new Color(102, 204, 255)); // Light blue
        addButton.setForeground(Color.BLACK); // Text color
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        viewButton.setPreferredSize(buttonSize);
        viewButton.setBackground(new Color(102, 204, 255)); // Light blue
        viewButton.setForeground(Color.BLACK); // Text color
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(addButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(viewButton, gbc);

        // Center panel for buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(buttonPanel);

        // Add panels to frame
        add(headingPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PatientForm(conn).setVisible(true);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewPatients(conn).setVisible(true);
            }
        });
    }

    // Method to check if a patient exists by ID
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if patient exists, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
