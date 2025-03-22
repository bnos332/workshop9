import messageboard.*;
import java.io.IOException;

public class TestMBApp {
    public static void main(String[] args) {
        MessageBoard board = new MessageBoard("Coding Support");
        
        // Add initial posts
        board.addPost("Alex Adam", "Help with Java", 
            "Hi, could anyone help me I need to learn how to code in java!");
        board.addPost("Belinda Bennett", "Help with Java", 
            "Hi Alex. Yes I can send some tutorials I found useful.");
        board.addPost("Cindy Carter", "Coding on a Chromebook", 
            "Hi, could anyone help me I need to learn how to code in java!");
        board.addPost("Dennis Dobson", "Windows problems", 
            "My windows laptop is stuck on a reboot loop. Does anyone know what to do!");

        // Display all posts
        System.out.println("Initial posts:");
        displayAllPosts(board);

        // Search and delete posts with "Java" in subject
        System.out.println("\nDeleting posts with 'Java' in subject...");
        int[] javaPostIDs = board.searchPostsBySubject("Java");
        for (int id : javaPostIDs) {
            board.deletePost(id);
        }

        // Display remaining posts
        System.out.println("\nRemaining posts:");
        displayAllPosts(board);

        // Add posts with specific dates
        board.addPost("Ellie", "Java IDE", 
            "Can someone recommend a Java IDE?", 20148);
        board.addPost("Fred Fansha", "Java IDE", 
            "I just use VS Code", 20149);

        // Search by date range
        System.out.println("\nPosts between dates 20147-20149:");
        int[] dateRangePosts = board.searchPostsByDate(20147, 20149);
        for (int id : dateRangePosts) {
            System.out.println(board.getFormattedPost(id));
        }

        // Save the message board
        try {
            board.saveMessageBoard("codingsupport.ser");
            System.out.println("Board saved successfully.");
        } catch(IOException ex) {
            System.out.println("Board not saved.");
            ex.printStackTrace();
        }
    }

    private static void displayAllPosts(MessageBoard board) {
        for (int id : board.getPostIDs()) {
            System.out.println(board.getFormattedPost(id));
        }
    }
} 