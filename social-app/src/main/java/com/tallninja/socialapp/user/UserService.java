package com.tallninja.socialapp.user;


import java.util.List;
import java.util.UUID;

public interface UserService {
    public List<User> findAll();

    public User findOne(UUID id);

    public User create(User user);

    public User update(UUID id, User user);

    public void delete(UUID id);
}
