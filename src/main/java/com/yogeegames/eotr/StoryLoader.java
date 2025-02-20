package com.yogeegames.eotr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class StoryLoader {

    public static String readStoryBlock(String storyFile) {
        try {
            System.out.println("📖 Trying to load: " + storyFile);
            System.out.println("📂 Full path: " + StoryLoader.class.getClassLoader().getResource(storyFile));

            // Load HTML file from the resources folder
            InputStream storyStream = StoryLoader.class.getClassLoader().getResourceAsStream(storyFile.trim());
            if (storyStream == null) {
                System.out.println("🚨 Error: Story file not found!");
                return "<html><body><h3>Error: Story file not found.</h3></body></html>";
            }

            // Read file content
            BufferedReader storyReader = new BufferedReader(new InputStreamReader(storyStream, StandardCharsets.UTF_8));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = storyReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            storyReader.close();

            // Append question file (if any)
            return appendQuestionFile(storyFile, content.toString());

        } catch (IOException e) {
            return "<html><body><h3>Error loading content.</h3></body></html>";
        }
    }

    private static String appendQuestionFile(String storyFile, String storyContent) {
        String questionFile = StoryMapLoader.getQuestionFile(storyFile);
        if (questionFile != null) {
            try {
                System.out.println("❓ Appending Questions from: " + questionFile);
                InputStream questionStream = StoryLoader.class.getClassLoader().getResourceAsStream(questionFile);
                if (questionStream != null) {
                    BufferedReader questionReader = new BufferedReader(new InputStreamReader(questionStream, StandardCharsets.UTF_8));
                    StringBuilder content = new StringBuilder(storyContent);
                    content.append("<br><br>"); // Adds spacing before questions
                    String line;
                    while ((line = questionReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    questionReader.close();
                    return content.toString();
                } else {
                    System.out.println("⚠️ Error: Could not find " + questionFile);
                }
            } catch (IOException e) {
                System.out.println("⚠️ Error loading question file.");
            }
        }
        return storyContent;
    }
}
