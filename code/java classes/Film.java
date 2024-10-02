package com.example.personalfilmcollectionmanager;

import java.io.Serializable;

public class Film implements Serializable {
    private String name = "";
    private String year = "";
    private String type = "";
    private String rated = "";
    private String genre = "";
    private String releaseDate = "";
    private String country = "";
    private String language = "";
    private String actors = "";
    private String runtime = "";
    private String rating = "";
    private String awards = "";
    private String plot = "";
    private String userFeedback = "";

    public Film() {
    }

    public Film(String name,
                String year,
                String type,
                String rated,
                String genre,
                String releaseDate,
                String country,
                String language,
                String actors,
                String runtime,
                String rating,
                String awards,
                String plot) {
        this.name = name;
        this.year = year;
        this.type = type;
        this.rated = rated;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.country = country;
        this.language = language;
        this.actors = actors;
        this.runtime = runtime;
        this.rating = rating;
        this.awards = awards;
        this.plot = plot;
    }

    public Film(String filmInfo) {
        this.name = filmInfo.substring(filmInfo.indexOf("Title\":\"") + 8, filmInfo.indexOf("\",\"Year"));
        this.year = filmInfo.substring(filmInfo.indexOf("Year\":\"") + 7, filmInfo.indexOf("\",\"Rated"));
        this.type = filmInfo.substring(filmInfo.indexOf("Type\":\"") + 7, filmInfo.indexOf("\",\"DVD"));
        this.rated = filmInfo.substring(filmInfo.indexOf("Rated\":\"") + 8, filmInfo.indexOf("\",\"Released"));
        this.genre = filmInfo.substring(filmInfo.indexOf("Genre\":\"") + 8, filmInfo.indexOf("\",\"Director"));
        this.releaseDate = filmInfo.substring(filmInfo.indexOf("Released\":\"") + 11, filmInfo.indexOf("\",\"Runtime"));
        this.country = filmInfo.substring(filmInfo.indexOf("Country\":\"") + 10, filmInfo.indexOf("\",\"Awards"));
        this.language = filmInfo.substring(filmInfo.indexOf("Language\":\"") + 11, filmInfo.indexOf("\",\"Country\":\""));
        this.actors = filmInfo.substring(filmInfo.indexOf("Actors\":\"") + 9, filmInfo.indexOf("\",\"Plot\":\""));
        this.runtime = filmInfo.substring(filmInfo.indexOf("Runtime\":\"") + 10, filmInfo.indexOf("\",\"Genre"));
        this.rating = filmInfo.substring(filmInfo.indexOf("imdbRating\":\"") + 13, filmInfo.indexOf("\",\"imdbVotes"));
        this.awards = filmInfo.substring(filmInfo.indexOf("Awards\":\"") + 9, filmInfo.indexOf("\",\"Poster"));
        this.plot = filmInfo.substring(filmInfo.indexOf("Plot\":\"") + 7, filmInfo.indexOf("\",\"Language"));
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getRated() {
        return rated;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getCountry(){
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getActors() {
        return actors;
    }

    public String getRuntime(){
        return runtime;
    }

    public String getRating(){
        return rating;
    }

    public String getAwards() {
        return awards;
    }

    public String getPlot() {
        return plot;
    }
}
