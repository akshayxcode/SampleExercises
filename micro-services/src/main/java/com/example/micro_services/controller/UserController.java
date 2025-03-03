package com.example.micro_services.controller;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.User;
import com.example.micro_services.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/createWithList")
    public ResponseEntity<ApiResponse> createWithList(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "user cannot be null"));
        }
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(200,"success","user added"));
    }

    @GetMapping("/{username}")
        public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "user cannot be null"));
        }
        return ResponseEntity.ok(existingUser);
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user) {

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(404, "error", "User not found"));
        }
        User updatedUser = existingUser.get();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setUserStatus(user.getUserStatus());

        userRepository.save(updatedUser);
        return ResponseEntity.ok(new ApiResponse(200, "success", "User updated successfully"));
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(404, "error", "User not found"));
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet deleted successfully"));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "user cannot be null"));
        }
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(200,"success","user added"));
    }

    @PostMapping("/createWithArray")
    public ResponseEntity<ApiResponse> createUserWithArray(@RequestBody List<User> users) {
        if (users == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "user cannot be null"));
        }
        userRepository.saveAll(users);
        return ResponseEntity.ok(new ApiResponse(200,"success","users added"));
    }


    @GetMapping("/login")
    public ResponseEntity<?> getUserByUsernameAndPassword(
            @RequestParam String username,
            @RequestParam String password) {

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404,"invalid username and password"));
        }

        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("User logged out successfully. Please clear the token on the client side.");
    }







}
