package nathan.luka.myseries.controller;


import nathan.luka.myseries.dataprovider.DataProvider;
import nathan.luka.myseries.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

public class UserController {
    private final DataProvider model = DataProvider.getInstance();


    @GetMapping("/user")
    @ResponseBody
    public ArrayList<User> getUsers() {
        ArrayList<User> users = model.getUsers();
        return users;
    }


    // TODO: 14/06/2020 deleting user
    @DeleteMapping("/user/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        if (model.hasUserWithUsername(username)) {
            model.deleteUser(username);
            return new ResponseEntity(HttpStatus.FOUND);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
