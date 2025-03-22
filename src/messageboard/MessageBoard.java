package messageboard;

import messageboard.MessageBoardInterface;
import messageboard.IDInvalidException;
import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class MessageBoard implements MessageBoardInterface {
    private List<Post> posts;
    private String boardName;

    public MessageBoard(String boardName) {
        this.boardName = boardName;
        this.posts = new ArrayList<>();
    }

    public String getBoardName() {
        return boardName;
    }

    public int[] getPostIDs() {
        int[] postIDs = new int[posts.size()];
        for (int i = 0; i < posts.size(); i++) {
            postIDs[i] = posts.get(i).getPostID();
        }
        return postIDs;
    }

    private int getPostIndex(int postID) throws IDInvalidException {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getPostID() == postID) {
                return i;
            }
        }
        throw new IDInvalidException("Invalid post ID: " + postID);
    }

    public int addPost(String author, String subject, String message) 
            throws IllegalArgumentException {
        if (author == null || author.isEmpty() || 
            subject == null || subject.isEmpty() || 
            message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Invalid post parameters");
        }
        Post post = new Post(author, subject, message);
        posts.add(post);
        return post.getPostID();
    }

    public int addPost(String author, String subject, String message, int epochDate) 
            throws IllegalArgumentException {
        if (author == null || author.isEmpty() || 
            subject == null || subject.isEmpty() || 
            message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Invalid post parameters");
        }
        Post post = new Post(author, subject, message, LocalDate.ofEpochDay(epochDate));
        posts.add(post);
        return post.getPostID();
    }

    public void deletePost(int postID) throws IDInvalidException {
        int index = getPostIndex(postID);
        posts.remove(index);
    }

    public int[] searchPostsBySubject(String keyword) {
        List<Integer> matchingPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getSubject().toLowerCase().contains(keyword.toLowerCase())) {
                matchingPosts.add(post.getPostID());
            }
        }
        int[] result = new int[matchingPosts.size()];
        for (int i = 0; i < matchingPosts.size(); i++) {
            result[i] = matchingPosts.get(i);
        }
        return result;
    }

    public int[] searchPostsByDate(int startDate, int endDate) {
        List<Integer> matchingPosts = new ArrayList<>();
        for (Post post : posts) {
            int postDate = post.getDate();
            if (postDate >= startDate && postDate <= endDate) {
                matchingPosts.add(post.getPostID());
            }
        }
        int[] result = new int[matchingPosts.size()];
        for (int i = 0; i < matchingPosts.size(); i++) {
            result[i] = matchingPosts.get(i);
        }
        return result;
    }

    public String getFormattedPost(int postID) throws IDInvalidException {
        int index = getPostIndex(postID);
        return posts.get(index).toFormattedString();
    }

    public void saveMessageBoard(String filename) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(boardName);
            Post[] postArray = posts.toArray(new Post[0]);
            out.writeObject(postArray);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void loadMessageBoard(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            boardName = (String) in.readObject();
            Post[] postArray = (Post[]) in.readObject();
            posts = new ArrayList<>(Arrays.asList(postArray));
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void savePostAsTextFile(int postID, String filename) 
            throws IDInvalidException, IOException {
        int index = getPostIndex(postID);
        posts.get(index).saveAsTextFile(filename);
    }
} 