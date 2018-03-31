package com.udacity.giannis.popularmovies.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.giannis.popularmovies.popularmovies.Utils.JsonUtils;

import java.util.List;

/**
 * Created by giann on 3/5/2018.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {
private Context context;
    public MoviesAdapter(@NonNull Context context, List<Movies> objects) {
        super( context, 0, objects);
        this.context=context;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Movies item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Movies movies =  getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_layout,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.image);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342/"+movies.getImage()).into(imageView);
        return convertView;
    }

}
