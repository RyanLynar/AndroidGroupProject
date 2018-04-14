package groupwork.androidgroupproject.MoviePackage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import groupwork.androidgroupproject.MoviePackage.AddMovieActivity;
import groupwork.androidgroupproject.MoviePackage.Movie;
import groupwork.androidgroupproject.MoviePackage.MovieAdapter;
import groupwork.androidgroupproject.MoviePackage.MovieDBAdapter;
import groupwork.androidgroupproject.R;


public class MovieDBActivity extends Activity {
    SQLiteDatabase m_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);
        Button addMovieBtn = findViewById(R.id.addMovieBtn);
        checkDB();
        addMovieBtn.setOnClickListener(t->{
            Intent in = new Intent(this,AddMovieActivity.class);
            startActivityForResult(in, 101);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkDB();
    }
    public void checkDB(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        MovieDBAdapter dba = new MovieDBAdapter(this);
        MovieAdapter movieAdapter = new MovieAdapter(this,R.id.movieList,movies);

        ListView movieList= findViewById(R.id.movieList);
        movieList.setAdapter(movieAdapter);
        m_db = dba.getWritableDatabase();
        Cursor curse = m_db.rawQuery("SELECT * FROM "+dba.DBTABLE+";",null);
        curse.moveToFirst();
        Log.i("where is cursor", ""+curse.isLast());
        while(!curse.isLast()){
          movies.add(new Movie(curse.getInt(curse.getColumnIndex(dba.DBKEY)),curse.getString(curse.getColumnIndex(dba.cTITLE)),curse.getString(curse.getColumnIndex(dba.cACTORS))
                  ,curse.getString(curse.getColumnIndex(dba.cDESC)),curse.getString(curse.getColumnIndex(dba.cGENRE)),curse.getString(curse.getColumnIndex(dba.cURL))
                  ,curse.getDouble(curse.getColumnIndex(dba.cRATING)), curse.getInt(curse.getColumnIndex(dba.cLENGTH))));
            curse.moveToNext();
        }

        movieAdapter.notifyDataSetChanged();


        m_db.close();
    }
}