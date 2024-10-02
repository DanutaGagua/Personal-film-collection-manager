package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowFilmActivity extends AppCompatActivity {
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_film);

        Bundle arguments = getIntent().getExtras();
        film = (Film) arguments.get("filmInfo");

        setInfo();
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void backToList(View view) {
        Intent intent = new Intent(this, FilmListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
}
