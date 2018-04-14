package groupwork.androidgroupproject.MoviePackage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list,parent,false);
        }
        TextView movieName = convertView.findViewById(R.id.movieTitle);
        ImageView movieImg = convertView.findViewById(R.id.movieImg);
        movieName.setText(movie.getTitle());
        if(movie.getURL().contains("www")){
            try {
                URL   url = new URL(movie.getURL());
                URLConnection connect = url.openConnection();
                movieImg.setImageBitmap(BitmapFactory.decodeStream(connect.getInputStream()));

            } catch (IOException e) {
                movieImg.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.no_image_url));
           }
        }
        return convertView;
    }
}
