package nathan.luka.myseries.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nathan.luka.myseries.model.Serie;
import nathan.luka.myseries.model.gjson.Episode;
import nathan.luka.myseries.model.gjson.Season;
import nathan.luka.myseries.model.gjson.SerieGjson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SerieCollector implements Callable<SerieGjson> {



    private ArrayList<Serie> series;
    private static ArrayList<Season> seasonsList;
    private ArrayList<SerieGjson> serieGjsonlist;
    Gson gson = new Gson();
    BufferedReader bufferedReader;
    static SerieGjson serieGjson;
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
    public SerieGjson getSerieWithSeason(int serieID, int amountOfSeasons, int option) {

        String website = "https://api.themoviedb.org/3/tv/";
        String season = "/season/";
        String var = "?";
        String api_key = "api_key=8acc85d41e6a21f84c2651c11e9453c7";
        String language = "&language=en-US";
        String result;
        List<String> results = new ArrayList<>();
        SerieGjson endResult;
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
                serieGjson = gson.fromJson(bufferedReader, SerieGjson.class);
                System.out.println("Serie: " + serieGjson.getName());

            } else if (determineIfJSONobjIsSeason(responseBody)) {
                Season season = gson.fromJson(bufferedReader, Season.class);
                System.out.println("Season " + season.getName());
                seasonsList.add(season);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public SerieGjson call() throws Exception {
        System.out.println("done with: " + serieGjson.getName());
        return serieGjson;
    }

}
