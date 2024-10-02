package com.example.personalfilmcollectionmanager;

import android.content.Context;
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

    public FilmAdapter(Context context, int resource, List<Film> films) {
        super(context, resource, films);
        this.films = films;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.film_name);
        TextView capitalView = view.findViewById(R.id.film_year);

        Film film = films.get(position);

        nameView.setText(film.getName());
        capitalView.setText(film.getYear());

        return view;
    }
}
