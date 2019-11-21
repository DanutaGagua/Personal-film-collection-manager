package com.example.personalfilmcollectionmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
    }

    public void signUp(View view) {
        EditText editText = findViewById(R.id.new_user_name);
        String newUserName = editText.getText().toString();

        if (userList.checkIfExistUser(newUserName)) {
            TextView textView = findViewById(R.id.activity_sign_up_info);
            textView.setText("This username is existing. \n Enter another username");
            editText.setText("");
            editText.setHint("Enter username");
        } else {
            userList.addUser(new User(newUserName));
        }

        //addNewUserToDataBase(newUserName);

        Intent intent = new Intent(this, FilmListActivity.class);
        intent.putExtra("user", new User(newUserName));
        startActivity(intent);
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

        query.close();
        db.close();

        return userList;
    }

    private void addNewUserToDataBase(String newUserName)
    {
        ContentValues cv = new ContentValues();
        //cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("INSERT INTO users VALUES (newUserName);");
        db.close();
    }
}
