package groupwork.androidgroupproject.MoviePackage;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;
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
    public static ArrayList<Movie> movies = new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);
        Button addMovieBtn = findViewById(R.id.addMovieBtn);


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

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNet = manager.getActiveNetworkInfo();
        if(activeNet!=null && activeNet.isConnectedOrConnecting()){
            new SiteParser(this).execute("");
        }
    }
    public void checkDB(){
        MovieDBAdapter dba = new MovieDBAdapter(this);
        MovieAdapter movieAdapter = new MovieAdapter(this,R.id.movieList,movies);

        ListView movieList= findViewById(R.id.movieList);
        movieList.setAdapter(movieAdapter);
        m_db = dba.getWritableDatabase();
        Cursor curse = m_db.rawQuery("SELECT * FROM "+dba.DBTABLE+";",null);
        curse.moveToFirst();
        while(!curse.isAfterLast()){
          movies.add(new Movie(curse.getInt(curse.getColumnIndex(dba.DBKEY)),curse.getString(curse.getColumnIndex(dba.cTITLE)),curse.getString(curse.getColumnIndex(dba.cACTORS))
                  ,curse.getString(curse.getColumnIndex(dba.cDESC)),curse.getString(curse.getColumnIndex(dba.cGENRE)),curse.getString(curse.getColumnIndex(dba.cURL))
                  ,curse.getDouble(curse.getColumnIndex(dba.cRATING)), curse.getInt(curse.getColumnIndex(dba.cLENGTH))));
            curse.moveToNext();
        }
        movieAdapter.notifyDataSetChanged();
        curse.close();
        m_db.close();
    }
public class SiteParser extends AsyncTask<String,Integer,ArrayList<Movie>>
    {
        Context con;
       String title;
       String actors;
       String genre;
       String url;
       String desc;
       double rating;
       int length;
       public SiteParser(Context c){
           con= c;
       }
       @Override
       protected ArrayList<Movie> doInBackground(String... list) {

           try {
               URL webHook = new URL("http://torunski.ca/CST2335/MovieInfo.xml");
               URLConnection connecter = webHook.openConnection();
               XmlPullParser parser = Xml.newPullParser();
               parser.setInput(connecter.getInputStream(), null);
               while (parser.next() != XmlPullParser.END_DOCUMENT) {
                   if(parser.getName()!=null&& parser.getEventType()==XmlPullParser.END_TAG){
                       if(parser.getName().equals("Movie")){
                           Movie temp = new Movie(title,actors,desc,genre,url,rating,length);
                           URL tUrl = new URL(url);
                           URLConnection con  = tUrl.openConnection();
                           temp.setImg(BitmapFactory.decodeStream(con.getInputStream()));
                           movies.add(temp);
                       }
                   }
                   if(parser.getName()!=null && parser.getEventType() == XmlPullParser.START_TAG){
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
        return movies;
       }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            Notification customNotification = new NotificationCompat.Builder(getApplicationContext(),"0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentText("Loaded "+movies.size()+ " items from the web")
                    .setContentTitle("MovieApp")
                    .build();
            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            try{
                m.notify("",0,customNotification);
            }catch(NullPointerException e){
            }
;        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar pBar = findViewById(R.id.prgBar);
            pBar.setProgress(values[0]);
        }
    }
}