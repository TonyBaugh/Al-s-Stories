package com.yogeegames.eotr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * GameWindow - Manages the GUI display, buttons, and text rendering.
 * - Does NOT handle game logic (delegates to GameManager)
 * - Does NOT handle input processing (delegates to InputHandler)
 */
public class GameWindow {
    private static JFrame frame;
    private static JEditorPane storyTextArea;
    private static JPanel inputPanel;
    private static JTextField userInputField;
    private static JButton submitButton;
    private static JButton continueButton;

    public GameWindow() {
        initializeWindow();
        initializeUIComponents();
    }

    /** 
     * Initializes the main game window. 
     */
    private void initializeWindow() {
        frame = new JFrame("Echoes of the Rift");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1200);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }

    /**
     * Initializes and adds UI components.
     */
    private void initializeUIComponents() {
        // === STORY DISPLAY AREA ===
        storyTextArea = new JEditorPane();
        storyTextArea.setEditable(false);
        storyTextArea.setContentType("text/html"); // Ensures proper rendering of HTML
        storyTextArea.setBackground(Color.BLACK);
        storyTextArea.setForeground(Color.CYAN);
        storyTextArea.setFont(new Font("Serif", Font.PLAIN, 18));

        JScrollPane scrollPane = new JScrollPane(storyTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // === INPUT AREA (BOTTOM) ===
        inputPanel = new JPanel(new BorderLayout());

        // Create buttons & input field
        userInputField = new JTextField();
        submitButton = new JButton("Submit");
        continueButton = new JButton("Continue");

        // Set Fonts
        userInputField.setFont(new Font("Serif", Font.PLAIN, 16));
        submitButton.setFont(new Font("Serif", Font.BOLD, 16));
        continueButton.setFont(new Font("Serif", Font.BOLD, 16));

        // Attach event listeners via InputHandler
        new InputHandler(userInputField, submitButton, continueButton);

        // Start game with "Continue" button
        inputPanel.add(continueButton, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Updates the story text display.
     * @param newContent The new HTML-formatted content to display.
     */
    public static void displayStoryBlock(String storyFile) {
        String fullStoryContent = StoryLoader.readStoryBlock(EoTR.currentStoryBlock); // ✅ Fetch the story + questions
        storyTextArea.setText(fullStoryContent);  // ✅ Set the text in the UI
        storyTextArea.setCaretPosition(0);  // ✅ Ensure scrolling resets to top
    }
    

    /**
     * Switches to user input mode (for numbered choices).
     */
    public static void switchToUserInput() {
        inputPanel.removeAll();
        inputPanel.add(userInputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Switches to continue mode (for passive progression).
     */
    public static void switchToContinueButton() {
        inputPanel.removeAll();
        inputPanel.add(continueButton, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Gets the user input field (needed for InputHandler).
     */
    public static JTextField getUserInputField() {
        return userInputField;
    }

    /**
     * Gets the submit button (needed for InputHandler).
     */
    public static JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Gets the continue button (needed for InputHandler).
     */
    public static JButton getContinueButton() {
        return continueButton;
    }
}
