/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.yogeegames.eotr;

/** 
 * @author Antonio Baugh - Feb, 2025
 */

public class EoTR {
    // Globals to keep track of users path
    public static String previousStoryBlock = "";
    public static String currentStoryBlock = "splash.html";

    // Tracks how many choices are in the currentStoryBlock
    public static int currentNumberOfChoices;

    public static void main(String[] args) {    
        GameWindow game = new GameWindow();
        GameWindow.displayStoryBlock(currentStoryBlock);  // Display the first story block
        // GameWindow.getContinueButton();
        
        // NEXT STEPS - Save full story to output file upon completion
    }
}

