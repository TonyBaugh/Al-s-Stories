package com.yogeegames.eotr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// This class retrieves the file requested, reads the text, and returns it as a string.
public class StoryBlockReader {

    public static String readBlock(String fileName) {
        StringBuilder storyBlock = new StringBuilder();

        try (InputStream inputStream = StoryBlockReader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader in = inputStream != null ? 
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)) : null) {
            
            if (in == null) {
                System.out.println("Error: Could not find story file " + fileName);
                return "Error: Story block missing.";
            }

            String line;
            while ((line = in.readLine()) != null) {
                storyBlock.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName + " - " + e.getMessage());
            e.printStackTrace();
        }
        
        return storyBlock.toString().trim();
    }
}
