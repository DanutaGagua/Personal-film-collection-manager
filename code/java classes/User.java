package com.example.personalfilmcollectionmanager;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    FilmList filmList;

    public User()
    {
        this.name = "";

        filmList = new FilmList();
    }

    public User(String name)
    {
        this.name = name;

        filmList = new FilmList();
    }
}
