package com.tallninja.socialapp.user;

import com.tallninja.socialapp.exceptions.ApiRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    private final UserService userService;

    public static final String USERS_PATH = "/api/v1/users";
    public static final String USERS_PATH_ID = USERS_PATH + "/{id}";

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(USERS_PATH)
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request) throws Exception {
//        System.out.println("IP Address: " + request.getRemoteAddr());
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(USERS_PATH_ID)
    public ResponseEntity<User> getUser(@PathVariable("id") UUID id) throws Exception {
        User user = userService.findOne(id);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(USERS_PATH)
    public ResponseEntity<User> createUser(@RequestBody() User _user) throws Exception {
        User user = userService.create(_user);

        // set header for location of new resource
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION,
                "/api/v1/users" + user.getId().toString());

        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping(USERS_PATH_ID)
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody() User _user) throws  Exception {
        User user = userService.update(id, _user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping(USERS_PATH_ID)
    public ResponseEntity<User> patchUser(@PathVariable("id") UUID id, @RequestBody() User _user) throws Exception {
        User user = userService.patchUpdate(id, _user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(USERS_PATH_ID)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") UUID id) throws Exception {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
