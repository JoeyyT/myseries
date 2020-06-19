package nathan.luka.myseries.model;

import com.google.gson.Gson;
import nathan.luka.myseries.model.gjson.Episode;
import nathan.luka.myseries.model.gjson.SeasonTheMovieDB;
import nathan.luka.myseries.model.gjson.SerieTheMovieDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerieMerger {
    private ArrayList<Serie> series;
    private ArrayList<SerieTheMovieDB> serieGjsonlist;
    Gson gson = new Gson();
    BufferedReader bufferedReader;

    public SerieMerger() {
        series = new ArrayList<>();
        serieGjsonlist = new ArrayList<>();
    }


    public void testData() {
        for (SerieTheMovieDB serieTheMovieDB : serieGjsonlist) {
            System.out.println(serieTheMovieDB.getName());
            List<SeasonTheMovieDB> seasonTheMovieDB = serieTheMovieDB.getSeasonTheMovieDBS();
            for (SeasonTheMovieDB seasonTheMovieDB1 : seasonTheMovieDB) {
                List<Episode> episode = seasonTheMovieDB1.getEpisodes();
                System.out.println(seasonTheMovieDB1.getName() + " season: " + seasonTheMovieDB1.getSeasonNumber());
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
                SerieTheMovieDB serieTheMovieDB = importSerieGjsonFromJsonFile(strNew);

                if (serieTheMovieDB != null) {
                    serieGjsonlist.add(serieTheMovieDB);
                }
            }
            for (SerieTheMovieDB serieTheMovieDB : serieGjsonlist) {
                System.out.println("Title: " + serieTheMovieDB.getName() + " | ID: " + serieTheMovieDB.getId() + " | Number of seasons:  " + serieTheMovieDB.getNumberOfSeasons() + " | Number of episodes: " + serieTheMovieDB.getNumberOfEpisodes());
            }
        }
    }

    private void bulkImportSeasonJsonToSerieGjsonList(List<String> listOfJsonFileNames) {
        if (listOfJsonFileNames != null) {
            String filePath = new File("").getAbsolutePath();
            for (String listOfJsonFileName : listOfJsonFileNames) {
                String strNew = filePath + "/src/main/resources/static/json/" + listOfJsonFileName;
                SeasonTheMovieDB seasonTheMovieDB = importSeasonFromJsonFile(strNew);

                if (seasonTheMovieDB != null) {
                    System.out.println("Season not null");
                    for (int i = 0; i < serieGjsonlist.size(); i++) {


                        if (Objects.equals(serieGjsonlist.get(i).getId(), seasonTheMovieDB.getEpisodes().get(0).getShowId())) {
                            System.out.println("ID match found " + seasonTheMovieDB.getEpisodes().get(0).getShowId());
                            //Season is of the same Serie.
                            boolean seasonAlreadyExists = false;
                            List<SeasonTheMovieDB> seasonTheMovieDBFromSeriesGjsonList = serieGjsonlist.get(i).getSeasonTheMovieDBS();


                            for (int j = 0; j < seasonTheMovieDBFromSeriesGjsonList.size(); j++) {
                                if (Objects.equals(seasonTheMovieDB.getAirDate(), seasonTheMovieDBFromSeriesGjsonList.get(j).getAirDate())) {
                                    seasonAlreadyExists = true;
                                    if (seasonTheMovieDBFromSeriesGjsonList.get(j).getEpisodes().isEmpty() || seasonTheMovieDBFromSeriesGjsonList.get(j).getEpisodes().size() < seasonTheMovieDB.getEpisodes().size()) {
                                        System.out.println("serieGjsonSeason is leeg");
                                        serieGjsonlist.get(i).getSeasonTheMovieDBS().set(j, seasonTheMovieDB);
                                    }
                                }
                            }
                            if (!seasonAlreadyExists) {
                                serieGjsonlist.get(i).getSeasonTheMovieDBS().add(seasonTheMovieDB);
//                                System.out.println("Added season:" + season.getSeasonNumber() + " for serie:" + serieGjson.getName());
                            }
                        }
                    }
                }

            }
        }
    }

    private SerieTheMovieDB importSerieGjsonFromJsonFile(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            SerieTheMovieDB serieTheMovieDB = gson.fromJson(bufferedReader, SerieTheMovieDB.class);
            return serieTheMovieDB;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SeasonTheMovieDB importSeasonFromJsonFile(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            SeasonTheMovieDB seasonTheMovieDB = gson.fromJson(bufferedReader, SeasonTheMovieDB.class);
            return seasonTheMovieDB;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}



