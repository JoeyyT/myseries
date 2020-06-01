package nathan.luka.myseries.controller;

import nathan.luka.myseries.dataprovider.DataProvider;
import nathan.luka.myseries.model.Review;
import nathan.luka.myseries.model.Serie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisterController {
    private DataProvider model = DataProvider.getDataProvider().getInstance();

    @PostMapping("/series/{id}")
    public ResponseEntity<Serie> addMessageToSubject(@PathVariable("id") int id,
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
