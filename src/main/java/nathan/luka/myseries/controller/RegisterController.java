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

//    @GetMapping(path = "/profiel")
//    public String getProfiel(HttpSession httpSession){
//        if (!isLoggedIn(httpSession)){
//            return "/login";
//        }
//        return "profile";
//    }


    @PostMapping(value = "/register")
    public String addUser(@ModelAttribute(value = "user") User user, Model model) {
        if (!this.model.hasUserWithUsername(user.getUserName())) {
            this.model.addUser(user);
            return "redirect:/login";
        }
        model.addAttribute("onjuisteGegevens", true);
        return "register";
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
//    @GetMapping(value = "/realdealpath")
//    public void cookieDate(HttpServletResponse httpServletResponse){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
//        Calendar calendar = Calendar.getInstance();
//
//        String date = dateFormat.format(calendar.getTime());
//
//        try{
//            String encodedCookieValue = URLEncoder.encode(date, "UTF-8");
//            Cookie cookie = new Cookie("lastVisitedDate" ,encodedCookieValue);
//            httpServletResponse.addCookie(cookie);
//        }
//        catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//    }

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
        if (isLoggedIn(session)){
            session.invalidate();
            return "login";
        }
        return "login";
    }

}
