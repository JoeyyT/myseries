package nathan.luka.myseries.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    public static int lastId = 1;

    private int id;
    private String username;


    private String password;

    private String displayName;

    private Set<Serie> serie;

    private Set<Review> reviews;

    private boolean loggedIn;

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.id = lastId;
        lastId++;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<Serie> getSerie() {
        return serie;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public int getId() {
        return id;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        User.lastId = lastId;
    }

    public void setSerie(Set<Serie> serie) {
        this.serie = serie;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String toString() {
        return displayName + username;
    }
}
