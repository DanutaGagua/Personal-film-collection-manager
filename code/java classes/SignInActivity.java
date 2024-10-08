package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private UserList userList;
    private Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userList = db.getUserList(getApplicationContext());

        setInfo();

        String[] users = userList.getUserNames();

        Spinner spinner = findViewById(R.id.users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , users);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        setButton(R.id.cancel_button, "Cancel", this::cancel);
        setButton(R.id.sign_in_button, "Sign in", this::signIn);
    }

    public void signIn(View view) {
        if (userList.getUserNumber() != 0) {
            Spinner spinner = findViewById(R.id.users);
            String name = spinner.getSelectedItem().toString();

            Intent intent = new Intent(this, FilmListActivity.class);
            intent.putExtra("username", name);
            startActivity(intent);
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void setInfo() {
        TextView textView = findViewById(R.id.activity_sign_in_info);

        if (userList.getUserNumber() == 0) {
            textView.setText("There are not users in list");
        } else {
            textView.setText("Select your username \n and sign in");
        }
    }

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(id).findViewById(R.id.button);
        button.setText(name);
        button.setOnClickListener(listener);
    }
}