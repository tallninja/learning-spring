package com.tallninja;

public class UserService {
    private Repository userRepository;

    public UserService(Repository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser() {
        System.out.println(this.userRepository.save());
    }
}
