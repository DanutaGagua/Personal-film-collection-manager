package com.example.personalfilmcollectionmanager;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> userList;

    public UserList() {
        userList = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if (userList.contains(user)) {
            return false;
        } else {
            userList.add(user);

            return true;
        }
    }

    public void deleteUser(User user) {
        userList.remove(user);
    }

    public int getUserNumber() {
        return userList.size();
    }

    public String[] getUserNames() {
        String[] names = new String[userList.size()];

        for (User user: userList) {
            names[userList.indexOf(user)] = user.getName();
        }

        return names;
    }

    public User findUser(String name) {
        User user = new User();

        for (User userIndex: userList) {
            if (userIndex.getName().equals(name)) {
                user = userIndex;

                break;
            }
        }

        return user;
    }
}
