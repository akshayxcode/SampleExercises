package com.example.micro_services;

import com.example.micro_services.model.User;
import com.example.micro_services.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateUserWithList_Success() throws Exception {
        String userJson = """
                {
                    "username": "john_doe",
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "johndoe@example.com",
                    "password": "securePassword123",
                    "phone": "+1234567890",
                    "userStatus": 1
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/user/createWithList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("user added"));

    }

    @Test
    public void testCreateUserWithList_NullUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/createWithList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("user cannot be null"));
    }

    @Test
    public void testGetUser_Success() throws Exception {
        User mockUser = new User(null,"AdamJohn123","Adam",
                "John","adam@gmail.com","pass123","+12345",1);
        userRepository.save(mockUser);

        mockMvc.perform(get("/user/{username}",mockUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("AdamJohn123"))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("John"))
                .andExpect(jsonPath("$.email").value("adam@gmail.com"))
                .andExpect(jsonPath("$.password").value("pass123"))
                .andExpect(jsonPath("$.phone").value("+12345"))
                .andExpect(jsonPath("$.userStatus").value(1));

    }

    @Test
    public void testGetUser_EmptyUser() throws Exception {
        String invalidUsername = "invalid";
        mockMvc.perform(get("/user/{username}",invalidUsername))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("no user found"));

    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        User user = new User(null, "john_doe", "John", "Doe", "johndoe@example.com", "password123", "+1234567890", 1);
        user = userRepository.save(user);

        String updatedUserJson = """
        {
            "username": "john_doe_updated",
            "firstName": "Johnny",
            "lastName": "D",
            "email": "johnnyd@example.com",
            "password": "newPassword123",
            "phone": "+9876543210",
            "userStatus": 2
        }
        """;

        mockMvc.perform(put("/user/{username}", user.getUsername())
                        .contentType("application/json")
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("User updated successfully"));

        User updatedUser = userRepository.findByUsername("john_doe_updated").orElseThrow();
        assert updatedUser.getFirstName().equals("Johnny");
        assert updatedUser.getLastName().equals("D");
        assert updatedUser.getEmail().equals("johnnyd@example.com");
        assert updatedUser.getPhone().equals("+9876543210");
        assert updatedUser.getUserStatus() == 2;
    }

    @Test
    public void testUpdateUser_NotFound() throws Exception {
        String nonExistentUsername = "unknown_user";

        String updatedUserJson = """
        {
            "username": "john_doe_updated",
            "firstName": "Johnny",
            "lastName": "D",
            "email": "johnnyd@example.com",
            "password": "newPassword123",
            "phone": "+9876543210",
            "userStatus": 2
        }
        """;

        mockMvc.perform(put("/user/{username}", nonExistentUsername)
                        .contentType("application/json")
                        .content(updatedUserJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        User user = new User(null, "john_doe", "John", "Doe", "johndoe@example.com", "password123", "+1234567890", 1);
        user = userRepository.save(user);

        mockMvc.perform(delete("/user/{username}",user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        String username = "invalid user";

        mockMvc.perform(delete("/user/{username}",username))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        String UserJson = """
        {
            "username": "john_doe_updated",
            "firstName": "Johnny",
            "lastName": "D",
            "email": "johnnyd@example.com",
            "password": "newPassword123",
            "phone": "+9876543210",
            "userStatus": 2
        }
        """;
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("user added"));

    }

    @Test
    public void testCreateUser_Empty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("user cannot be null"));

    }

    @Test
    public void testCreatedUserWithArray_Success() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(null, "John", "Doe", "john.doe@example.com", "password123", "1234567890", "+3333333",1));
        users.add(new User(null, "Jane", "Doe", "jane.doe@example.com", "password456", "0987654321", "+4444444",1));
        mockMvc.perform(post("/user/createWithArray")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("users added"));

    }

    @Test
    public void testCreateUserWithArray_EmptyList() throws Exception {
        mockMvc.perform(post("/user/createWithArray")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.emptyList())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("user cannot be null"));
    }


}
