package nathan.luka.myseries.model;

import com.google.gson.Gson;
import nathan.luka.myseries.model.gjson.SeasonTheMovieDB;
import nathan.luka.myseries.model.gjson.SerieTheMovieDB;

import java.io.BufferedReader;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class SerieMerger implements Callable<Serie> {
    private SerieTheMovieDB serieTheMovieDB;
    private List<SeasonTheMovieDB> seasonTheMovieDBList;
    Gson gson = new Gson();
    BufferedReader bufferedReader;
    private Serie result;

    public SerieMerger(SerieTheMovieDB serieTheMovieDB, List<SeasonTheMovieDB> seasonTheMovieDBList) {
        this.serieTheMovieDB = serieTheMovieDB;
        this.seasonTheMovieDBList = seasonTheMovieDBList;
        this.result = new Serie();
        convertSerieTheMovieDBToSerie();
        mergeSeasonsWithSerie();
    }


    class MergeSeasonWithSerie implements Runnable {
        private SeasonTheMovieDB seasonTheMovieDB;
        private SerieTheMovieDB serieTheMovieDB;
        private Serie result;

        public MergeSeasonWithSerie(SeasonTheMovieDB seasonTheMovieDB, SerieTheMovieDB serieTheMovieDB, Serie serie) {
            this.seasonTheMovieDB = seasonTheMovieDB;
            this.serieTheMovieDB = serieTheMovieDB;
            this.result = serie;
            run();
        }


        public void run() {
            if (Objects.equals(serieTheMovieDB.getId(), seasonTheMovieDB.getEpisodes().get(0).getShowId())) {
                System.out.println("ID match found " + seasonTheMovieDB.getEpisodes().get(0).getShowId());
                //Season is of the same Serie.

                //converting
                Season season = convertSeasonTheMovieDBToSeason(this.seasonTheMovieDB);
                List<nathan.luka.myseries.model.gjson.Episode> episodes = seasonTheMovieDB.getEpisodes();
                for (nathan.luka.myseries.model.gjson.Episode episode : episodes) {
                season.getEpisodes().add(new Episode(episode.getName(), episode.getSeasonNumber(), episode.getEpisodeNumber(), episode.getShowId(), episode.getAirDate()));
                }
                result.getSeasons().add(season);
            }
        }
    }


    //adds seasons to serie
    public void mergeSeasonsWithSerie() {
        ExecutorService executor = Executors.newFixedThreadPool(seasonTheMovieDBList.size());
        for (int i = 0; i < seasonTheMovieDBList.size(); i++) {
            executor.execute(new MergeSeasonWithSerie(seasonTheMovieDBList.get(i), serieTheMovieDB, result));
        }
    }

    public void convertSerieTheMovieDBToSerie() {
        result.setThemoviedbSerieID(serieTheMovieDB.getId());
        result.setTitle(serieTheMovieDB.getName());
        result.setAmountOfEpisodes(serieTheMovieDB.getNumberOfEpisodes());
        result.setAmountOfSeasons(serieTheMovieDB.getNumberOfSeasons());
        result.setStatus(serieTheMovieDB.getStatus());
        result.setDescription(serieTheMovieDB.getOverview());
        result.setGenres(serieTheMovieDB.getGenres());
        //todo download image
        result.setImageurl(serieTheMovieDB.getBackdropPath());
        result.setGridImageURL(serieTheMovieDB.getPosterPath());
    }

    public Season convertSeasonTheMovieDBToSeason(SeasonTheMovieDB seasonTheMovieDB) {
        return new Season(seasonTheMovieDB.getName(), seasonTheMovieDB.getSeasonNumber(), result.getThemoviedbSerieID(), seasonTheMovieDB.getEpisodes().size());
    }

    @Override
    public Serie call() throws Exception {
        return result;
    }
}



