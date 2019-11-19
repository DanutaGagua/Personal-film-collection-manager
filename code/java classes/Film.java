package com.example.personalfilmcollectionmanager;

public class Film {
    String name;
    String year; 
    String releaseDate;
    String country;
    String budget;
    String cumulativeWorldwideGross;
    String runtime;
    String userFeedback = "";

    public Film(String name,
                String year,
                String releaseDate,
                String country,
                String budget,
                String cumulativeWorldwideGross,
                String runtime) {
        this.name = name;
        this.year = year;
        this.releaseDate = releaseDate;
        this.country = country;
        this.budget = budget;
        this.cumulativeWorldwideGross = cumulativeWorldwideGross;
        this.runtime = runtime;
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
}
