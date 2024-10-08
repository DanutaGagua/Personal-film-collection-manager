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

        setButton(R.id.sign_in_button, "Sign in", this::signIn);
        setButton(R.id.sign_up_button, "Sign up", this::signUp);
        setButton(R.id.sign_in_as_visitor_button, "Sign in as visitor",
                 this::signInAsVisitor);
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

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(id).findViewById(R.id.button);
        button.setText(name);
        button.setOnClickListener(listener);
    }
}