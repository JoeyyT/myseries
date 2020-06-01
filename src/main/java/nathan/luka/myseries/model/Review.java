package nathan.luka.myseries.model;

import java.time.LocalDate;

public class Review {
    private int rating;
    private String comment;
    private LocalDate dateAdded;
    private User user;

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public User getUser() {
        return user;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
