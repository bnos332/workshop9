package messageboard;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post implements Serializable {
    private static int idCounter = 0;
    private int postID;
    private String author;
    private String subject;
    private String message;
    private int date;

    public Post(String author, String subject, String message) {
        this(author, subject, message, null);
    }

    public Post(String author, String subject, String message, LocalDate date) {
        this.postID = ++idCounter;
        this.author = author;
        this.subject = subject;
        this.message = message;
        if (date == null) {
            this.date = (int)LocalDate.now().toEpochDay();
        } else {
            this.date = (int)date.toEpochDay();
        }
    }

    public int getPostID() { return postID; }
    public String getAuthor() { return author; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public int getDate() { return date; }

    public String toString() {
        return String.format("Post[postID=%d, author=\"%s\", subject=\"%s\", message=\"%s\", date=%d]", 
                           postID, author, subject, message.replace("\n", "\\n"), date);
    }

    public String toFormattedString() {
        LocalDate postDate = LocalDate.ofEpochDay(this.date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return "\n------------------  Post " + postID + "  -------------------" + 
               "\nAuthor: " + author + 
               "\nDate: " + postDate.format(formatter) + 
               "\nSubject: " + subject + "\n" + 
               "----  Message:  -------------------------------\n" +  
               message + 
               "\n-----------------------------------------------\n";
    }

    public void saveAsTextFile(String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        out.write(this.toFormattedString());
        out.close();
    }
} 