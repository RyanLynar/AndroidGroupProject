package groupwork.androidgroupproject.MoviePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import groupwork.androidgroupproject.R;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private ImageView movieImg;
    private Movie movie;
    Context con;
    MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        con = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        movie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list,parent,false);
        }
        TextView movieName = convertView.findViewById(R.id.movieTitle);
        movieImg = convertView.findViewById(R.id.movieImg);
        if(movie!=null) {
            movieName.setText(movie.getTitle());
            movieImg.setImageBitmap(movie.getImg());
        }
        return convertView;
    }

}
