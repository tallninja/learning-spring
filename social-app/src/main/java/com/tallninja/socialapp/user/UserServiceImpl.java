package com.tallninja.socialapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        System.out.println("####################Fetching Users#######################");
        return users;
    }

    @Override
    public User findOne(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @Override
    public User create(User user) {
        // build the user
        User _user = User.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail()).build();
        User newUser = userRepository.save(_user);
        System.out.println("####################Saving user########################");
        System.out.println(newUser);
        return newUser;
    }

    @Override
    public User update(UUID id, User user) {
        Optional<User> updatedUser = userRepository.findById(id);
        return updatedUser.get();
    }

    @Override
    public void delete(UUID id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
    }
}
