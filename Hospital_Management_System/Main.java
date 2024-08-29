package com.Hospital_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton patientButton, doctorButton, appointmentButton;

    public Main(Connection conn) {
        setTitle("Hospital Management System");
        setSize(800, 700); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Navbar panel for date and time
        JPanel navbarPanel = new JPanel();
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        navbarPanel.setPreferredSize(new Dimension(getWidth(), 60)); // Increased height of navbar
        navbarPanel.setBackground(new Color(51, 153, 255)); // Set background color for navbar
        JLabel dateTimeLabel = new JLabel(getCurrentDateTime());
        dateTimeLabel.setForeground(Color.WHITE); // Adjust text color for better visibility
        navbarPanel.add(dateTimeLabel);

        // Heading panel
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(102, 178, 255)); // Slightly darker blue
        headingPanel.setPreferredSize(new Dimension(getWidth(), 60)); // Reduced height of heading background
        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center alignment
        JLabel headingLabel = new JLabel("Hospital Management System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.WHITE);
        headingPanel.add(headingLabel);

        // Container panel for images and buttons
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBackground(new Color(230, 230, 250)); // Light lavender background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Image and Button panels
        JPanel patientPanel = createImageWithButtonPanel("C:/Users/rauna/OneDrive/Coding 2024/JAVA/Eclipse_Java/Java_Project/src/com/Hospital_Management_System/img/patient.jpg", "Patient");
        JPanel doctorPanel = createImageWithButtonPanel("C:/Users/rauna/OneDrive/Coding 2024/JAVA/Eclipse_Java/Java_Project/src/com/Hospital_Management_System/img/doctor.jpg", "Doctor");
        JPanel appointmentPanel = createImageWithButtonPanel("C:/Users/rauna/OneDrive/Coding 2024/JAVA/Eclipse_Java/Java_Project/src/com/Hospital_Management_System/img/appointment.jpg", "Appointment");

        // Adding panels to the containerPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerPanel.add(patientPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        containerPanel.add(doctorPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        containerPanel.add(appointmentPanel, gbc);

        // Add navbar, heading, and container panels to the frame
        add(navbarPanel, BorderLayout.NORTH);
        add(headingPanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        patientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PatientManagementPage(conn).setVisible(true);
            }
        });

        doctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DoctorManagementPage(conn).setVisible(true);
            }
        });

        appointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AppointmentBookingPage(conn).setVisible(true);
            }
        });

        // Update the date and time every second
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dateTimeLabel.setText(getCurrentDateTime());
            }
        });
        timer.start();
    }

    private JPanel createImageWithButtonPanel(String imgPath, String buttonText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(230, 230, 250)); // Same background as containerPanel

        // Add image
        JLabel imageLabel = createImageLabel(imgPath, true);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add button
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(200, 50)); // Adjust button size
        button.setBackground(new Color(102, 178, 255)); // Button background color
        button.setForeground(Color.WHITE); // Button text color
        panel.add(button, BorderLayout.SOUTH);

        // Store button for later use
        switch (buttonText) {
            case "Patient":
                patientButton = button;
                break;
            case "Doctor":
                doctorButton = button;
                break;
            case "Appointment":
                appointmentButton = button;
                break;
        }

        return panel;
    }

    private JLabel createImageLabel(String imgPath, boolean adjustSize) {
        ImageIcon icon = new ImageIcon(imgPath);
        if (adjustSize) {
            // Set the size to be consistent with other images (e.g., 200x150)
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }
        JLabel label = new JLabel(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    // No need for a main method since this class is instantiated from Index.java
}
