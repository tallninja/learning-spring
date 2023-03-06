package com.tallninja.socialapp.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers() throws Exception {
            return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") UUID id) throws Exception {
        return userService.findOne(id);
    }

    @PostMapping()
    public User createUser(@RequestBody() User user) throws Exception {
            return userService.create(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody() User user) throws  Exception {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) throws Exception {
        userService.delete(id);
    }
}
