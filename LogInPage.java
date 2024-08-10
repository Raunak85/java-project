package LoginPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class LoginProcess {
    String correctUser, correctPass;
    int attemptCount = 0;

    // Components
    Frame frame;
    Panel panel;
    Font fontStyle;
    TextField tx;
    TextField tx1;
    Label messageLabel;
    Button btn;

    LoginProcess(String user, String pass) {
        this.correctUser = user;
        this.correctPass = pass;

        fontStyle = new Font("SansSerif", Font.BOLD, 12);

        frame = new Frame("Login Page");
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        panel = new Panel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for vertical alignment
        panel.setBackground(Color.gray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // User Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        Label label1 = new Label("User Name:");
        label1.setFont(fontStyle);
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tx = new TextField(20);
        tx.setFont(fontStyle);
        panel.add(tx, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        Label label2 = new Label("Password:");
        label2.setFont(fontStyle);
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        tx1 = new TextField(20);
        tx1.setFont(fontStyle);
        tx1.setEchoChar('*'); // Hide password input
        panel.add(tx1, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btn = new Button("Login");
        btn.setFont(fontStyle);
        panel.add(btn, gbc);
        
        // Message Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        gbc.anchor = GridBagConstraints.CENTER;
        messageLabel = new Label("");
        messageLabel.setFont(fontStyle);
        panel.add(messageLabel, gbc);
       
        frame.add(panel, BorderLayout.CENTER);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VerifyUserInput();
            }
        });
       
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }
    
    void VerifyUserInput() {
        if (attemptCount >= 3) {
            messageLabel.setBackground(Color.LIGHT_GRAY);
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Maximum attempts reached.");
            return;
        }

        String userInputUserName = tx.getText();
        String userInputPass = tx1.getText();

        if (userInputUserName.equals(correctUser) && userInputPass.equals(correctPass)) {
            messageLabel.setBackground(Color.LIGHT_GRAY);
            messageLabel.setForeground(Color.blue);
            messageLabel.setText("Login Successful!!");
        } else {
            attemptCount++;
            messageLabel.setBackground(Color.LIGHT_GRAY);
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Login Failed!! Attempt " + attemptCount + " of 3.");
        }
    }
}

public class LogInPage {

    public static void main(String[] args) {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginpage", "root", "8521");

            // Create Statement
            Statement smt = con.createStatement();

            // Execute query
            ResultSet rs = smt.executeQuery("SELECT * FROM login");

            // Process result set
            while (rs.next()) {
                String userName = rs.getString(1);
                String adminPass = rs.getString(2);

                new LoginProcess(userName, adminPass);
            }

            // Close resources
            rs.close();
            smt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
