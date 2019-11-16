package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    boolean visitor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view)
    {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void signUp(View view)
    {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void signInAsVisitor(View view)
    {
        visitor = true;

        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("visitorFlag", visitor);
        
        startActivity(intent);
    }
}
