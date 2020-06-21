package nathan.luka.myseries.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import nathan.luka.myseries.model.gjson.Genre;

import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder(alphabetic=true)
@JsonRootName(value = "Serie")
public class Serie  {


    private static int lastId;

    private final int id;
    @SerializedName("title")
    @Expose
    private String title;  //d

    @SerializedName("imageurl")
    @Expose
    private String imageurl; //backdrop

    @SerializedName("gridImageURL")
    @Expose
    private String gridImageURL;

    @SerializedName("amountOfEpisodes")
    @Expose
    private Integer amountOfEpisodes; //d

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres; // test if works

    @SerializedName("amountOfSeasons")
    @Expose
    private Integer amountOfSeasons; //d

    @SerializedName("status")
    @Expose
    private String status; //d

    @SerializedName("themoviedbSerieID")
    @Expose
    private Integer themoviedbSerieID; //d

    @SerializedName("description")
    @Expose
    private String description; //d

    @SerializedName("seasons")
    @Expose
    private List<Season> seasons;
    private final List<Review> reviews;
    private double meanRating;

    private User user;
    private int currentEP = 5;
    private int maxEP = 10;
    private int progress;
    private boolean completed;
    public Serie() {
        this.seasons = new ArrayList<>();
        this.id = lastId;
        lastId++;
        this.reviews = new ArrayList<>();
        init();
    }

    public Serie(String title, Integer amountOfSeasons, Integer amountOfEpisodes,
                 List<Season> seasons, List<Genre> genres, Integer themoviedbSerieID, String description, User user, String status) {
        this.title = title;
        this.amountOfSeasons = amountOfSeasons;
        this.amountOfEpisodes = amountOfEpisodes;
        this.seasons = seasons;
        this.genres = genres;
        this.themoviedbSerieID = themoviedbSerieID;
        this.description = description;
        this.user = user;
        this.status = status;
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

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Serie.lastId = lastId;
    }

    public int getId() {
        return id;
    }
    @JsonGetter("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getAmountOfEpisodes() {
        return amountOfEpisodes;
    }

    public void setAmountOfEpisodes(Integer amountOfEpisodes) {
        this.amountOfEpisodes = amountOfEpisodes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Integer getAmountOfSeasons() {
        return amountOfSeasons;
    }

    public void setAmountOfSeasons(Integer amountOfSeasons) {
        this.amountOfSeasons = amountOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public double getMeanRating() {
        return meanRating;
    }

    public void setMeanRating(double meanRating) {
        this.meanRating = meanRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCurrentEP() {
        return currentEP;
    }

    public void setCurrentEP(int currentEP) {
        this.currentEP = currentEP;
    }

    public int getMaxEP() {
        return maxEP;
    }

    public void setMaxEP(int maxEP) {
        this.maxEP = maxEP;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getThemoviedbSerieID() {
        return themoviedbSerieID;
    }

    public void setThemoviedbSerieID(Integer themoviedbSerieID) {
        this.themoviedbSerieID = themoviedbSerieID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGridImageURL() {
        return gridImageURL;
    }

    public void setGridImageURL(String gridImageURL) {
        this.gridImageURL = gridImageURL;
    }

    @Override
//    @JsonValue
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** Employee Details *****\n");
        sb.append("ID=" + getId() + "\n");
        sb.append("Name=" + getTitle() + "\n");
        sb.append("*****************************");

        return sb.toString();

    }
}
