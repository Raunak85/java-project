package com.Hospital_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class DoctorManagementPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private JTable doctorTable;

    public DoctorManagementPage(Connection conn) {
        this.conn = conn;
        setTitle("Doctor Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and set up the table
        doctorTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and display doctor data
        fetchDoctorData();
    }

    private void fetchDoctorData() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Create table model and set column names
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Doctor Id");
            tableModel.addColumn("Name");
            tableModel.addColumn("Specialization");

            // Add rows to the table model
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("specialization"));
                tableModel.addRow(row);
            }

            // Set the table model to the table
            doctorTable.setModel(tableModel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
