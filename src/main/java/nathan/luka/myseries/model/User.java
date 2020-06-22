package nathan.luka.myseries.model;

import nathan.luka.myseries.model.usermetadata.UserSerieMetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User {
    public static int lastId = 1;

    private int id;
    private String email;


    private String password;

    private String userName;

    private Set<Serie> serie;

    private List<UserSerieMetaData> userSerieMetaDataList;

    private Set<Review> reviews;

    private boolean loggedIn;
    public User(){

    }


    public User(String password, String userName) {
        this.userSerieMetaDataList = new ArrayList<>();
        this.password = password;
        this.userName = userName;
        this.id = lastId;
        lastId++;
    }

    public void addSerie(Serie serie){
        userSerieMetaDataList.add(new UserSerieMetaData(serie));
    }

    public void setEpisodeWatched(Integer themoviedbSerieID, Integer seasonNumber, Integer episodeNumber){
        for (int i = 0; i < userSerieMetaDataList.size(); i++) {
            if (userSerieMetaDataList.get(i).getThemoviedbSerieID().equals(themoviedbSerieID)){
                //match

                if (!userSerieMetaDataList.get(i).getUserSeasonMetaDataList().get(seasonNumber).getUserEpisodeMetaDataList().get(episodeNumber).isWatched()){
                    userSerieMetaDataList.get(i).getUserSeasonMetaDataList().get(seasonNumber).getUserEpisodeMetaDataList().get(episodeNumber).setWatched(true);
                } else{
                    userSerieMetaDataList.get(i).getUserSeasonMetaDataList().get(seasonNumber).getUserEpisodeMetaDataList().get(episodeNumber).setWatched(false);
                }
                System.out.println("gelukt niggah");
            }
        }

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public Set<Serie> getSerie() {
        return serie;
    }

    public void addToSetSerie(String serieName, User user) {
        Serie serie = new Serie(serieName, user);
        this.serie.add(serie);
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return userName + email;
    }
}
