package com.udacity.giannis.popularmovies.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.udacity.giannis.popularmovies.popularmovies.Utils.JsonUtils;
import com.udacity.giannis.popularmovies.popularmovies.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import static com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity.EXTRA_PLOT;
import static com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity.EXTRA_RELEASE_DATE;
import static com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity.EXTRA_THUMBNAIL;
import static com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity.EXTRA_TITLE;
import static com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity.EXTRA_USERS_RATING;


public class MainActivity extends AppCompatActivity{
    private GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //call popular movies for main page.
        URL correctUrl = NetworkUtils.buildUrl("popular");
        //check if there is an internet connection
        if (checkConnectivity()){
            new GetMoviesInfo().execute(correctUrl.toString());
        }else{
            setContentView(R.layout.fragmant_layout);
        }
    }

    //check connectivity method
    public boolean checkConnectivity(){
        ConnectivityManager checkConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = checkConnectivity.getActiveNetworkInfo();
        return info !=null && info.isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == (R.id.popular_id)) {
            URL correctUrl = NetworkUtils.buildUrl("popular");
            if (checkConnectivity()){
                setContentView(R.layout.activity_main);
                setTitle("Popular Movies");
                new GetMoviesInfo().execute(correctUrl.toString());
            }else{
                setContentView(R.layout.fragmant_layout);
            }
            return true;
        } else if (id == (R.id.top_rated_id)) {
            URL correctUrl = NetworkUtils.buildUrl("top_rated");
            if (checkConnectivity()){
                setContentView(R.layout.activity_main);
                setTitle("Top Rated Movies");
                new GetMoviesInfo().execute(correctUrl.toString());
            }else{
                setContentView(R.layout.fragmant_layout);
            }
            return true;
        }
    return super.onOptionsItemSelected(item);
    }

    //asyncTask class that retrieves data as a background threat.
    public  class GetMoviesInfo extends AsyncTask<String, Void, String> {
      @Override
      protected void onPreExecute() {
          super.onPreExecute();
      }

      @Override
      protected void onPostExecute(String s) {
          List<Movies> moviesDataFromJson = new ArrayList<>();
          try {
              moviesDataFromJson = JsonUtils.getStringInfoFromJson(s);
          } catch (JSONException e) {
              e.printStackTrace();
          }
          if (moviesDataFromJson != null) {
              //call the method populateUi to set the data to moviesAdapter class.
              populateUi(moviesDataFromJson);
          }
      }
      @Override
      protected String doInBackground(String... strings) {
          String apiLink = strings[0];
          try {
              URL url = new URL(apiLink);
              String httpDataResponce = NetworkUtils.getDataFromServer(url);

              return httpDataResponce;

          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return null;
      }
  }
  public void populateUi (final List list){
      //call the adapter class and pass context and also the List of movies objects
      MoviesAdapter adapter = new MoviesAdapter(this,list);
      gv = (GridView) findViewById(R.id.grid_view);
      //set the adapter
      gv.setAdapter(adapter);
      //set on item click listener to show details about selected movie.
      gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //according to pressed item we get the details for the MoviesDetailActivity.
              Movies mv= (Movies) list.get(i);
              String tt =   mv.getTitle();
              String us = mv.getUsers_rating();
              String pl = mv.getPlot();
              String re = mv.getRelease();
              String th = mv.getThumbnail();
              startDetailActivity(tt ,us ,pl , re ,th);
          }
      });
  }
  //in this method we start MoviesDetailActivity.
private void startDetailActivity(String titl, String us,String pl, String re, String th){
    Intent intent = new Intent(this, MoviesDetailActivity.class);
    intent.putExtra(EXTRA_TITLE,titl);
    intent.putExtra(EXTRA_USERS_RATING,us);
    intent.putExtra(EXTRA_PLOT,pl);
    intent.putExtra(EXTRA_RELEASE_DATE,re);
    intent.putExtra(EXTRA_THUMBNAIL,th);
    startActivity(intent);
}

}



