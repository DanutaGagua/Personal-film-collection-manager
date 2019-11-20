package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private String previousClassName = "";
    private UserList userList;
    private boolean visitor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Bundle arguments = getIntent().getExtras();
        previousClassName = arguments.getString("previousClassName");
        visitor = arguments.getBoolean("visitorFlag");

        TextView textView = findViewById(R.id.activity_sign_in_info);
        textView.setText("Enter username and \n add new user");
    }

    public void signUp(View view) {
        //if ()

        //Intent intent = new Intent(this, SignInActivity.class);
        //startActivity(intent);
    }

    public void cancel(View view) {
        Intent intent;

        if (previousClassName.equals("MainActivity")) {
            intent = new Intent(this, MainActivity.class);
        } else if (previousClassName.equals("FindFilmActivity")) {
            intent = new Intent(this, FindFilmActivity.class);
        } else {
            intent = new Intent(this, ShowFilmInformationActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("visitorFlag", visitor);
        startActivity(intent);
    }

    private UserList getUserList() {
        UserList userList = new UserList();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT)");
        Cursor query = db.rawQuery("SELECT * FROM users;", null);

        if(query.moveToFirst()) {
            String name = query.getString(0);

            userList.addUser(new User(name));
        }

        return userList;
    }
}
