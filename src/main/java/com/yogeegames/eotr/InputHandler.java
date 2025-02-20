package com.yogeegames.eotr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;


public class InputHandler {
    private JTextField userInputField;
    private JButton submitButton;
    private JButton continueButton;

    public InputHandler(JTextField inputField, JButton submitBtn, JButton continueBtn) {
        this.userInputField = inputField;
        this.submitButton = submitBtn;
        this.continueButton = continueBtn;
        initializeListeners();
    }

    private void initializeListeners() {
        // Continue button listener (Moves past splash/intro)
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("🔄 Continue Button Pressed - Moving to Next Block");
                GameController.loadNextBlock(null);  // Do NOT pass a user choice
            }
        });
    
    
        // Submit button listener (Handles user choices)
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processUserInput();
            }
        });
    
        // Pressing Enter acts as a submit action
        userInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processUserInput();
            }
        });
    }
    

    private void processUserInput() {
        String userInput = userInputField.getText().trim();
        System.out.println("User Input Received: " + userInput);
    
        if (!userInput.isEmpty()) {
            try {
                int choice = Integer.parseInt(userInput);
                System.out.println("Parsed Choice: " + choice);
    
                // Call GameController to handle the transition
                GameController.loadNextBlock(userInput);  
    
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
    
            userInputField.setText("");  // Clear input field after submission
        }
    }
    
}
