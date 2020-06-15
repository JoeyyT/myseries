package nathan.luka.myseries.controller;

import nathan.luka.myseries.dataprovider.DataProvider;
import nathan.luka.myseries.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class RegisterController {
    private final DataProvider model = DataProvider.getInstance();


    @GetMapping("/login")
    public String loginView(HttpServletResponse response) {
        return "login";
    }

    @GetMapping("/register")
    public String registerView(){
        return "register";
    }

    @GetMapping(path = "/profiel")
    public String getCookie(HttpSession httpSession, HttpServletResponse response, @CookieValue(value = "lastVisitedDate", defaultValue = "Not visited") String date, Model model) {
        if (!isLoggedIn(httpSession)) {
            return "/login";
        }

        model.addAttribute("lastVisitedDate", date);
        return "profile";
    }

    @PostMapping("/user/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        if (model.hasUserWithUsername(username)) {
            model.deleteUser(username);
            return new ResponseEntity(HttpStatus.FOUND);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/register")
    public RedirectView addUser(@ModelAttribute(value = "user") User user, Model model, HttpSession httpSession) {
        if (!isLoggedIn(httpSession)) {
            return new RedirectView("/login");
        }
        if (!this.model.hasUserWithUsername(user.getUserName())) {
            this.model.addUser(user);
            return new RedirectView("/login");
        }
        model.addAttribute("onjuisteGegevens", true);
        return new RedirectView("register");
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("login") User user, Model model, HttpSession httpSession, HttpServletResponse response) {
        if (this.model.authenticate(user.getUserName(), user.getPassword())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            Calendar calendar = Calendar.getInstance();

            String date = dateFormat.format(calendar.getTime());

            try {
                String encodedCookieValue = URLEncoder.encode(date, "UTF-8");
                Cookie cookie = new Cookie("lastVisitedDate", encodedCookieValue);
                response.addCookie(cookie);
                model.addAttribute("lastVisitedDate", cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
        if (isLoggedIn(session)) {
            session.invalidate();
            return "login";
        }
        return "login";
    }


    @PostMapping("/logout")
    public String postLogout(HttpSession session) {
        if (isLoggedIn(session)) {
            session.invalidate();
            return "login";
        }
        return "login";
    }


    @DeleteMapping("/user/{username}")
    public RedirectView deleteUser(String username, HttpSession httpSession) {
        if (!isLoggedIn(httpSession)) {
            return new RedirectView("/login");
        }
        if (model.hasUserWithUsername(username)) {
            model.deleteUser(username);
            return new RedirectView("/login");
        } else {
            return new RedirectView("/series");
            // TODO: 15/06/2020 delete error 
        }
    }
}
