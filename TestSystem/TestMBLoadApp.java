

import messageboard.*;

import java.io.IOException;

public class TestMBLoadApp {
    public static void main(String[] args) {
        MessageBoard board = new MessageBoard("Empty Board");
        
        // Load the message board
        try {
            board.loadMessageBoard("codingsupport.ser");
            System.out.println("Board loaded successfully.");
        } catch(IOException ex) {
            System.out.println("Board not loaded.");
            ex.printStackTrace();
        } catch(ClassNotFoundException ex) {
            System.out.println("Could not find class.");
            ex.printStackTrace();
        }

        // Display all posts
        System.out.println("\nLoaded posts:");
        for (int id : board.getPostIDs()) {
            System.out.println(board.getFormattedPost(id));
        }

        // Save Windows post to file
        try {
            int[] windowsPosts = board.searchPostsBySubject("windows");
            if (windowsPosts.length > 0) {
                board.savePostAsTextFile(windowsPosts[0], "windowspost.txt");
                System.out.println("Windows post saved to file.");
            }
        } catch(IOException ex) {
            System.out.println("File not saved.");
            ex.printStackTrace();
        }
    }
} 