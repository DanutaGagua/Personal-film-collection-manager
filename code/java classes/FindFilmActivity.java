package com.example.personalfilmcollectionmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FindFilmActivity extends AppCompatActivity {
    boolean visitor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");

        if (visitor)
        {
            setContentView(R.layout.activity_find_film_visitor);
        }
    }
}
