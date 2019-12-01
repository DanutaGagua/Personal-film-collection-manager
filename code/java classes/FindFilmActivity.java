package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class FindFilmActivity extends AppCompatActivity {
    private boolean visitor = false;
    String filmInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getIntent().getExtras();
        visitor = arguments.getBoolean("visitorFlag");

        if (visitor) {
            setContentView(R.layout.activity_find_film_visitor);
        } else {
            setContentView(R.layout.activity_find_film_user);
        }
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
        String filmName = editText.toString().replaceAll(" ", "+");

        new ProgressTask().execute(filmName);

        Intent intent = new Intent(this, ShowFilmInformationActivity.class);
        intent.putExtra("visitorFlag", visitor);

        TextView textView = findViewById(R.id.film_info);
        textView.setText(filmInfo);

        Toast.makeText(this,"visitorFlag", Toast.LENGTH_SHORT).show();

        //if (!filmInfo.equals(""))
        //intent.putExtra("filmInfo", filmInfo);
        //startActivity(intent);
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
        }

        private String getContent(String filmName) throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL("https://www.omdbapi.com/?t=" + filmName + "&r=xml&apikey=1d4c22a7");
                //Toast.makeText(se, url.toString(), Toast.LENGTH_SHORT).show();

                HttpsURLConnection c=(HttpsURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();

                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder buf=new StringBuilder();
                String line=null;

                while ((line=reader.readLine()) != null) {
                    buf.append(line + "\n");
                }

                return(buf.toString());
            } catch(MalformedURLException e) {
                Log.e("downloadXML", "downloadXML: Invalid URL " + e.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

            return null;
        }
    }
}
