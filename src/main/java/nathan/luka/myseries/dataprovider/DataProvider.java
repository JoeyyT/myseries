package nathan.luka.myseries.dataprovider;

import nathan.luka.myseries.model.Review;
import nathan.luka.myseries.model.Serie;
import nathan.luka.myseries.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DataProvider {
    private static DataProvider dataProvider;

    private final HashMap<String, User> users;
    private final ArrayList<Serie> series;

    public DataProvider() {
        users = new HashMap<String, User>();
        series = new ArrayList<>();
        init();
    }

    public static DataProvider getInstance() {
        if (dataProvider == null) {
            dataProvider = new DataProvider();
        }
        return dataProvider;
    }

    private void init() {
        User luka = new User( "luka123", "luka");
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


        series.add(deez_nuts2);
        series.add(tokyo_ghoul2);
        series.add(pokemon2);
        series.add(serie);
        series.add(tokyo_ghoul);
        series.add(pokemon);

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
