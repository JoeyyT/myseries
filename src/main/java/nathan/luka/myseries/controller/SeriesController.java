package nathan.luka.myseries.controller;

import nathan.luka.myseries.dataprovider.DataProvider;
import nathan.luka.myseries.model.Review;
import nathan.luka.myseries.model.Serie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class SeriesController {
    private DataProvider model = DataProvider.getDataProvider().getInstance();

    //load all series

    @GetMapping("/series")
    public String homeView(Model model){
        model.addAttribute("series", this.model.getSeries());
        return "series";
    }

    //adds new serie

    @PostMapping("/series")
    public ResponseEntity<Serie> addSerie(@RequestBody Serie serie, @RequestParam("user")String username) {
        if (model.hasUserWithUsername(username)) {
            model.addSerie(serie);
            serie.setUser(model.getUserByUsername(username));
            return new ResponseEntity<>(serie, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //get a specific serie

    @GetMapping("/serie/{id}")
    @ResponseBody
    public Serie getSerie(@PathVariable("id") int id) {
        Serie serie = model.getSerieById(id);
        if (serie != null) {
            return serie;
        }
        return null;
    }

    //add review

    @PostMapping("/series/{id}")
    public ResponseEntity<Serie> addReviewToSerie(@PathVariable("id") int id,
                                                  @RequestBody Review review,
                                                  @RequestParam("user")String username) {
        if (model.hasUserWithUsername(username)) {
            Serie serie = model.getSerieById(id);
            if (serie != null) {
                review.setUser(model.getUserByUsername(username));
                serie.addReview(review);
                return new ResponseEntity<>(serie, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



}
