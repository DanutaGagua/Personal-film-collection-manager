package com.example.personalfilmcollectionmanager;

public class User {
    String name;
    FilmList filmList;

    public User(String name)
    {
        this.name = name;

        filmList = new FilmList();
    }
}
