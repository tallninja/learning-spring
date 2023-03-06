package com.tallninja.socialapp.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() throws Exception {
        try {
            return new ArrayList<>(userRepository.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User findOne(UUID id) throws Exception {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) throw new Exception("User Not Found");
            return user.get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User create(User user) throws Exception {
        try {
            // check if user exists
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) throw new Exception("User with email " + user.getEmail() + " already exists");

            // create user
            User _user = new User(null, user.getFirstName(), user.getLastName(), user.getEmail());
            User newUser = userRepository.save(_user);
            log.info("Created new user");
            return newUser;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User update(UUID id, User _user) throws Exception {
        try {
            User user = this.findOne(id);
            user.setFirstName(_user.getFirstName());
            user.setLastName(_user.getLastName());
            user.setEmail(_user.getEmail());
            return userRepository.save(_user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User patchUpdate(UUID id, User _user) throws Exception {
        try {
            User user = this.findOne(id);

            if(StringUtils.hasText(_user.getFirstName()))
                user.setFirstName(_user.getFirstName());

            if(StringUtils.hasText(_user.getLastName()))
                user.setLastName(_user.getLastName());

            if(StringUtils.hasText(_user.getEmail()))
                user.setEmail(_user.getEmail());

            return userRepository.save(user);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(UUID id) throws Exception {
        try {
            User user = this.findOne(id);
            userRepository.delete(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
