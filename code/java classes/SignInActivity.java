package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    private UserList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userList = getUserList();

        setInfo();

        String[] users = userList.getUserNames();

        Spinner spinner = findViewById(R.id.users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , users);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        Button cancelButton = findViewById(R.id.cancel_button).findViewById(R.id.button);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
            }
        });

        Button signInButton = findViewById(R.id.sign_in_button).findViewById(R.id.button);
        signInButton.setText("Sign in");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });
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

    private UserList getUserList() {
        UserList userList = new UserList();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT)");
        Cursor query = db.rawQuery("SELECT * FROM users;", null);

        if(query.moveToFirst()) {
            do{
                String name = query.getString(0);

                userList.addUser(new User(name));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return userList;
    }

    private void setInfo() {
        TextView textView = findViewById(R.id.activity_sign_in_info);

        if (userList.getUserNumber() == 0) {
            textView.setText("There are not users in list");
        } else {
            textView.setText("Select your username \n and sign in");
        }
    }
}