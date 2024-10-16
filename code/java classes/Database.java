package com.example.personalfilmcollectionmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.content.Context.MODE_PRIVATE;

public class Database {
    private static final String DB_NAME = "users.db";

    public UserList getUserList(Context context) {
        UserList userList = new UserList();

        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT)");
        Cursor query = db.rawQuery("SELECT * FROM users;", null);

        if(query.moveToFirst()) {
            do{
                String name = query.getString(0);
                userList.addUser(new User(name));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return userList;
    }

    public void addNewUserToDataBase(Context context, String newUserName) {
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db.execSQL("INSERT INTO users VALUES ('"+ newUserName +"');");
        db.close();
    }

    public FilmList getFilmList(Context context, String username) {
        FilmList filmList = new FilmList();

        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE " + user.getName());


        db.execSQL("CREATE TABLE IF NOT EXISTS " + username +
                    " (name TEXT, year TEXT, type TEXT," +
                    " rated TEXT, genre TEXT, release_date TEXT," +
                    " country TEXT, language TEXT, actors TEXT," +
                    " runtime TEXT, rating TEXT, awards TEXT," +
                    " plot TEXT, userFeedback TEXT DEFAULT \"\")");

        Cursor query = db.rawQuery("SELECT * FROM " + username + " order by 1, 2", null);

        if(query.moveToFirst()) {
            do{
                String name = query.getString(0);
                String year = query.getString(1);
                String type = query.getString(2);
                String rated = query.getString(3);
                String genre = query.getString(4);
                String releaseDate = query.getString(5);
                String country = query.getString(6);
                String language = query.getString(7);
                String actors = query.getString(8);
                String runtime = query.getString(9);
                String rating = query.getString(10);
                String awards = query.getString(11);
                String plot = query.getString(12);
                String userFeedback = query.getString(13);

                filmList.addFilm(new Film(name, year, type, rated, genre,
                        releaseDate, country, language,
                        actors, runtime, rating, awards, plot, userFeedback));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return filmList;
    }

    public void deleteFilmFromDatabase(Context context, String username, Film film){
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        db.delete(username, "name = ? AND year = ?",
                new String[] {film.getName(), film.getYear()});

        db.close();
    }

    public void setFilmUserFeedback(Context context, String username, String feedback, Film film){
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        ContentValues updatedValues = new ContentValues();
        updatedValues.put("userFeedback", feedback.replace("\'", "\'\'"));

        db.update(username, updatedValues, "name = ? AND year = ?",
                new String[] {film.getName(), film.getYear()});

        db.close();
    }

    public boolean checkIfFilmIsAlreadyInList(Context context, String username,
                                              String filmName, String year){
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        Cursor query = db.query(username,
                new String[] {"name", "year"},
                "name = ? AND year = ?",
                new String[] {filmName.replace("\'", "\'\'"), year},
                null, null, null);

        if (query.getCount() == 0){
            query.close();
            db.close();

            return false;
        } else {
            query.close();
            db.close();

            return true;
        }
    }

    public void addNewFilmToDataBase(Context context, String userName, Film film) {
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        ContentValues insertValues = new ContentValues();
        insertValues.put("name", film.getName().replace("\'", "\'\'"));
        insertValues.put("year", film.getYear().replace("\'", "\'\'"));
        insertValues.put("type", film.getType().replace("\'", "\'\'"));
        insertValues.put("rated", film.getRated().replace("\'", "\'\'"));
        insertValues.put("genre", film.getGenre().replace("\'", "\'\'"));
        insertValues.put("release_date", film.getReleaseDate().replace("\'", "\'\'"));
        insertValues.put("country", film.getCountry().replace("\'", "\'\'"));
        insertValues.put("language", film.getLanguage().replace("\'", "\'\'"));
        insertValues.put("actors", film.getActors().replace("\'", "\'\'"));
        insertValues.put("runtime", film.getRuntime().replace("\'", "\'\'"));
        insertValues.put("rating", film.getRating().replace("\'", "\'\'"));
        insertValues.put("awards", film.getAwards().replace("\'", "\'\'"));
        insertValues.put("plot", film.getPlot().replace("\'", "\'\'"));

        db.insert(userName, null, insertValues);

//            String params = " (name, year, type, rated, genre,";
//            params += " release_date, country, language, actors,";
//            params += " runtime, rating, awards, plot)";
//
//            String values = name + "','" + film.getYear() + "','";
//            values += film.getType() + "','" + film.getRated() + "','" + film.getGenre() + "','";
//            values += film.getReleaseDate() + "','" + film.getCountry() + "','" + film.getLanguage() + "','";
//            values += film.getActors().replace("\'", "\'\'") + "','" + film.getRuntime() + "','";
//            values += film.getRating() + "','" + film.getAwards().replace("\'", "\'\'");
//            values += "','" + film.getPlot().replace("\'", "\'\'");

//            db.execSQL("INSERT INTO " + userName + params + " VALUES ('" + values + "');");

        db.close();
    }
}
