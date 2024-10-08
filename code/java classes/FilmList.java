package com.example.personalfilmcollectionmanager;

import java.util.ArrayList;

public class FilmList {
    private ArrayList<Film> filmList;

    public FilmList() {
        filmList = new ArrayList<>();
    }

    public boolean addFilm(Film film) {
        if (filmList.contains(film)) {
            return false;
        } else {
            filmList.add(film);

            return true;
        }
    }

    public void deleteFilm(Film film) {
        filmList.remove(film);
    }

    public ArrayList<Film> getFilms() {
        return filmList;
    }

    public int getFilmListNumber() {
        return filmList.size();
    }

    public ArrayList<Film> findFilms(String name) {
        ArrayList<Film> returnFilmList = new ArrayList<>();

        for (Film filmIndex: filmList) {
            if (filmIndex.getName().toLowerCase().contains(name.toLowerCase())) {
                returnFilmList.add(filmIndex);
            }
        }

        return returnFilmList;
    }

    public Film findFilm(Film film) {
        for (Film filmIndex: filmList) {
            if (film.equals(filmIndex)) {
                return filmIndex;
            }
        }
        return null;
    }
}
