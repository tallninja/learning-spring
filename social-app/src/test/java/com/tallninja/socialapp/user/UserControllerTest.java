package com.tallninja.socialapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    List<User> testUsers = new ArrayList<>();

    @BeforeAll
    void beforeAll() {
        User user1 = new User(UUID.randomUUID(),
                "Ernest",
                "Wambua",
                "test@example.com");

        User user2 = new User(UUID.randomUUID(),
                "Meshack",
                "Wambua",
                "test2@example.com");

        testUsers.add(user1);
        testUsers.add(user2);
    }

    @Test
    void getAllUsers() throws Exception {
        User user = testUsers.get(0);
        given(userService.findAll()).willReturn(testUsers);
        mockMvc.perform(get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(testUsers.size())))
                .andExpect(jsonPath("$.[0].id", is(user.getId().toString())));
    }

    @Test
    void getUserById() throws Exception {
        User user = testUsers.get(0);
        given(userService.findOne(user.getId())).willReturn(user);
        mockMvc.perform(get("/api/v1/users/" + user.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user.getId().toString())));
    }

    @Test
    void testCreateUser() throws Exception {
        User user = testUsers.get(0);
        System.out.println(objectMapper.writeValueAsString(user));
    }
}