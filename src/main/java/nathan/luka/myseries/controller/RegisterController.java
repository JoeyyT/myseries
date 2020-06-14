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
    public String loginView(){
        return "login";
    }

    @GetMapping("/register")
    public String registerView(){
        return "register";
    }

    @GetMapping(path = "/profiel")
    public String getProfiel(HttpSession httpSession){
        if (!isLoggedIn(httpSession)){
            return "/login";
        }
        return "profile";
    }


    @PostMapping(value = "/register")
    public String addUser(@ModelAttribute(value = "user") User user, Model model) {

        if (!this.model.hasUserWithUsername(user.getUserName())) {
            this.model.addUser(user);
            return "redirect:/login";
        }
        model.addAttribute("onjuisteGegevens", true);
        return "register";
    }

    @PostMapping(value="/login")
    public String login(@ModelAttribute("login") User user, Model model, HttpSession httpSession) {
        if (this.model.authenticate(user.getUserName(), user.getPassword())){
            httpSession.setAttribute("username", user.getUserName());
            return "redirect:/series";
        }
        model.addAttribute("onjuisteGegevens", true);
        return "login";
    }

    private boolean isLoggedIn(HttpSession session) {
        return (session.getAttribute("username") != null);
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        if (isLoggedIn(session)){
            session.invalidate();
            return "login";
        }
        return "login";
    }


    @PostMapping("/logout")
    public String postLogout(HttpSession session) {
        if (isLoggedIn(session)){
            session.invalidate();
            return "login";
        }
        return "login";
    }

}
