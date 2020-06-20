package nathan.luka.myseries.model;

public class Episode {

    private String airDate;
    private Integer id;
    private String name;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private Integer tmdbID;
    private String releaseDate;


    public Episode(String name, Integer seasonNumber, Integer episodeNumber, Integer tmdbID, String releaseDate) {
        this.name = name;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.releaseDate = releaseDate;
        this.tmdbID = tmdbID;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getTmdbID() {
        return tmdbID;
    }

    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setTmdbID(Integer tmdbID) {
        this.tmdbID = tmdbID;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
