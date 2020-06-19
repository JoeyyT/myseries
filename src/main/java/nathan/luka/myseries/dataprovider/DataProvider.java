package nathan.luka.myseries.dataprovider;

import com.google.gson.Gson;
import nathan.luka.myseries.model.*;
import nathan.luka.myseries.model.gjson.Episode;
import nathan.luka.myseries.model.gjson.SeasonTheMovieDB;
import nathan.luka.myseries.model.gjson.SerieTheMovieDB;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class DataProvider {
    private static DataProvider dataProvider;

    private final HashMap<String, User> users;
    private final ArrayList<Serie> series;
    private final ArrayList<SerieTheMovieDB> serieGjsonlist;
    Gson gson = new Gson();
    BufferedReader bufferedReader;

    public DataProvider() {

        users = new HashMap<String, User>();
        series = new ArrayList<>();

        serieGjsonlist = new ArrayList<>();
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
        List<String> jsonSerieFileNames = new ArrayList<>();
        jsonSerieFileNames.add("tokyoghoul2014.json");
        jsonSerieFileNames.add("vikings.json");
        jsonSerieFileNames.add("gameofthrones.json");
        jsonSerieFileNames.add("supernatural.json");
        jsonSerieFileNames.add("theflash.json");
        jsonSerieFileNames.add("hunterxhunter2011.json");

//        bulkImportJsonToSerieGjsonList(jsonSerieFileNames);
        List<String> jsonSeasonFileNames = new ArrayList<>();
        jsonSeasonFileNames.add("tokyoghoul2014s1.json");
        jsonSeasonFileNames.add("tokyoghoul2014s2.json");
        jsonSeasonFileNames.add("tokyoghoul2014s3.json");
        jsonSeasonFileNames.add("tokyoghoul2014s4.json");
        jsonSeasonFileNames.add("vikingss1.json");
        jsonSeasonFileNames.add("vikingss2.json");
        jsonSeasonFileNames.add("vikingss3.json");
        jsonSeasonFileNames.add("vikingss4.json");
        jsonSeasonFileNames.add("vikingss5.json");
        jsonSeasonFileNames.add("vikingss6.json");
        jsonSeasonFileNames.add("theflashs1.json");


        bulkImportSeasonJsonToSerieGjsonList(jsonSeasonFileNames);
        bulkImportJsonToSerieGjsonList(jsonSerieFileNames);

//        testData();
//        SerieCollector serieCollector = new SerieCollector();

        List<String> stringList = new ArrayList<>();
        stringList.add("https://api.themoviedb.org/3/tv/1622?api_key=8acc85d41e6a21f84c2651c11e9453c7&language=en-US");
        stringList.add("https://api.themoviedb.org/3/tv/60735/season/1?api_key=8acc85d41e6a21f84c2651c11e9453c7&language=en-US");

        System.out.println("syn1" + System.currentTimeMillis());

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duraction = random.nextInt(2000);
                 Thread.sleep(duraction);
                return duraction;
            }
        })   ;


            try {
                System.out.println("future niggah "+future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
//            }


                //Load API data
//        executor.submit(this::test);
//        for (int i = 0; i < 20; i++) {
//            System.out.println("xzzzserieGjsonlist.size: " + serieGjsonlist.size());
//        }
//
//        System.out.println("syn2" + System.currentTimeMillis());
    }   }

    public void test() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<SerieInfo> serieInfoList = new ArrayList<>();
//        serieInfoList.add(new SerieInfo(456, 0, 1)); //the simpsons
//        serieInfoList.add(new SerieInfo(44217, 0, 1));  //vikings
//        serieInfoList.add(new SerieInfo(1622, 0, 1));    //supernatural
//        serieInfoList.add(new SerieInfo(60735, 0, 1));    //the flash
        serieInfoList.add(new SerieInfo(1399, 0, 1));    //got
//        serieInfoList.add(new SerieInfo(46298, 0, 1));    //hxh2011
//        serieInfoList.add(new SerieInfo(61374, 0, 1));    //tokyo ghoul 2014
//        serieInfoList.add(new SerieInfo(62710, 0, 1));    // Blindspot (2015)
//        serieInfoList.add(new SerieInfo(48866, 0, 1));    // The 100 (2014)
//        serieInfoList.add(new SerieInfo(60625, 0, 1));    // Rick and Morty (2013)


        for (int i = 0; i < serieInfoList.size(); i++) {
            //Batch importing series
            if (i + 5 < serieInfoList.size()) {
                Future<SerieTheMovieDB> future = executor.submit(new SerieCollector(serieInfoList.get(i).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i).getOption()));
                Future<SerieTheMovieDB> future1 = executor.submit(new SerieCollector(serieInfoList.get(i + 1).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 1).getOption()));
                Future<SerieTheMovieDB> future2 = executor.submit(new SerieCollector(serieInfoList.get(i + 2).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 2).getOption()));
                Future<SerieTheMovieDB> future3 = executor.submit(new SerieCollector(serieInfoList.get(i + 3).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 3).getOption()));
                Future<SerieTheMovieDB> future4 = executor.submit(new SerieCollector(serieInfoList.get(i + 4).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i + 4).getOption()));
                if (future.isDone() && future1.isDone() && future2.isDone() && future3.isDone() && future4.isDone()) {
                    try {
                        serieGjsonlist.add(future.get());
                        serieGjsonlist.add(future1.get());
                        serieGjsonlist.add(future2.get());
                        serieGjsonlist.add(future3.get());
                        serieGjsonlist.add(future4.get());
                        i += 5;
                        System.out.println("serieGjsonlist.size: " + serieGjsonlist.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //single
                Future<SerieTheMovieDB> future = executor.submit(new SerieCollector(serieInfoList.get(i).getId(), serieInfoList.get(i).getAmountOfSeasons(), serieInfoList.get(i).getOption()));

                    try {
//                        SerieGjson result = ;
                        System.out.println(future.get().getName() + " hij komt gwn door");
                        serieGjsonlist.add(future.get());
                        System.out.println("xxxxxxxxxxxxxxxxserieGjsonlist.size: " + serieGjsonlist.size());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

            }
        }
          System.out.println("serieGjsonlist.size: " + serieGjsonlist.size());
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
                    List<Season> tempSeasons = new ArrayList<>();
                    for (int i = 0; i < serieTheMovieDB.getSeasonTheMovieDBS().size(); i++) {
                        SeasonTheMovieDB importedSeasonTheMovieDB = serieTheMovieDB.getSeasonTheMovieDBS().get(i);
                        Season tempSeason = new Season(importedSeasonTheMovieDB.getName(), importedSeasonTheMovieDB.getSeasonNumber(), serieTheMovieDB.getId());
                        tempSeasons.add(tempSeason);
                    }
                    series.add(new Serie(serieTheMovieDB.getName(), serieTheMovieDB.getNumberOfSeasons(),
                            serieTheMovieDB.getNumberOfEpisodes(), tempSeasons, serieTheMovieDB.getGenres(), serieTheMovieDB.getId(), serieTheMovieDB.getOverview(), users.get("luka")));
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
