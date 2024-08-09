package com.GuessingGame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
//import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

class Game {
    int userInput;
    int noOfGuesses;
    int randomNumber;
    boolean rightGuess = true;
    
    // Components
    Frame frame;
    Panel panel;
    Label messageLabel;
    TextField tx;
    Button btn;
    
    Game() {
        Random random = new Random();
        this.randomNumber = random.nextInt(100) + 1; 
        noOfGuesses = 0;
        
        frame = new Frame("Number Guessing Game");
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());
        Font boldFont = new Font("SansSerif", Font.BOLD, 11);
        
        panel=new Panel();
        panel.setSize(400,400);
        panel.setBackground(Color.gray);
        panel.setLayout(null);
        
        Label label1 = new Label("Enter Number Between 1 to 100 : ");
        label1.setFont(boldFont);
        label1.setBackground(Color.yellow);
        label1.setForeground(Color.black);
        label1.setBounds(200, 300, 190, 20);
        panel.add(label1);
        
        tx = new TextField();
        tx.setBounds(410, 300, 80, 20);
        panel.add(tx);
        
        btn = new Button("Submit");
        btn.setBounds(300, 350, 80, 20);
        btn.setFont(boldFont);
        btn.setBackground(Color.lightGray);
        panel.add(btn);
        
        messageLabel = new Label("");
        messageLabel.setBounds(200, 400, 300, 20);
        messageLabel.setFont(boldFont);
        messageLabel.setBackground(Color.LIGHT_GRAY);
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel);
        
        frame.add(panel, BorderLayout.CENTER);
        
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        
        frame.setVisible(true);
    }

    void processGuess() {
        if (rightGuess) {
            noOfGuesses++;
            try {
                userInput = Integer.parseInt(tx.getText());
            } catch (NumberFormatException e1) {
                messageLabel.setText("Invalid input. Please enter a number.");
                return;
            }

            if (userInput == randomNumber) {
                messageLabel.setText("Congratulations!! You Win. You Guessed it in " + noOfGuesses + " attempts!!");
                tx.setEnabled(false);
                btn.setEnabled(false);
                rightGuess = false;
            } else if (userInput < randomNumber) {
                messageLabel.setText(userInput + " is too low. Please enter a higher number.");
            } else {
                messageLabel.setText(userInput + " is too high. Please enter a lower number.");
            }
        }
    }
}

public class NumberGuessingGame {
    public static void main(String[] args) {
        new Game();
    }
}
