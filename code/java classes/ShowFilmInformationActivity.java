package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ShowFilmInformationActivity extends AppCompatActivity {
    private boolean visitor = false;
    private Film film;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");
        film = new Film(arguments.getString("filmInfo"));

        if (visitor) {
            setContentView(R.layout.activity_show_found_film_information_visitor);
        } else {
            setContentView(R.layout.activity_show_found_film_information_user);

            userName = arguments.getString("username");
        }

        setInfo();
    }

    private void setInfo() {
        TextView textView = findViewById(R.id.found_film_name);

        textView.setText(film.getName());

        TextView textInfo = findViewById(R.id.found_film_year);
        textInfo.setText("Year           " + film.getYear());

        textInfo = findViewById(R.id.found_film_release_date);
        textInfo.setText("Release Date   " + film.getReleaseDate());

        textInfo = findViewById(R.id.found_film_country);
        textInfo.setText("Country        " + film.getCountry());

        textInfo = findViewById(R.id.found_film_runtime);
        textInfo.setText("Runtime        " + film.getRuntime());
    }

    public void back(View view) {
        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.putExtra("visitorFlag", visitor);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("visitorFlag", visitor);
        intent.putExtra("previousClassName", "ShowFilmInformationActivity");
        startActivity(intent);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void add(View view) {
        addNewFilmToDataBase(film);

        Intent intent = new Intent(this, FilmListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void addNewFilmToDataBase(Film film) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO " + userName + " VALUES ('" + film.getName() + "','" + film.getYear() + "','" + film.getReleaseDate() + "','"+ film.getCountry() +"','"+ film.getRuntime() +"');");
        db.close();
    }
}
