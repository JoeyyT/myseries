package nathan.luka.myseries.model;

import com.google.gson.Gson;
import nathan.luka.myseries.model.gjson.Episode;
import nathan.luka.myseries.model.gjson.SeasonTheMovieDB;
import nathan.luka.myseries.model.gjson.SerieTheMovieDB;


import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class SerieCollector implements Callable<SerieTheMovieDB> {



    private ArrayList<Serie> series;
    private static ArrayList<SeasonTheMovieDB> seasonsList;
    private ArrayList<SerieTheMovieDB> serieGjsonlist;
    Gson gson = new Gson();
    BufferedReader bufferedReader;
    static SerieTheMovieDB serieTheMovieDB;
    boolean completed;
    public SerieCollector(int serieID, int amountOfSeasons, int option) {
        completed = false;
        seasonsList = new ArrayList<>();
        series = new ArrayList<>();
        serieGjsonlist = new ArrayList<>();
        getSerieWithSeason(serieID, amountOfSeasons, option);
    }

    //option 1: Serie only
    //option 2: Season only
    //option 3: both
    public SerieTheMovieDB getSerieWithSeason(int serieID, int amountOfSeasons, int option) {

        String website = "https://api.themoviedb.org/3/tv/";
        String season = "/season/";
        String var = "?";
        String api_key = "api_key=8acc85d41e6a21f84c2651c11e9453c7";
        String language = "&language=en-US";
        String result;
        List<String> results = new ArrayList<>();
        SerieTheMovieDB endResult;
        switch (option) {
            case 1:
                result = website + serieID + var + api_key + language;
//                results.add(result);
                getResponseBody(result);
                break;
            case 2:
                for (int i = 0; i < amountOfSeasons; i++) {
                    result = website + serieID + season + i + var + api_key + language;
//                    results.add(result);
                    getResponseBody(result);
                }
                break;
            case 3:
//                result = website + serieID + var + api_key + language;
//                results.add(result);
//                getSerieWithSeason()
                break;
        }


        return null;
    }


    public void getResponseBody(String urls) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urls))
                    //cancel if timeout == 2min
//                .timeout(Duration.ofMinutes(2))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    //get the body
                    .thenApply(HttpResponse::body)
                    //print body
                    .thenApply(SerieCollector::parse)
                    .join();

    }

//    public void getResponseBody(List<String> urls) {
//        for (int i = 0; i < urls.size(); i++) {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urls.get(i)))
//                    //cancel if timeout == 2min
////                .timeout(Duration.ofMinutes(2))
//                    .build();
//            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                    //get the body
//                    .thenApply(HttpResponse::body)
//                    //print body
//                    .thenApply(SerieCollector::parse)
//                    .join();
//        }
//    }

    public static boolean determineIfJSONobjIsSeason(String responseBody) {
        if (responseBody.contains("season_number")) {
            System.out.println("DIKKE Season");
            return true;
        }
        return false;
    }

    public static boolean determineIfJSONobjIsSerie(String responseBody) {
        if (responseBody.contains("number_of_seasons")) {
            System.out.println("DIKKE SERIE");

            return true;
        }
        return false;
    }


    public static org.json.JSONObject parse(String responseBody) {
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        try {
            //create a temp file
            File temp = File.createTempFile("tempfile", ".tmp");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp));
            bufferedWriter.write(responseBody);
            bufferedWriter.close();
            bufferedReader = new BufferedReader(new FileReader(temp));
            if (determineIfJSONobjIsSerie(responseBody)) {
                serieTheMovieDB = gson.fromJson(bufferedReader, SerieTheMovieDB.class);
                System.out.println("Serie: " + serieTheMovieDB.getName());

            } else if (determineIfJSONobjIsSeason(responseBody)) {
                SeasonTheMovieDB seasonTheMovieDB = gson.fromJson(bufferedReader, SeasonTheMovieDB.class);
                System.out.println("Season " + seasonTheMovieDB.getName());
                seasonsList.add(seasonTheMovieDB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public SerieTheMovieDB call() throws Exception {
        System.out.println("done with: " + serieTheMovieDB.getName());
        return serieTheMovieDB;
    }

}
