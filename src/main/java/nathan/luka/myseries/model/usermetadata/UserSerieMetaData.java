package nathan.luka.myseries.model.usermetadata;

import nathan.luka.myseries.model.Serie;

import java.util.ArrayList;
import java.util.List;

public class UserSerieMetaData {
    private Integer themoviedbSerieID;
    private Integer currentEP = 5;
    private Integer maxEP = 10;
    private Integer progress;
    private Integer completed;
    private Serie serie;

    private List<UserSeasonMetaData> userSeasonMetaDataList;

    public UserSerieMetaData(Integer themoviedbSerieID) {
        this.themoviedbSerieID = themoviedbSerieID;
    }
    public UserSerieMetaData(Serie serie) {
        this.serie = serie;
        this.themoviedbSerieID = serie.getThemoviedbSerieID();
        this.maxEP = serie.getAmountOfEpisodes();
        this.userSeasonMetaDataList = new ArrayList<>();
        for (int i = 0; i < serie.getSeasons().size(); i++) {
            userSeasonMetaDataList.add(i, new UserSeasonMetaData(serie, serie.getSeasons().get(i)));
        }
    }

    public Integer getThemoviedbSerieID() {
        return themoviedbSerieID;
    }

    public void setThemoviedbSerieID(Integer themoviedbSerieID) {
        this.themoviedbSerieID = themoviedbSerieID;
    }

    public Integer getCurrentEP() {
        return currentEP;
    }

    public void setCurrentEP(Integer currentEP) {
        this.currentEP = currentEP;
    }

    public Integer getMaxEP() {
        return maxEP;
    }

    public void setMaxEP(Integer maxEP) {
        this.maxEP = maxEP;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public List<UserSeasonMetaData> getUserSeasonMetaDataList() {
        return userSeasonMetaDataList;
    }

    public void setUserSeasonMetaDataList(List<UserSeasonMetaData> userSeasonMetaDataList) {
        this.userSeasonMetaDataList = userSeasonMetaDataList;
    }
}


