package com.Hospital_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentBookingPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private DoctorManagementPage doctorManagementPage;
    private JTextField doctorIdField;
    private JTextField patientIdField;
    private JTextField appointmentDateField;

    private JPanel inputPanel;
    private JLabel headingLabel;
    private JButton checkDoctorButton;
    private JButton bookAppointmentButton;

    public AppointmentBookingPage(Connection conn) {
        this.conn = conn;
        this.doctorManagementPage = new DoctorManagementPage(conn);

        setTitle("Appointment Booking");
        setSize(500, 400); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Heading with background color
        headingLabel = new JLabel("Check Doctor Availability", SwingConstants.CENTER);
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(0, 123, 255)); // Blue background
        headingLabel.setForeground(Color.WHITE); // White text
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(headingLabel, BorderLayout.NORTH);

        // Panel for user inputs
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Define component sizes
        Dimension textFieldSize = new Dimension(150, 25);

        JLabel doctorIdLabel = new JLabel("Enter Doctor ID:");
        doctorIdField = new JTextField();
        doctorIdField.setPreferredSize(textFieldSize);

        JLabel patientIdLabel = new JLabel("Enter Patient ID:");
        patientIdField = new JTextField();
        patientIdField.setPreferredSize(textFieldSize);

        JLabel appointmentDateLabel = new JLabel("Enter Appointment Date (YYYY-MM-DD):");
        appointmentDateField = new JTextField();
        appointmentDateField.setPreferredSize(textFieldSize);

        checkDoctorButton = new JButton("Check Availability");
        bookAppointmentButton = new JButton("Book Appointment");

        // Initially, only check availability section is visible
        bookAppointmentButton.setEnabled(false);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(doctorIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(doctorIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(patientIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(patientIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(appointmentDateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(appointmentDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(checkDoctorButton, gbc);

        gbc.gridy = 4;
        inputPanel.add(bookAppointmentButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Event listener for checking doctor availability
        checkDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    String appointmentDate = appointmentDateField.getText();

                    if (doctorManagementPage.getDoctorById(doctorId)) {
                        if (checkDoctorAvailability(doctorId, appointmentDate)) {
                            JOptionPane.showMessageDialog(null, "Doctor is available!");
                            // Enable book appointment button and update heading
                            bookAppointmentButton.setEnabled(true);
                            headingLabel.setText("Book Appointment");
                            // Disable check availability button
                            checkDoctorButton.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Doctor not available on this date.");
                            bookAppointmentButton.setEnabled(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found.");
                        bookAppointmentButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric Doctor ID.");
                }
            }
        });

        // Event listener for booking appointment
        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    int patientId = Integer.parseInt(patientIdField.getText());
                    String appointmentDate = appointmentDateField.getText();

                    if (!getPatientById(patientId)) {
                        int response = JOptionPane.showConfirmDialog(null,
                                "Patient not found. Do you want to add patient details?",
                                "Patient Not Found",
                                JOptionPane.YES_NO_OPTION);

                        if (response == JOptionPane.YES_OPTION) {
                            // Automatically open PatientForm page
                            new PatientForm(conn).setVisible(true); // Open PatientForm instead of PatientManagementPage
                        }
                        return; // Exit if patient is not found
                    }

                    if (doctorManagementPage.getDoctorById(doctorId)) {
                        if (checkDoctorAvailability(doctorId, appointmentDate)) {
                            bookAppointment(patientId, doctorId, appointmentDate);
                        } else {
                            JOptionPane.showMessageDialog(null, "Doctor not available on this date.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric IDs.");
                }
            }
        });
    }

    private boolean getPatientById(int patientId) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkDoctorAvailability(int doctorId, String appointmentDate) {
        String query = "SELECT * FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next(); // Returns true if no appointment found on that date
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void bookAppointment(int patientId, int doctorId, String appointmentDate) {
        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, appointmentDate);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Appointment successfully booked!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to book appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
