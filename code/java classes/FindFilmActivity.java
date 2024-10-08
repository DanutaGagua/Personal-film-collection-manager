package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class FindFilmActivity extends AppCompatActivity {
    private boolean visitor = false, end = false;
    String filmInfo = "";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");

        setContentView(R.layout.activity_find_film);

        setButton(R.id.third_button, "Find", this::find);

        if (visitor) {
            setButton(R.id.first_button, "Sign up", this::signUp);

            LinearLayout panelButton = findViewById(R.id.button_panel);
            panelButton.removeView(findViewById(R.id.second_button));
        } else {
            setButton(R.id.first_button, "Cancel", this::cancel);
            setButton(R.id.second_button, "Exit", this::exit);

            userName = arguments.getString("username");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        filmInfo = "";
    }

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(R.id.button_panel).findViewById(id);
        button.setText(name);
        button.setOnClickListener(listener);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, FilmListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("visitorFlag", visitor);
        intent.putExtra("previousClassName", "FindFilmActivity");
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void find(View view) {
        EditText editText = findViewById(R.id.find_film_name);
        String filmName = editText.getText().toString().replaceAll(" ", "+");

        ProgressTask progressTask = new ProgressTask();
        progressTask.execute(filmName);

        TextView textView = findViewById(R.id.find_film_info);

        if (filmInfo.equals("")) {
            textView.setText("Enter button \"Find\" another.");
            return;
        }

        if (filmInfo.indexOf("False") != -1) {
            textView.setText("Can't find this film. It isn't existing.");
        } else {
            if (filmInfo.indexOf("Unable") != -1){
                textView.setText("No network connection.");
            } else {
                Intent intent = new Intent(this, ShowFilmInformationActivity.class);
                intent.putExtra("visitorFlag", visitor);
                intent.putExtra("filmInfo", filmInfo);
                intent.putExtra("username", userName);
                startActivity(intent);
            }
        }
    }

    private class ProgressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... filmName) {
            String content;
            try {
                content = getContent(filmName[0]);
            } catch (IOException ex) {
                content = ex.getMessage();
            }

            return content;
        }

        @Override
        protected void onPostExecute(String content) {
            filmInfo = content;
            end = true;
        }

        private String getContent(String filmName) throws IOException {
            BufferedReader reader = null;
            try {
                String urlstr = "https://www.omdbapi.com/?t=" + filmName + "&plot=full&apikey=1d4c22a7";

                EditText editText = findViewById(R.id.find_film_year);
                if (editText.getText().toString() != "" ){
                    urlstr = urlstr + "&y=" + editText.getText().toString();
                }

                URL url = new URL(urlstr);

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                return stringBuilder.toString();

            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            //return null;
        }
    }
}
