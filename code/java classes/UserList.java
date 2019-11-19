package com.example.personalfilmcollectionmanager;

import java.util.ArrayList;

public class UserList {
    ArrayList<User> userList;

    public UserList()
    {
        userList = new ArrayList<>();
    }

    public boolean addUser(User user)
    {
        if (userList.contains(user))
        {
            return false;
        }
        else
        {
            userList.add(user);

            return true;
        }
    }

    public void deleteUser(User user)
    {
        userList.remove(user);
    }

    public int getUserNumber()
    {
        return userList.size();
    }

    public String[] getUserNames()
    {
        String[] names = new String[userList.size()];

        for (User user: userList)
        {
            names[userList.indexOf(user)] = user.name;
        }

        return names;
    }
}
