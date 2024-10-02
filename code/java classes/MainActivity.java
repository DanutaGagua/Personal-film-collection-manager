package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private boolean visitor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHelpTextView();

        Button signInButton = findViewById(R.id.sign_in_button).findViewById(R.id.button);
        signInButton.setText("Sign in");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });

        Button signUpButton = findViewById(R.id.sign_up_button).findViewById(R.id.button);
        signUpButton.setText("Sign up");
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

        Button signInAsVisitorButton = findViewById(R.id.sign_in_as_visitor_button).findViewById(R.id.button);
        signInAsVisitorButton.setText("Sign in as visitor");
        signInAsVisitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAsVisitor(view);
            }
        });
    }

    public void signIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("visitorFlag", visitor);
        intent.putExtra("previousClassName", "MainActivity");

        startActivity(intent);
    }

    public void signInAsVisitor(View view) {
        visitor = true;

        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("visitorFlag", visitor);

        startActivity(intent);
    }

    private void setHelpTextView() {
        TextView textView = findViewById(R.id.help_text_view);
        textView.setText("Help:\nIf you want to use application without signing up click button \"Sign in as visitor\"");
    }
}