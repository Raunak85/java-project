package com.Hospital_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private JTextField nameField, ageField, genderField;

    public PatientForm(Connection conn) {
        this.conn = conn;

        setTitle("Add Patient");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch fields to fill horizontal space

        // Heading label
        JLabel headingLabel = new JLabel("Add Patient Details", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setForeground(Color.BLUE);

        // Set up labels and fields
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20); // Set preferred width
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(5); // Set preferred width
        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(10); // Set preferred width
        JButton submitButton = new JButton("Add Patient");

        // Add heading to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(headingLabel, gbc);

        // Adjust GridBagConstraints for the other components
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int age = Integer.parseInt(ageField.getText());
                    addPatient(nameField.getText(), age, genderField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PatientForm.this, "Invalid age entered. Please enter a number.");
                }
            }
        });
    }

    private void addPatient(String name, int age, String gender) {
        String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Patient Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Add Patient.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
