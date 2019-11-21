package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FindFilmActivity extends AppCompatActivity {
    private boolean visitor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");

        if (visitor) {
            setContentView(R.layout.activity_find_film_visitor);
        } else {
            setContentView(R.layout.activity_find_film_user);
        }
    }

    public void cancel(View view) {
        //Intent intent = new Intent(this, SignUpActivity.class);
        //startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void find(View view) {

    }
}
