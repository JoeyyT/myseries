package nathan.luka.myseries.model;

import java.util.HashSet;
import java.util.Set;

public class User {
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
        this.serie = new HashSet<>();
        this.reviews = new HashSet<>();
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
