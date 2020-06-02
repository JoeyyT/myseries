package nathan.luka.myseries.model;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    private static int lastId;

    private int id;

    private String title;
    private String imageurl;
    private String category;

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
    private List<Review> reviews;
    private boolean completed;

    public int getId() {
        return id;
    }

    public Serie() {
        this.id = lastId;
        lastId++;
        this.reviews = new ArrayList<>();
    }

    public Serie(String title, User user, String imageurl) {
        this();
        this.title = title;
        this.user = user;
        this.imageurl = imageurl;
    }


    public void addReview(Review review){
        this.reviews.add(review);
    }


    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public List<Review> getReview() {
        return reviews;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) { this.category = category; }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString() {
        return "Movie: " + title;
    }
}
