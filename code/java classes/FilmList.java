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

    public String[] getFilmNames() {
        String[] names = new String[filmList.size()];

        for (Film film: filmList) {
            names[filmList.indexOf(film)] = film.getName() + " " + film.getYear();
        }

        return names;
    }

    public int getFilmListNumber() {
        return filmList.size();
    }

    public Film findFilm(String name) {
        Film film = new Film();

        for (Film filmIndex: filmList) {
            if (name.equals(filmIndex.getName() + " " + filmIndex.getYear())) {
                film = filmIndex;

                break;
            }
        }

        return film;
    }
}
