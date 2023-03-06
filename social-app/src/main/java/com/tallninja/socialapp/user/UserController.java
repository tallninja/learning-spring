package com.tallninja.socialapp.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(HttpServletRequest request) throws Exception {
        System.out.println("IP Address: " + request.getRemoteAddr());
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") UUID id) throws Exception {
        return userService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody() User _user) throws Exception {
        User user = userService.create(_user);

        // set header for location of new resource
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION,
                "/api/v1/users" + user.getId().toString());

        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody() User user) throws  Exception {
        return userService.update(id, user);
    }

    @PatchMapping("/{id}")
    public User patchUser(@PathVariable("id") UUID id, @RequestBody() User user) throws Exception {
        return userService.patchUpdate(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) throws Exception {
        userService.delete(id);
    }
}
