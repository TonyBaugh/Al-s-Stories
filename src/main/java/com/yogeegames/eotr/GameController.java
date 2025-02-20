package com.yogeegames.eotr;

public class GameController {
    public static void loadNextBlock(String userChoice) {
        System.out.println("🔍 Current Story Block BEFORE: " + EoTR.currentStoryBlock);
        
        if (EoTR.currentStoryBlock.equals("splash.html")) {
            System.out.println("Loading Splash Screen");
            SplashScreen.loadSplashScreen();
            EoTR.currentStoryBlock = "intro.html";
            
        }else if (EoTR.currentStoryBlock.equals("intro.html")) {
            System.out.println("🌟 Special Case: Transitioning from Intro to Who Are You");
            EoTR.previousStoryBlock = "intro.html";
            EoTR.currentStoryBlock = "who_are_you.html";
        } else {
            EoTR.previousStoryBlock = EoTR.currentStoryBlock;
    
            // If userChoice is NULL, just transition to the next block naturally
            if (userChoice == null) {
                System.out.println("➡️ No user choice yet, moving to next block...");
                return;
            }
    
            // Otherwise, process user choice normally
            EoTR.currentStoryBlock = StoryMapLoader.getNextStoryBlock(EoTR.currentStoryBlock, userChoice);
        }
    
        // Validate if we actually got a valid next block
        if (EoTR.currentStoryBlock == null) {
            System.out.println("❌ ERROR: StoryMapLoader returned null for user choice: " + userChoice);
            return;
        }
    
        System.out.println("✅ Current Story Block UPDATED TO: " + EoTR.currentStoryBlock);
    
        // Load and display the next story block
        String fullStory = StoryLoader.readStoryBlock(EoTR.currentStoryBlock);
        GameWindow.displayStoryBlock(fullStory);
    
        // Check if there are questions
        String questionFile = StoryMapLoader.getQuestionFile(EoTR.currentStoryBlock);
        if (questionFile != null) {
            GameWindow.switchToUserInput();
        } else {
            GameWindow.switchToContinueButton();
        }
    }
}     
