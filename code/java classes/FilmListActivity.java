package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FilmListActivity extends AppCompatActivity {
    private User user = new User();
    String[] films;

    public static final String ACTION ="com.eugene.SHOW_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        Bundle arguments = getIntent().getExtras();
        user = new User(arguments.getString("username"));

        getFilmList();

        films = user.getFilmList().getFilmNames();

        ListView filmList = findViewById(R.id.film_list);

        if (user.getFilmList().getFilmListNumber() == 0) {
            TextView textView = findViewById(R.id.film_list_info);
            textView.setText("There are not films in list");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, films);
        filmList.setAdapter(adapter);
        filmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("filmInfo", user.getFilmList().findFilm(films[position]));
                startActivity(intent);
            }
        });

        filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFilmList();

        films = user.getFilmList().getFilmNames();

        ListView filmList = findViewById(R.id.film_list);

        if (user.getFilmList().getFilmListNumber() == 0) {
            TextView textView = findViewById(R.id.film_list_info);
            textView.setText("There are not films in list");
        }

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, films);
        // устанавливаем для списка адаптер
        filmList.setAdapter(adapter);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void addNewFilm(View view) {
        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.putExtra("visitorFlag", false);
        intent.putExtra("username", user.getName());
        startActivity(intent);
    }

    private void getFilmList() {
        FilmList filmList = new FilmList();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + user.getName() + " (name TEXT, year TEXT, release_date TEXT, country TEXT, runtime TEXT)");
        Cursor query = db.rawQuery("SELECT * FROM " + user.getName(), null);

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

                filmList.addFilm(new Film(name, year, type, rated, genre, releaseDate, country, language, actors, runtime, rating, awards, plot));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        user.setFilmList(filmList);
    }
}
