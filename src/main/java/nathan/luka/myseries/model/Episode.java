package nathan.luka.myseries.model;

import java.util.ArrayList;
import java.util.List;

public class Episode {

    private String airDate;
    private Integer id;
    private String name;
    private int seasonNumber;
    private int SerieID;


    public Episode(String name, Integer seasonNumber, Integer serieID) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        SerieID = serieID;
    }
}
