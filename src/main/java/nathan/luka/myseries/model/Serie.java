package nathan.luka.myseries.model;

import java.util.List;

public class Serie {
    private String title;
    private String imageurl;
    private String category;

    private List<Review> reviews;
    private boolean completed;

    public Serie() {
    }

    public Serie(String title) {
        this.title = title;
    }

    public Serie(String title, String imageURL) {
        this.title = title;
        this.imageurl = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() { return category; }

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
