package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FilmListActivity extends AppCompatActivity {
    private User user = new User();
    String[] films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        Bundle arguments = getIntent().getExtras();
        user = new User(arguments.getString("username"));

        getFilmList();

        films = user.getFilmList().getFilmNames();

        ListView filmList = findViewById(R.id.film_list);

        if (user.getFilmList().getFilmListNumber() == 0) {
            TextView textView = findViewById(R.id.film_list_info);
            textView.setText("There are not films in list");
        }

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, films);
        // устанавливаем для списка адаптер
        filmList.setAdapter(adapter);
        // добвляем для списка слушатель
        filmList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // по позиции получаем выбранный элемент
                String selectedItem = films[position];
            }
        });

        filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id)
            {
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFilmList();

        films = user.getFilmList().getFilmNames();

        ListView filmList = findViewById(R.id.film_list);

        if (user.getFilmList().getFilmListNumber() == 0) {
            TextView textView = findViewById(R.id.film_list_info);
            textView.setText("There are not films in list");
        }

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, films);
        // устанавливаем для списка адаптер
        filmList.setAdapter(adapter);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void addNewFilm(View view) {
        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.putExtra("visitorFlag", false);
        intent.putExtra("username", user.getName());
        startActivity(intent);
    }

    private void getFilmList() {
        FilmList filmList = new FilmList();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("users.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + user.getName() + " (name TEXT, year TEXT, release_date TEXT, country TEXT, runtime TEXT)");
        Cursor query = db.rawQuery("SELECT * FROM " + user.getName(), null);

        if(query.moveToFirst()) {
            do{
                String name = query.getString(0);
                String year = query.getString(1);
                String releaseDate = query.getString(2);
                String country = query.getString(3);
                String runtime = query.getString(4);

                filmList.addFilm(new Film(name, year, releaseDate, country, "", "", runtime));
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        user.setFilmList(filmList);
    }
}
