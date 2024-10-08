package com.example.personalfilmcollectionmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FilmAdapter extends ArrayAdapter<Film> {
    private LayoutInflater inflater;
    private int layout;
    private List<Film> films;
    private boolean[] selections;

    public FilmAdapter(Context context, int resource, List<Film> films) {
        super(context, resource, films);
        this.films = films;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.selections = new boolean[films.size()];
    }

    public void switchSelection(int position){
        selections[position] = !selections[position];
        //оповещаем адаптер об изменениях, чтобы он обновил все элементы списка.
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.film_name);
        TextView yearView = view.findViewById(R.id.film_year);

        Film film = films.get(position);

        if (selections[position]){
            view.setBackgroundColor(Color.parseColor("#00D4AA"));
        }else {
            view.setBackgroundColor(Color.WHITE);
        }

        nameView.setText(film.getName());
        yearView.setText(film.getYear());

        return view;
    }
}
