package groupwork.androidgroupproject.MoviePackage;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import groupwork.androidgroupproject.R;


public class MovieDBActivity extends Activity {
    SQLiteDatabase m_db;
    MovieAdapter adapter;
    public static ArrayList<Movie> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);
        adapter= new MovieAdapter(this,R.id.movieList,movies);
        ListView movieList = findViewById(R.id.movieList);
        movieList.setAdapter(adapter);
        Button addMovieBtn = findViewById(R.id.addMovieBtn);
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager!=null) {
           NetworkInfo activeNet = manager.getActiveNetworkInfo();
           if (activeNet != null && activeNet.isConnectedOrConnecting()) {
               if(savedInstanceState==null)
                new SiteParser(this).execute();
           }
       }
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            movieList.setOnItemClickListener((parent, view, pos, id) -> {
                movieList.setItemChecked(pos,  true);
                ExtendedMovieInformationForm fragment = (ExtendedMovieInformationForm) getFragmentManager().findFragmentById(R.id.movieDetails);
                fragment.setupLayout(findViewById(R.id.movieDetails),pos);
                getFragmentManager().beginTransaction().replace(R.id.movieDetails,fragment).commit();
            });
        }else {
            movieList.setOnItemClickListener((parent, view, pos, id) -> {
                Intent in = new Intent(this, ExtendedMovieActivity.class);
                in.putExtra("listItemPos", pos);
                startActivity(in);
            });
        }
        checkDB();
        addMovieBtn.setOnClickListener(t->{
            Intent in = new Intent(this,AddMovieActivity.class);
            startActivity(in);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkDB();
        adapter.notifyDataSetChanged();

    }
    public void checkDB(){
        MovieDBAdapter dba = new MovieDBAdapter(this);
        m_db = dba.getWritableDatabase();
        Cursor curse = m_db.rawQuery("SELECT * FROM "+MovieDBAdapter.DBTABLE+" ;",null);
        curse.moveToFirst();
        while(!curse.isAfterLast()){
           Movie toAdd = new Movie(curse.getString(curse.getColumnIndex(MovieDBAdapter.cTITLE)),curse.getString(curse.getColumnIndex(MovieDBAdapter.cACTORS))
                    ,curse.getString(curse.getColumnIndex(MovieDBAdapter.cDESC)),curse.getString(curse.getColumnIndex(MovieDBAdapter.cGENRE)),curse.getString(curse.getColumnIndex(MovieDBAdapter.cURL))
                    ,curse.getDouble(curse.getColumnIndex(MovieDBAdapter.cRATING)), curse.getInt(curse.getColumnIndex(MovieDBAdapter.cLENGTH)));
            if(!movies.contains(toAdd)) {
                new ImageGrab().execute(toAdd);
                movies.add(toAdd);
                adapter.notifyDataSetChanged();
            }
            curse.moveToNext();
        }
        curse.close();
        m_db.close();
    }
public class SiteParser extends AsyncTask<Void,Integer,ArrayList<Movie>>
    {

       ProgressBar pBar;
       Context con;
       String title;
       String actors;
       String genre;
       String url;
       String desc;
       double rating;
       int length;
       int accessCount;
       SiteParser(Context c){
           con= c;
       }
       @Override
       protected ArrayList<Movie> doInBackground(Void... voids) {
           accessCount = 0;
           ArrayList<Movie> tempList = new ArrayList<>();
           try {
               URL webHook = new URL("http://torunski.ca/CST2335/MovieInfo.xml");
               URLConnection connecter = webHook.openConnection();
               XmlPullParser parser = Xml.newPullParser();
               parser.setInput(connecter.getInputStream(), null);
               while (parser.next() != XmlPullParser.END_DOCUMENT) {
                   if(parser.getName()!=null&& parser.getEventType()==XmlPullParser.END_TAG){
                       if(parser.getName().equals("Movie")){
                           accessCount++;
                           Movie temp = new Movie(title,actors,desc,genre,url,rating,length);
                           if(!tempList.contains(temp)) {
                               tempList.add(temp);
                           }
                       }
                   }
                   if(parser.getName()!=null && parser.getEventType() == XmlPullParser.START_TAG){
                       publishProgress(0);
                       if(parser.getName().equals("Title") && parser.next() == XmlPullParser.TEXT){
                           title = parser.getText();
                           onProgressUpdate(14);
                       }
                       else if(parser.getName().equals("Actors") && parser.next() == XmlPullParser.TEXT){
                           actors = parser.getText();
                           onProgressUpdate(28);
                       }
                       else if(parser.getName().equals("Length") && parser.next() == XmlPullParser.TEXT){
                           length =Integer.parseInt(parser.getText());
                           onProgressUpdate(42);
                       }
                       else if(parser.getName().equals("Description") && parser.next() == XmlPullParser.TEXT){
                           desc = parser.getText();
                           onProgressUpdate(56);
                       }
                       else if(parser.getName().equals("Rating") && parser.next() == XmlPullParser.TEXT){
                           rating = Double.parseDouble(parser.getText());
                           onProgressUpdate(60);
                       }
                       else if(parser.getName().equals("Genre") && parser.next() == XmlPullParser.TEXT){
                           genre = parser.getText();
                           onProgressUpdate(78);
                       }
                       else if(parser.getName().equals("URL")){
                           url= parser.getAttributeValue(null,"value");
                           onProgressUpdate(100);
                       }
                   }

               }

           } catch (IOException e) {
               Log.e("IOE", e.getMessage());
           } catch (XmlPullParserException e) {
               Log.e("XMLERROR", "DID XML BAD " + e.getMessage());
           }
        return tempList;
       }

        @Override
        protected void onPostExecute(ArrayList<Movie> innerMovie) {
           pBar.setVisibility(ProgressBar.INVISIBLE);
            //Notification customNotification = new NotificationCompat.Builder(getApplicationContext(),"0")
            //        .setSmallIcon(R.drawable.ic_launcher_foreground)
            //        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
            //        .setContentText("Loaded "+accessCount+ " items from the web")
            //        .setContentTitle("MovieApp")
            //        .build();
            MovieDBAdapter dbadapter = new MovieDBAdapter(con);
            for(Movie movie : innerMovie){
                dbadapter.addItem(movie.getTitle(),movie.getActors(),movie.getRating(),movie.getLength(),movie.getGenre(),movie.getURL(),movie.getDesc());
                new ImageGrab().execute(movie);
                if(!movies.contains(movie))
                    movies.add(movie);
                adapter.notifyDataSetChanged();
            }
            //NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
            //try{
            //    if(m!=null) {
            //        m.notify("", 0, customNotification);
            //    }
            //}catch(NullPointerException e){
            //    e.printStackTrace();
            //}
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar = findViewById(R.id.prgBar);
            pBar.setProgress(values[0]);
        }
    }


    public class ImageGrab extends AsyncTask<Movie,Integer,Movie> {

        @Override
        protected Movie doInBackground(Movie[] movies) {
            Movie working = movies[0];
            try {
                URL mUrl = new URL(working.getURL());
                URLConnection urlConnection = mUrl.openConnection();
                working.setImg(BitmapFactory.decodeStream(urlConnection.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            adapter.notifyDataSetChanged();
        }
    }
}