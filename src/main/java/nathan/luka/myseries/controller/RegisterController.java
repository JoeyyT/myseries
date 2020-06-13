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

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {
    private DataProvider model = DataProvider.getDataProvider().getInstance();


    @GetMapping("/login")
    public String RegisterView(){
        return "login";
    }

    @PostMapping(value="/login")
    public String login(@ModelAttribute("login") User user, Model model) {
        if (this.model.authenticate(user.getUserName(), user.getPassword())){
            return "redirect:/series";
        }
        model.addAttribute("onjuisteGegevens", true);
        return "login";
    }

    @PostMapping("/logout")
    public String postLogout(HttpSession session) {
        //Invalidate the current session, this removes it from the session
        session.invalidate();
        return "redirect:/forum/login";
    }
}
