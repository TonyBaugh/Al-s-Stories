package com.yogeegames.eotr;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

// This class is load the json data into memory and fetch the lookup the correct story block and question .txt files
public class StoryMapLoader {
    private static Map<String, Map<String, Object>> storyMap;  // Stores the JSON data

    static {
        loadStoryMap();  // Load JSON when class is initialized
    }

    // Loads the story map JSON file into memory
    private static void loadStoryMap() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = StoryMapLoader.class.getClassLoader().getResourceAsStream("story_map.json");

            if (inputStream == null) {
                throw new RuntimeException("Error: story_map.json not found in resources.");
            }

            storyMap = objectMapper.readValue(inputStream, Map.class);  // Parse JSON into a Map
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading story_map.json");
        }
        // Debug: Print the full JSON data on startup       

    }

    // fetches the next story block to be printed based on the user's choice
    public static String getNextStoryBlock(String storyBlockFile, String userChoice) {
        // ✅ Check if storyMap is properly initialized
        if (storyMap == null) {
            System.out.println("ERROR: storyMap is NULL! JSON was not loaded.");
            return null;
        }
    
        // ✅ Get the current block's data
        Map<String, Object> currentData = storyMap.get(storyBlockFile);
    
        // ✅ Check if the block exists in the JSON
        if (currentData == null) {
            System.out.println("ERROR: No entry found for storyBlockFile: " + storyBlockFile);
            return null;
        }
    
        // ✅ Check if "choices" exist
        Object choicesObj = currentData.get("choices");
        if (choicesObj == null) {
            System.out.println("ERROR: 'choices' key is missing in block: " + storyBlockFile);
            return null;
        }
    
        // ✅ Ensure "choices" is a valid Map<String, String>
        if (!(choicesObj instanceof Map)) {
            System.out.println("ERROR: 'choices' is not a valid map in block: " + storyBlockFile);
            return null;
        }
    
        Map<String, String> choices = (Map<String, String>) choicesObj;
    
        // ✅ Ensure user choice exists in the map
        if (!choices.containsKey(userChoice)) {
            System.out.println("ERROR: No valid choice found for '" + userChoice + "' in block: " + storyBlockFile);
            System.out.println("Available choices: " + choices.keySet()); // Debugging: Print available choices
            return null;
        }
    
        String nextBlock = choices.get(userChoice);
        
        System.out.println("✅ Next Story Block: " + nextBlock + " (User Choice: " + userChoice + ")");
        return nextBlock;
    }
    

    // fetches the current user question file
    public static String getQuestionFile(String storyBlockFile) {
        Map<String, Object> currentData = storyMap.get(storyBlockFile);
    
        if (currentData == null) {
            return null;  // Handle missing story blocks safely
        }
    
        // Retrieve the questions file 
        String questionFile = (String) currentData.get("questions");
    
        // Retrieve the choices map and update the number of choices
        Map<String, String> choices = (Map<String, String>) currentData.get("choices");
    
        if (choices != null) {
            EoTR.currentNumberOfChoices = choices.size();
        } else {
            EoTR.currentNumberOfChoices = 0;  // No choices found
        }
    
        return questionFile;  // Return the name of the questions file
    }
    
}
