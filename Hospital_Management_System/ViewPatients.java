package com.Hospital_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewPatients extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ViewPatients(Connection conn) {
        setTitle("View Patients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        String[] columnNames = {"ID", "Name", "Age", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        try {
            String query = "SELECT * FROM patients";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                model.addRow(new Object[]{id, name, age, gender});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(scrollPane, BorderLayout.CENTER);
    }
}
