package com.example.personalfilmcollectionmanager;

import android.content.Intent;
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
    private Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Bundle arguments = getIntent().getExtras();
        previousClassName = arguments.getString("previousClassName");
        visitor = arguments.getBoolean("visitorFlag");

        userList = db.getUserList(getApplicationContext());

        TextView textView = findViewById(R.id.activity_sign_up_info);
        textView.setText("Enter username and \n add new user");

        setButton(R.id.cancel_button, "Cancel", this::cancel);
        setButton(R.id.sign_up_button, "Sign up", this::signUp);

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

                db.addNewUserToDataBase(getApplicationContext(), newUserName);

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

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(id).findViewById(R.id.button);
        button.setText(name);
        button.setOnClickListener(listener);
    }
}