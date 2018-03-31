package com.udacity.giannis.popularmovies.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by giann on 3/26/2018.
 */

public class MoviesDetailActivity extends AppCompatActivity {

    //string tags to help as to get the extras from the intent
    public static final String EXTRA_TITLE="extra_title";
    public static final String EXTRA_USERS_RATING="extra_user_rating";
    public static final String EXTRA_PLOT="extra_plot";
    public static final String EXTRA_RELEASE_DATE="extra_release_date";
    public static final String EXTRA_THUMBNAIL="extra_thumbnail";


    protected final String BASE_THUMBNAIL_URL ="http://image.tmdb.org/t/p/w342/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        ImageView mImageView = (ImageView) findViewById(R.id.image_detail);

        TextView mOriginalTitle= (TextView) findViewById(R.id.original);
        TextView mUser= (TextView) findViewById(R.id.users_rating);
        TextView mRelease= (TextView) findViewById(R.id.release_date);
        TextView mPlot= (TextView) findViewById(R.id.plot_synopsis);


        Intent intent = getIntent();
        if (intent==null){
            return;
        }
        String title = intent.getStringExtra(EXTRA_TITLE);
        String us = intent.getStringExtra(EXTRA_USERS_RATING);
        String pl = intent.getStringExtra(EXTRA_PLOT);
        String re = intent.getStringExtra(EXTRA_RELEASE_DATE);
        String th = intent.getStringExtra(EXTRA_THUMBNAIL);

        if (title==null){
            return;
        }else {
            //setting the data to the UI.
            mUser.setText(us);
            mOriginalTitle.setText(title);
            mRelease.setText(re);
            mPlot.setText(pl);
            Picasso.with(this).load(BASE_THUMBNAIL_URL+th).into(mImageView);
        }
    }


}
