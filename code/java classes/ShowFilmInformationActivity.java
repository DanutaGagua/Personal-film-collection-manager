package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

        setContentView(R.layout.activity_show_found_film_information);

        if (visitor) {
            Button signUpButton = findViewById(R.id.button_panel).findViewById(R.id.first_button);
            signUpButton.setText("Sign up");
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signUp(view);
                }
            });

            Button backButton = findViewById(R.id.button_panel).findViewById(R.id.second_button);
            backButton.setText("Back");
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    back(view);
                }
            });

            LinearLayout panelButton = findViewById(R.id.button_panel);
            panelButton.removeView(findViewById(R.id.third_button));
        } else {
            Button cancelButton = findViewById(R.id.button_panel).findViewById(R.id.first_button);
            cancelButton.setText("Cancel");
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel(view);
                }
            });

            Button exitButton = findViewById(R.id.button_panel).findViewById(R.id.second_button);
            exitButton.setText("Exit");
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exit(view);
                }
            });

            Button addButton = findViewById(R.id.button_panel).findViewById(R.id.third_button);
            addButton.setText("Add");
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add(view);
                }
            });

            userName = arguments.getString("username");
        }

        setInfo();
    }

    private void setInfo() {
        TextView textInfo = findViewById(R.id.found_film_name);
        textInfo.setText(film.getName());

        textInfo = findViewById(R.id.found_film_year);
        textInfo.setText(film.getYear());

        textInfo = findViewById(R.id.found_film_type);
        textInfo.setText(film.getType());

        textInfo = findViewById(R.id.found_film_rated);
        textInfo.setText(film.getRated());

        textInfo = findViewById(R.id.found_film_genre);
        textInfo.setText(film.getGenre());

        textInfo = findViewById(R.id.found_film_release_date);
        textInfo.setText(film.getReleaseDate());

        textInfo = findViewById(R.id.found_film_country);
        textInfo.setText(film.getCountry());

        textInfo = findViewById(R.id.found_film_language);
        textInfo.setText(film.getLanguage());

        textInfo = findViewById(R.id.found_film_actors);
        textInfo.setText(film.getActors());

        textInfo = findViewById(R.id.found_film_runtime);
        textInfo.setText(film.getRuntime());

        textInfo = findViewById(R.id.found_film_rating);
        textInfo.setText(film.getRating());

        textInfo = findViewById(R.id.found_film_awards);
        textInfo.setText(film.getAwards());

        textInfo = findViewById(R.id.found_film_plot);
        textInfo.setText(film.getPlot());
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

        String values = film.getName() + "','" + film.getYear() + "','" + film.getType();
        values += "','" + film.getRated() + "','" + film.getGenre() + "','" + film.getReleaseDate();
        values += "','" + film.getCountry() + "','" + film.getLanguage() + "','" + film.getActors();
        values += "','" + film.getRuntime() + "','" + film.getRating() + "','" + film.getAwards();
        values += "','" + film.getPlot() + "');";

        db.execSQL("INSERT INTO " + userName + " VALUES ('" + values);
        db.close();
    }
}
