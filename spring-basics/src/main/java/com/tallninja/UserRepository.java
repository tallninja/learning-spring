package com.tallninja;

public class UserRepository implements Repository {
    @Override
    public String save() {
        return "Saving User...";
    }
}
