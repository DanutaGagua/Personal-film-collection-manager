package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ShowFilmInformationActivity extends AppCompatActivity {
    private boolean visitor = false;
    private Film film;
    String userName;
    Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");
        film = new Film(arguments.getString("filmInfo"));

        setContentView(R.layout.activity_show_found_film_information);

        if (visitor) {
            setButton(R.id.first_button, "Sign up", this::signUp);
            setButton(R.id.second_button, "Back", this::back);
            findViewById(R.id.button_panel).findViewById(R.id.third_button).setVisibility(View.GONE);
        } else {
            setButton(R.id.first_button, "Cancel", this::cancel);
            setButton(R.id.second_button, "Exit", this::exit);
            setButton(R.id.third_button, "Add", this::add);

            userName = arguments.getString("username");
        }

        setInfo();
    }

    private void setInfo() {
        setTextViewInfo(R.id.found_film_name, film.getName());
        setTextViewInfo(R.id.found_film_year, film.getYear());
        setTextViewInfo(R.id.found_film_type, film.getType());
        setTextViewInfo(R.id.found_film_rated, film.getRated());
        setTextViewInfo(R.id.found_film_genre, film.getGenre());
        setTextViewInfo(R.id.found_film_release_date, film.getReleaseDate());
        setTextViewInfo(R.id.found_film_country, film.getCountry());
        setTextViewInfo(R.id.found_film_language, film.getLanguage());
        setTextViewInfo(R.id.found_film_actors, film.getActors());
        setTextViewInfo(R.id.found_film_runtime, film.getRuntime());
        setTextViewInfo(R.id.found_film_rating, film.getRating());
        setTextViewInfo(R.id.found_film_awards, film.getAwards());
        setTextViewInfo(R.id.found_film_plot, film.getPlot());

        findViewById(R.id.show_user_feedback).setVisibility(View.GONE);
        findViewById(R.id.film_user_feedback).setVisibility(View.GONE);

    }

    private void setTextViewInfo(int id, String info){
        TextView textInfo = findViewById(id);
        textInfo.setText(info);
    }

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(R.id.button_panel).findViewById(id);
        button.setText(name);
        button.setOnClickListener(listener);
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
        if (db.checkIfFilmIsAlreadyInList(getApplicationContext(), userName, film.getName(), film.getYear())){
            Toast toast = Toast.makeText(this, "Film with such name and year is already in list", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
            toast.show();
        } else {
            db.addNewFilmToDataBase(getApplicationContext(), userName, film);

            Toast toast = Toast.makeText(this, film.getName() + " " + film.getYear() + " was added", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
            toast.show();
        }

        Intent intent = new Intent(this, FilmListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
