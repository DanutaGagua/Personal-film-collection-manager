package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        userList = getUserList();

        TextView textView = findViewById(R.id.activity_sign_up_info);
        textView.setText("Enter username and \n add new user");

        Button cancelButton = findViewById(R.id.cancel_button).findViewById(R.id.button);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
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

        EditText editText = findViewById(R.id.new_user_name).findViewById(R.id.edit_text);
        editText.setHint("Enter username");
    }

    public void signUp(View view) {
        EditText editText = findViewById(R.id.new_user_name).findViewById(R.id.edit_text);
        String newUserName = editText.getText().toString();

        if (!newUserName.equals("")) {
            if (userList.checkIfExistUser(newUserName)) {
                TextView textView = findViewById(R.id.activity_sign_up_info);
                textView.setText("This username is existing. \nEnter another username");

                editText.setText("");
                editText.setHint("Enter username");
            } else {
                userList.addUser(new User(newUserName));

                addNewUserToDataBase(newUserName);

                Intent intent = new Intent(this, FilmListActivity.class);
                intent.putExtra("username", newUserName);
                startActivity(intent);
            }
        }
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
            do{
                String name = query.getString(0);

                userList.addUser(new User(name));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return userList;
    }

    private void addNewUserToDataBase(String newUserName) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO users VALUES ('"+ newUserName +"');");
        db.close();
    }
}