package groupwork.androidgroupproject.MoviePackage;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import groupwork.androidgroupproject.R;

public class Movie {
    int movieID;
    String movieTitle,movieGenre,movieActors,movieDesc,movieURL;
    double movieRating;
    int movieLength;
    Bitmap movieIMG;

    public Movie(int id){
        this.movieID= id;
        this.movieIMG = movieIMG;

    }

    public Movie(int id, String title, String actors, String desc, String genre, String url, double rating, int length) {
        movieID =id;
        movieTitle=title;
        movieActors=actors;
        movieDesc=desc;
        movieGenre=genre;
        movieURL= url;
        movieRating=rating;
        movieLength=length;
    }

    public String getTitle(){
        return movieTitle;
    }
    public String getURL(){return movieURL;}


}
