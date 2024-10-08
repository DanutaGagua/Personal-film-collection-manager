package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowFilmActivity extends AppCompatActivity {
    private Film film;
    private Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_film);

        Bundle arguments = getIntent().getExtras();
        film = (Film) arguments.get("filmInfo");

        setButton(R.id.first_button, "Exit", this::exit);
        setButton(R.id.second_button, "Back to list", this::backToList);
        findViewById(R.id.button_panel).findViewById(R.id.third_button).setVisibility(View.GONE);

        setInfo();
    }

    public void exit(View view) {
        checkFeedback();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void backToList(View view) {
        checkFeedback();

        Intent intent = new Intent(this, FilmListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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

        EditText editText = findViewById(R.id.film_user_feedback);
        editText.setText(film.getUserFeedback());
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

    private void checkFeedback(){
        EditText editText = findViewById(R.id.film_user_feedback);
        String userFeedback = editText.getText().toString();

        if (!userFeedback.equals("") && !userFeedback.equals(film.getUserFeedback())){
            film.setUserFeedback(userFeedback);

            db.setFilmUserFeedback(getApplicationContext(),
                    getIntent().getExtras().get("username").toString(),
                    userFeedback, film);

            Toast toast = Toast.makeText(this, "Your feedback was edited", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
            toast.show();
        }

    }
}
