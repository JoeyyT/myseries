package nathan.luka.myseries.model;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    private static int lastId;

    private final int id;

    private String title;
    private String imageurl;
    private String category;
    private double meanRating;

    private User user;
    private final List<Review> reviews;
    private boolean completed;
    private int currentEP = 5;
    private int maxEP = 10;
    private int progress;

    public Serie() {
        this.id = lastId;
        lastId++;
        this.reviews = new ArrayList<>();
        init();
    }

    public Serie(String title, User user, String imageurl) {
        this();
        this.title = title;
        this.user = user;
        this.imageurl = imageurl;
        init();
    }

    public Serie(String title, User user) {
        this();
        this.title = title;
        this.user = user;
        init();
    }

    public void init() {
        currentEP = 5;
        maxEP = 10;
        progress = 50;
//        progress = (currentEP / maxEP) * 100;
    }

    public int getProgress() {
        return progress;
    }

    public User getUser() {
        return user;
    }

    public void addReview(Review review) {
        //todo reviewRating not higher then 5 or 10?
        //Adding review to list of reviews
        this.reviews.add(review);
        //recalculating the mean rating with new added review
        calculateMeanRating();
    }

    private void calculateMeanRating() {
        for (Review reviewi : reviews) {
            meanRating += reviewi.getRating();
        }
        meanRating = meanRating / reviews.size();
    }

    public double getMeanRating() {
        return meanRating;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Movie: " + title;
    }

}
