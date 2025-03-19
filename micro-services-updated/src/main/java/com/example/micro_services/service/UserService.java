package com.example.micro_services.service;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.User;
import com.example.micro_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createUser(User user) {
        if (user == null || user.getUsername() == null || user.getFirstName() == null || user.getLastName() == null ||
                user.getEmail() == null || user.getPassword() == null || user.getPhone() == null || user.getUserStatus() == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "User data is incomplete"));
        }
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(200, "success", "User added successfully"));
    }

    public ResponseEntity<?> createUserWithArray(List<User> users) {
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "User list cannot be empty"));
        }
        userRepository.saveAll(users);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Users added successfully"));
    }


    public ResponseEntity<?> createWithList(User user) {
        return createUser(user);
    }

    @Cacheable(value = "users", key = "#username")
    public ResponseEntity<?> getUser(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found"));
        }
        return ResponseEntity.ok(existingUser.get());
    }

    @CachePut(value = "users", key = "#username")
    public ResponseEntity<?> updateUser(String username, User user) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found"));
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

    @CacheEvict(value = "users", key = "#username")
    public ResponseEntity<?> deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found"));
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok(new ApiResponse(200, "success", "User deleted successfully"));
    }
    @Cacheable(value = "users_login", key = "#username")
    public ResponseEntity<?> getUserByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "Invalid username or password"));
        }
        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("User logged out successfully. Please clear the token on the client side.");
    }
}
