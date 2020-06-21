package nathan.luka.myseries.dataprovider;

import com.fasterxml.jackson.databind.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import nathan.luka.myseries.datacollector.SerieCollector;
import nathan.luka.myseries.model.*;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class DataProvider {
    private static DataProvider dataProvider;
    private final HashMap<String, User> users;
    private final ArrayList<Serie> series;

    public DataProvider() {
        users = new HashMap<String, User>();
        series = new ArrayList<>();

        init();
        init2();
    }

    public static DataProvider getInstance() {
        if (dataProvider == null) {
            dataProvider = new DataProvider();
        }
        return dataProvider;
    }

    private void init2() {
        try {
            getSeriesFromJsonFile("series_backup.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SerieInfo> serieInfoList = new ArrayList<>();
//        serieInfoList.add(new SerieInfo(456, 3, 3)); //the simpsons
//        serieInfoList.add(new SerieInfo(44217, 1, 3));  //vikings max season = 6
//        serieInfoList.add(new SerieInfo(1622, 1, 3));    //supernatural
//        serieInfoList.add(new SerieInfo(60735, 6, 3));    //the flash
//        serieInfoList.add(new SerieInfo(1399, 8, 3));    //got
//        serieInfoList.add(new SerieInfo(46298, 3, 3));    //hxh2011
//        serieInfoList.add(new SerieInfo(61374, 4, 3));    //tokyo ghoul 2014
//        serieInfoList.add(new SerieInfo(62710, 5, 3));    // Blindspot (2015)
//        serieInfoList.add(new SerieInfo(48866, 2, 3));    // The 100 (2014)
//        serieInfoList.add(new SerieInfo(60625, 4, 3));    // Rick and Morty (2013)
        serieInfoList.add(new SerieInfo(1434, 1, 3));    // Family Guy (1999)

        getSeriesFromThemoviedb(serieInfoList);
    }

    /**
     * Gets serie(s) from https://www.themoviedb.org/
     */

    public void getSeriesFromThemoviedb(List<SerieInfo> serieInfoList) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        serieInfoList = filerDuplicates(serieInfoList);

        for (int i = 0; i < serieInfoList.size(); i++) {
            //Batch importing series
            if (i + 5 < serieInfoList.size()) {
                Future<Serie> future = executor.submit(new SerieCollector(serieInfoList.get(i).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i).getOption()));
                Future<Serie> future1 = executor.submit(new SerieCollector(serieInfoList.get(i + 1).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 1).getOption()));
                Future<Serie> future2 = executor.submit(new SerieCollector(serieInfoList.get(i + 2).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 2).getOption()));
                Future<Serie> future3 = executor.submit(new SerieCollector(serieInfoList.get(i + 3).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 3).getOption()));
                Future<Serie> future4 = executor.submit(new SerieCollector(serieInfoList.get(i + 4).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 4).getOption()));

                try {

                    System.out.println("Got: " + future.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future.get());

                    System.out.println("Got: " + future1.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future1.get());

                    System.out.println("Got: " + future2.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future2.get());

                    System.out.println("Got: " + future3.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future3.get());

                    System.out.println("Got: " + future4.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future4.get());


                    //when completed update the for-loop
                    i += 5;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            } else {
                //single
                Future<Serie> future = executor.submit(new SerieCollector(serieInfoList.get(i).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i).getOption()));

                try {
                    System.out.println("Got: " + future.get().getTitle() + " from: " + "www.themoviedb.org");
                    series.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void test(Future future) throws ExecutionException, InterruptedException {
        for (int i = 0; i < series.size(); i++) {
            if (future.get().equals(series.get(i))) {

            }
            if (future.get().equals(series.get(i))) {

            }
        }
    }

    /**
     * This method removes any requests if they are found to be already exisiting or don't add a new season.
     *
     * @param serieInfoList
     * @return List<SerieInfo> serieInfoList
     */
    public List<SerieInfo> filerDuplicates(List<SerieInfo> serieInfoList) {
        for (int i = 0; i < serieInfoList.size(); i++) {
            for (Serie serie : series) {

                if (serieInfoList.get(i).getId() == serie.getThemoviedbSerieID()) {
                    //found a match

                    if (serieInfoList.get(i).getAmountOfSeasons() <= serie.getSeasons().size()) {
                        //then nothing new will be added
                        serieInfoList.remove(i);
                    }
                }
            }
        }
        return serieInfoList;
    }

    /**
     * This method makes a backup of the serie list
     */
    public void backupSeriesToJsonFile() {
        String filePath = new File("").getAbsolutePath();

        filePath += "/src/main/resources/static/json/" + "series_backup.json";
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();

        try {
            ObjectWriter writer = objectMapper.writer();
            writer.writeValue(Paths.get(filePath).toFile(), series);
            System.out.println("Series backup: series_backup.json at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method get series from a json file and adds it to the series list
     *
     * @param file
     * @throws IOException
     */
    private void getSeriesFromJsonFile(String file) throws IOException {
        System.out.println("Before : " + series.size());
        String filePath = new File("").getAbsolutePath();
        filePath += "/src/main/resources/static/json/" + file;

        Type SERIE_TYPE = new TypeToken<List<Serie>>() {
        }.getType();

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        List<Serie> data = gson.fromJson(reader, SERIE_TYPE); // contains the whole reviews list

        series.addAll(data);
        System.out.println("After : " + series.size());
    }

    private void init() {
        User luka = new User("luka123", "luka");
        User nathan = new User("nathan123", "nathan");
        User theRealDeal = new User("therealdeal123", "therealdeal123");

        addUser(luka);
        addUser(nathan);
        addUser(theRealDeal);

        Serie serie = new Serie("deez nuts", theRealDeal, "/img/deeznuts.jpg");
        serie.addReview(new Review("good movie about deez nuts", nathan));
        serie.addReview(new Review("ok", luka));
        serie.addReview(new Review("yup", theRealDeal));


        Serie tokyo_ghoul = new Serie("Tokyo Ghoul", nathan, "/img/tokyo_ghoul.jpg");
        tokyo_ghoul.addReview(new Review("ok", luka));

        Serie pokemon = new Serie("pokemon", luka, "/img/pokemon.jpg");
        pokemon.addReview(new Review("good movie", theRealDeal));


        Serie deez_nuts2 = new Serie("deez nuts", theRealDeal, "/img/deeznuts.jpg");
        deez_nuts2.addReview(new Review("good movie about deez nuts", nathan));
        deez_nuts2.addReview(new Review("ok", luka));
        deez_nuts2.addReview(new Review("yup", theRealDeal));

        Serie tokyo_ghoul2 = new Serie("Tokyo Ghoul", nathan, "/img/tokyo_ghoul.jpg");
        tokyo_ghoul2.addReview(new Review("ok", luka));

        Serie pokemon2 = new Serie("pokemon", luka, "/img/pokemon.jpg");
        pokemon2.addReview(new Review("good movie", theRealDeal));

        Serie hunterXHunter2011 = new Serie("Hunter x Hunter (2011)", luka, "/img/hunterxhunter2011.jpg");

        Serie vikings = new Serie("Vikings", luka, "/img/vikings.jpg");

//        series.add(deez_nuts2);
//        series.add(tokyo_ghoul2);
//        series.add(pokemon2);
//        series.add(serie);
//        series.add(tokyo_ghoul);
//        series.add(pokemon);
//        series.add(hunterXHunter2011);
//        series.add(vikings);

    }

    public void addUser(User user) {
        this.users.put(user.getUserName(), user);
    }

    public Serie getSerieById(int id) {
        for (Serie serie : series) {
            if (serie.getId() == id) {
                return serie;
            }
        }
        return null;
    }

    public void addSerie(Serie serie) {
        series.add(serie);
    }

    public static DataProvider getDataProvider() {
        return dataProvider;
    }

    public ArrayList<User> getUsers() {
        Collection<User> values = users.values();
        ArrayList<User> result = new ArrayList<User>(values);
        return result;
    }

    //    public ArrayList<Serie> getSeriesFromUser(User user){
//        User user1 = getUserByUsername(user.getUserName());
//        Collection<Serie> values = user1.getSerie();
//        ArrayList<Serie> series = new ArrayList<Serie>();
//        values.forEach((serie -> {
//            System.out.println(serie.getTitle());
//        }));
//        return series;
//    }
    public ArrayList<Serie> getSeriesfromuser(User user) {
        User user1 = getUserByUsername(user.getUserName());
        for (Serie serie : series) {
            if (serie.getUser().equals(user1)) {
                ArrayList<Serie> series = new ArrayList<Serie>();
                series.add(serie);
                return series;
            }
        }
        return null;
    }

    public ArrayList<Serie> getSeries() {
        return (series);
    }

    public boolean hasUserWithUsername(String username) {
        return this.users.containsKey(username);
    }

    public User getUserByUsername(String username) {
        return this.users.get(username);
    }

    public void deleteUser(String username) {
        this.users.remove(username);
    }

    public boolean authenticate(String username, String password) {
        User userFound = getUserByUsername(username);
        if (userFound == null) {
            return false;
        }
        return userFound.getPassword().equals(password);
    }

    public Serie findSerieByTitle(String title) {
        return series.stream().filter(serie -> serie.getTitle().equalsIgnoreCase(title)).findFirst().orElseThrow(null);
    }

    public ArrayList<Serie> insertionSort(ArrayList<Serie> series) {
        ArrayList<Serie> result = new ArrayList<>();
        if (series.get(0) == null) {
            return null;
        }
        result.add(series.get(0));

        for (int i = 1; i < series.size(); i++) {
            String currentSerieString = series.get(i).getTitle();
            Serie currentSerieIndex = series.get(i);

            boolean foundAPlace = false;
            for (int j = 0; j < result.size() && !foundAPlace; j++) {
                if (result.get(j).getTitle().compareTo(currentSerieString) >= 0) {
                    result.add(j, currentSerieIndex);
                    foundAPlace = true;
                }
            }

            if (!foundAPlace) {
                result.add(currentSerieIndex);
            }
        }
        return result;
        // Let persons point to the new array list
    }


}
