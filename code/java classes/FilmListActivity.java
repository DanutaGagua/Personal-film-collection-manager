package com.example.personalfilmcollectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmListActivity extends AppCompatActivity {
    private User user = new User();
    private ArrayList<Film> films;
    private Database db = new Database();
    private int selectedPosition = -1;
    private boolean findFlag = false;
    private ListView filmList;
    private String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        Bundle arguments = getIntent().getExtras();
        user = new User(arguments.getString("username"));
        user.setFilmList(db.getFilmList(getApplicationContext(), user.getName()));

        filmList = findViewById(R.id.film_list);

        TextView header = findViewById(R.id.list_header).findViewById(R.id.film_name);
        header.setText("Film");

        header = findViewById(R.id.list_header).findViewById(R.id.film_year);
        header.setText("Year");

        findViewById(R.id.list_header).setBackgroundColor(Color.LTGRAY);

        setMainItems();

        setButton(R.id.first_button, "Exit", this::exit);
        setButton(R.id.second_button, "Add new film", this::addNewFilm);
        findViewById(R.id.button_panel).findViewById(R.id.third_button).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        user.setFilmList(db.getFilmList(getApplicationContext(), user.getName()));
        setMainItems();
    }

    public void exit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void cancel(View view) {
        FilmAdapter adapter = (FilmAdapter) filmList.getAdapter();
        adapter.switchSelection(selectedPosition);

        if (findFlag){
            setButton(R.id.first_button, "Return to list", this::returnToList);
        } else {
            setButton(R.id.first_button, "Exit", this::exit);
            setMainItems();
        }

        setButton(R.id.second_button, "Add new film", this::addNewFilm);
    }

    public void deleteFilm(View view) {
        String filmNameAndYear = films.get(selectedPosition).getName() + " " + films.get(selectedPosition).getYear();

        db.deleteFilmFromDatabase(getApplicationContext(), user.getName(),
                                  films.get(selectedPosition));
        user.getFilmList().deleteFilm(films.get(selectedPosition));

        if (findFlag){
            setButton(R.id.first_button, "Return to list", this::returnToList);
        } else {
            setButton(R.id.first_button, "Exit", this::exit);
        }

        setButton(R.id.second_button, "Add new film", this::addNewFilm);
        setMainItems();

        Toast toast = Toast.makeText(this, filmNameAndYear + " was deleted", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0,160);   // import android.view.Gravity;
        toast.show();
    }

    public void addNewFilm(View view) {
        Intent intent = new Intent(this, FindFilmActivity.class);
        intent.putExtra("visitorFlag", false);
        intent.putExtra("username", user.getName());
        startActivity(intent);
    }

    public void find(View view){
        EditText editText = findViewById(R.id.find_panel).findViewById(R.id.find_film_name);
        filter = editText.getText().toString();

        findFlag = true;

        setMainItems();

        setButton(R.id.first_button, "Return to list", this::returnToList);
    }

    public void returnToList(View view){
        findFlag = false;

        setMainItems();
        setButton(R.id.first_button, "Exit", this::exit);
    }

    public Boolean selectItem(AdapterView<?> parent, View v, int position, long id) {
        selectedPosition = position;

        FilmAdapter adapter = (FilmAdapter)filmList.getAdapter();
        adapter.switchSelection(position);

        setButton(R.id.first_button, "Cancel", this::cancel);
        setButton(R.id.second_button, "Delete film", this::deleteFilm);
        return true;
    }

    private void setButton(int id, String name, View.OnClickListener listener){
        Button button = findViewById(R.id.button_panel).findViewById(id);
        button.setText(name);
        button.setOnClickListener(listener);
    }

    public void showInfo(AdapterView<?> parent, View v, int position, long id) {
        Intent intent = new Intent(this, ShowFilmActivity.class);
        intent.putExtra("filmInfo", user.getFilmList().findFilm(films.get(position)));
        intent.putExtra("username", user.getName());
        startActivity(intent);
    }

    private void setTextViewAndFindPanel()  {
        TextView textView = findViewById(R.id.film_list_info);
        LinearLayout layout = findViewById(R.id.find_panel);
        if (user.getFilmList().getFilmListNumber() == 0) {
            textView.setText("There are not films in list");
            layout.setVisibility(View.GONE);

        } else{
            layout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

    private void setMainItems(){
        if (findFlag){
            films = user.getFilmList().findFilms(filter);
        }  else{
            films = user.getFilmList().getFilms();
        }

        setTextViewAndFindPanel();

        // создаем адаптер
        FilmAdapter adapter = new FilmAdapter(this, R.layout.film_item, films);
        filmList.setAdapter(adapter);
        filmList.setOnItemClickListener(this::showInfo);
        filmList.setOnItemLongClickListener(this::selectItem);
    }
}
