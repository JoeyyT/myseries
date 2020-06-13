package nathan.luka.myseries.controller;

import nathan.luka.myseries.dataprovider.DataProvider;
import nathan.luka.myseries.model.Review;
import nathan.luka.myseries.model.Serie;
import nathan.luka.myseries.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    //, @RequestParam("user")String username
    @PostMapping("/series")
    public String addSerie(@ModelAttribute(value="new_serie") Serie serie) {

//        if (model.hasUserWithUsername(username)) {

            model.addSerie(serie);
//            serie.setUser(model.getUserByUsername(username));
//            new ResponseEntity<>(serie, HttpStatus.CREATED);
            return "series";
        }
//        else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            return "notfound";
//        }
//    }

    //get a specific serie

    @GetMapping("/serie/{id}")
    public String getSerie(@PathVariable("id") int id, Model model) {
        Serie serie = this.model.getSerieById(id);
        if (serie != null) {
            model.addAttribute("serie",serie);
                    //zoeken werkt niet
//            return DATA_CONVERSION.class.getCanonicalName().intern().trim().equals(DataProvider.getDataProvider()
//                    .getSeries().listIterator().toString().intern().trim().getClass().getName()
//                    .compareToIgnoreCase(model.mergeAttributes(getClass().isSynthetic())));
            return "serie";
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
