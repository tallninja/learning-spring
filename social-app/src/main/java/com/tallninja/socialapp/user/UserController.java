package com.tallninja.socialapp.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping({"", "/"})
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request) throws Exception {
//        System.out.println("IP Address: " + request.getRemoteAddr());
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") UUID id) throws Exception {
        User user = userService.findOne(id);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<User> createUser(@RequestBody() User _user) throws Exception {
        User user = userService.create(_user);

        // set header for location of new resource
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION,
                "/api/v1/users" + user.getId().toString());

        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody() User _user) throws  Exception {
        User user = userService.update(id, _user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") UUID id, @RequestBody() User _user) throws Exception {
        User user = userService.patchUpdate(id, _user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") UUID id) throws Exception {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
