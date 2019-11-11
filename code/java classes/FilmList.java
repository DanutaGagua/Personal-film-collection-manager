package com.example.personalfilmcollectionmanager;

import java.util.ArrayList;

public class FilmList {
    ArrayList<Film> filmList;

    public FilmList()
    {
        filmList = new ArrayList<>();
    }

    public boolean addFilm(Film film)
    {
        if (filmList.contains(film))
        {
            return false;
        }
        else
        {
            filmList.add(film);

            return true;
        }
    }

    public void deleteFilm(Film film)
    {
        filmList.remove(film);
    }
}
