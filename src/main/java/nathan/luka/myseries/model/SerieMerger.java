package nathan.luka.myseries.model;

import com.google.gson.Gson;
import nathan.luka.myseries.model.Serie;
import nathan.luka.myseries.model.gjson.Episode;
import nathan.luka.myseries.model.gjson.Season;
import nathan.luka.myseries.model.gjson.SerieGjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerieMerger {
    private ArrayList<Serie> series;
    private ArrayList<SerieGjson> serieGjsonlist;
    Gson gson = new Gson();
    BufferedReader bufferedReader;

    public SerieMerger() {
        series = new ArrayList<>();
        serieGjsonlist = new ArrayList<>();
    }


    public void testData() {
        for (SerieGjson serieGjson : serieGjsonlist) {
            System.out.println(serieGjson.getName());
            List<Season> season = serieGjson.getSeasons();
            for (Season season1 : season) {
                List<Episode> episode = season1.getEpisodes();
                System.out.println(season1.getName() + " season: " + season1.getSeasonNumber());
                for (int i = 0; i < episode.size(); i++) {
//                        System.out.println(serieGjson.getSeasons().get(0).getEpisodes().get(i).getEpisodeNumber() + ": ");
                    System.out.println(episode.get(i).getEpisodeNumber() + ": " + episode.get(i).getName());
                }
            }
        }
    }

    private void bulkImportJsonToSerieGjsonList(List<String> listOfJsonFileNames) {
        if (listOfJsonFileNames != null) {
            String filePath = new File("").getAbsolutePath();
            for (String listOfJsonFileName : listOfJsonFileNames) {
                String strNew = filePath + "/src/main/resources/static/json/" + listOfJsonFileName;
                SerieGjson serieGjson = importSerieGjsonFromJsonFile(strNew);

                if (serieGjson != null) {
                    serieGjsonlist.add(serieGjson);
                }
            }
            for (SerieGjson serieGjson : serieGjsonlist) {
                System.out.println("Title: " + serieGjson.getName() + " | ID: " + serieGjson.getId() + " | Number of seasons:  " + serieGjson.getNumberOfSeasons() + " | Number of episodes: " + serieGjson.getNumberOfEpisodes());
            }
        }
    }

    private void bulkImportSeasonJsonToSerieGjsonList(List<String> listOfJsonFileNames) {
        if (listOfJsonFileNames != null) {
            String filePath = new File("").getAbsolutePath();
            for (String listOfJsonFileName : listOfJsonFileNames) {
                String strNew = filePath + "/src/main/resources/static/json/" + listOfJsonFileName;
                Season season = importSeasonFromJsonFile(strNew);

                if (season != null) {
                    System.out.println("Season not null");
                    for (int i = 0; i < serieGjsonlist.size(); i++) {


                        if (Objects.equals(serieGjsonlist.get(i).getId(), season.getEpisodes().get(0).getShowId())) {
                            System.out.println("ID match found " + season.getEpisodes().get(0).getShowId());
                            //Season is of the same Serie.
                            boolean seasonAlreadyExists = false;
                            List<Season> seasonFromSeriesGjsonList = serieGjsonlist.get(i).getSeasons();


                            for (int j = 0; j < seasonFromSeriesGjsonList.size(); j++) {
                                if (Objects.equals(season.getAirDate(), seasonFromSeriesGjsonList.get(j).getAirDate())) {
                                    seasonAlreadyExists = true;
                                    if (seasonFromSeriesGjsonList.get(j).getEpisodes().isEmpty() || seasonFromSeriesGjsonList.get(j).getEpisodes().size() < season.getEpisodes().size()) {
                                        System.out.println("serieGjsonSeason is leeg");
                                        serieGjsonlist.get(i).getSeasons().set(j, season);
                                    }
                                }
                            }
                            if (!seasonAlreadyExists) {
                                serieGjsonlist.get(i).getSeasons().add(season);
//                                System.out.println("Added season:" + season.getSeasonNumber() + " for serie:" + serieGjson.getName());
                            }
                        }
                    }
                }

            }
        }
    }

    private SerieGjson importSerieGjsonFromJsonFile(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            SerieGjson serieGjson = gson.fromJson(bufferedReader, SerieGjson.class);
            return serieGjson;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Season importSeasonFromJsonFile(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            Season season = gson.fromJson(bufferedReader, Season.class);
            return season;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}



