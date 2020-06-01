package nathan.luka.myseries.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Review {
    private static int lastId;
    private int rating;
    private String comment;
    private LocalDate dateAdded;
    private User user;
    private  int id;



    public Review(String comment, User user) {
        this.id = lastId;
        lastId++;
        this.comment = comment;
        this.user = user;
    }


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
