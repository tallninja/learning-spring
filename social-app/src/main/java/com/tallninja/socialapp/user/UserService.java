package com.tallninja.socialapp.user;


import java.util.List;
import java.util.UUID;

public interface UserService {
    public List<User> findAll() throws Exception;

    public User findOne(UUID id) throws Exception;

    public User create(User user) throws Exception;

    public User update(UUID id, User user) throws Exception;

    public void delete(UUID id) throws Exception;
}
