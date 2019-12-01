package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ShowFilmInformationActivity extends AppCompatActivity {
    private boolean visitor = false;
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");
        //film = (Film) arguments.get("film");

        if (visitor) {
            setContentView(R.layout.activity_show_found_film_information_visitor);
        } else {
            setContentView(R.layout.activity_show_found_film_information_user);
        }

        //setInfo(arguments.getString("filmInfo"));
    }

    private void setInfo(String filmInfo) {
        TextView textView = findViewById(R.id.find_film_name_show_activity);

        textView.setText(filmInfo.substring(filmInfo.indexOf("movie title=\"") + 13, filmInfo.indexOf("year=\"")));
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
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);*/
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void add(View view) {
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);*/
    }
}
