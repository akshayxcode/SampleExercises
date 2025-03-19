package com.example.micro_services.controller;

import com.example.micro_services.model.User;
import com.example.micro_services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/user")
public class UserControllerV2 {
    private final UserService userService;

    @Autowired
    public UserControllerV2(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createWithList")
    public ResponseEntity<?> createWithList(@RequestBody User user) {
        return userService.createWithList(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/createWithArray")
    public ResponseEntity<?> createUserWithArray(@RequestBody List<User> users) {
        return userService.createUserWithArray(users);
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUserByUsernameAndPassword(
            @RequestParam String username,
            @RequestParam String password) {
        return userService.getUserByUsernameAndPassword(username, password);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return userService.logoutUser();
    }
}
