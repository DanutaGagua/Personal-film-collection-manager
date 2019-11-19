package com.example.personalfilmcollectionmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowFilmInformationActivity extends AppCompatActivity {
    boolean visitor = false;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");
        film = (Film) arguments.get("film");

        if (visitor)
        {
            setContentView(R.layout.activity_show_found_film_information_visitor);

            setInfo();
        }
        else
        {
            //setContentView(R.layout.activity_find_film_user);
        }
    }

    private void setInfo()
    {
        //TextView textView = findViewById(R.id)
    }
}
