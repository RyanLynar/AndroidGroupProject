package groupwork.androidgroupproject.MoviePackage;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import groupwork.androidgroupproject.R;

public class Movie {
    private int movieID;
    private String movieDesc;
    private String movieTitle;
    private String movieGenre;
    private String movieActors;
    private String movieURL;
    private double movieRating;
    private int movieLength;
    private Bitmap movieIMG;

    public Movie(int id){
        this.movieID= id;
        this.movieIMG = null;

    }


    Movie(String title, String actors, String desc, String genre, String url, double rating, int length) {
        movieTitle=title;
        movieActors=actors;
        movieDesc=desc;
        movieGenre=genre;
        movieURL= url;
        movieRating=rating;
        movieLength=length;
        movieIMG= null;
    }
    public String getTitle(){
        return movieTitle;
    }
    public String getURL(){return movieURL;}
    public String getGenre(){return movieGenre;}
    public String getActors(){return movieActors;}
    public String getDesc(){return movieDesc;}
    public double getRating(){return movieRating;}
    public int getLength(){return movieLength;}
    public Bitmap getImg(){return movieIMG;}
    public void setImg(Bitmap bmp){movieIMG=bmp;}

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie) {
            Movie compare = (Movie) obj;
            return this.toString().equals(compare.toString());
        }
        return false;
    }
    public String toString(){
        return this.getTitle() +" " + this.getDesc() +" " + this.getURL() +" " + this.getRating() +" " + this.getLength() +" " + this.getActors() +" " + this.getGenre();
    }
}
