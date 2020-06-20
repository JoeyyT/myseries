package nathan.luka.myseries.model;

import java.util.ArrayList;
import java.util.List;

public class Season {

    private String airDate;
    private Integer episodeCount;
    private List<Episode> episodes;

    private String name;
    private Integer seasonNumber;
    private Integer themoviedbSerieID;


    public Season(String name, Integer seasonNumber, Integer themoviedbSerieID, Integer episodeCount) {
        this.episodes = new ArrayList<>();
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeCount = episodeCount;
        this.themoviedbSerieID = themoviedbSerieID;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getThemoviedbSerieID() {
        return themoviedbSerieID;
    }

    public void setThemoviedbSerieID(Integer themoviedbSerieID) {
        this.themoviedbSerieID = themoviedbSerieID;
    }
}
