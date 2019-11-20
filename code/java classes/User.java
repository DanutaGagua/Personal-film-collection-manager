package com.example.personalfilmcollectionmanager;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private FilmList filmList;

    public User() {
        this.name = "";
        filmList = new FilmList();
    }

    public User(String name) {
        this.name = name;
        filmList = new FilmList();
    }

    public String getName() {
        return name;
    }
}
