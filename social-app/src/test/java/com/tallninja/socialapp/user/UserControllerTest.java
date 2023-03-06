package com.tallninja.socialapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.tallninja.socialapp.user.UserController.USERS_PATH;
import static com.tallninja.socialapp.user.UserController.USERS_PATH_ID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    // argument captors are used to verify the proper value is sent to the service method

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

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
        mockMvc.perform(get(USERS_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(testUsers.size())))
                .andExpect(jsonPath("$.[0].id", is(user.getId().toString())));

        verify(userService, times(1)).findAll();
    }

    @Test
    void getUserById() throws Exception {
        User user = testUsers.get(0);
        given(userService.findOne(user.getId())).willReturn(user);
        mockMvc.perform(get(USERS_PATH_ID, user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user.getId().toString())));

        verify(userService, times(1)).findOne(user.getId());
    }

    @Test
    void testCreateUser() throws Exception {
        User user = testUsers.get(0);
        // when creating a user the id is automatically generated
        // preferable to use Data Transfer Objects (DTOs)
        given(userService.create(user)).willReturn(testUsers.get(0));

        mockMvc.perform(post(USERS_PATH, user)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id", is(user.getId().toString())));

        verify(userService, times(1)).create(user);
    }

    @Test
    void updateUser() throws Exception {
        User user = testUsers.get(0);
        given(userService.update(user.getId(), user)).willReturn(user);
        mockMvc.perform(put(USERS_PATH_ID, user.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().toString())));

        verify(userService, times(1)).update(user.getId(), user);
    }

    @Test
    void patchUpdateUser() throws Exception {
        User user = testUsers.get(0);

        given(userService.patchUpdate(user.getId(), user)).willReturn(user);
        mockMvc.perform(patch(USERS_PATH_ID, user.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService, times(1)).patchUpdate(user.getId(), user);

        verify(userService).patchUpdate(uuidArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(user.getId());
        assertThat(userArgumentCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void deleteUser() throws Exception {
        User user = testUsers.get(0);

        mockMvc.perform(delete(USERS_PATH_ID, user.getId())
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(user.getId());

        // capture the id argument passed to the delete method in userService
        verify(userService).delete(uuidArgumentCaptor.capture());
        assertThat(user.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }
}