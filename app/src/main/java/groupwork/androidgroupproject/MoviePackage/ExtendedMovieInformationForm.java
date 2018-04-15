package groupwork.androidgroupproject.MoviePackage;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import groupwork.androidgroupproject.R;

public class ExtendedMovieInformationForm extends Fragment {
    MovieAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_extended_movie_information_form, container, false);
        if(getActivity()!= null) {
           adapter = new MovieAdapter(getActivity(), R.id.movieList, MovieDBActivity.movies);
        }
        if (getArguments() != null) {
            setupLayout(result,getArguments().getInt("listItemPos"));
        }

        return result;
    }
    public void setupLayout(View result,int listPos) {
            Movie m = MovieDBActivity.movies.get(listPos);
            TextView title = result.findViewById(R.id.movieTitleText);
            title.setText(m.getTitle());
            TextView genre = result.findViewById(R.id.movieGenre);
            genre.setText(m.getGenre());
            TextView desc = result.findViewById(R.id.movieDescText);
            desc.setMovementMethod(new ScrollingMovementMethod());
            desc.setText(m.getDesc());
            TextView actors = result.findViewById(R.id.movieActor);
            actors.setMovementMethod(new ScrollingMovementMethod());
            actors.setText(m.getActors());
            TextView length = result.findViewById(R.id.movieLengthText);
            length.setText("Length:" + m.getLength() + "Min");
            setRating(m.getRating(), result);
            ImageView poster = result.findViewById(R.id.moviePosterImg);
            poster.setImageBitmap(m.getImg());
            Button dBtn = result.findViewById(R.id.deleteBtn);
            dBtn.setVisibility(View.VISIBLE);
            dBtn.setOnClickListener(t -> {
                MovieDBAdapter dba = new MovieDBAdapter(result.getContext());
                if(getActivity() instanceof MovieDBActivity) {
                    ListView mList = getActivity().findViewById(R.id.movieList);
                    mList.setAdapter(adapter);
                }
                MovieDBActivity.movies.remove(listPos);
                dba.remItem(m.getTitle(),m.getURL());
                if(adapter!=null)
                    adapter.notifyDataSetChanged();
                Toast alert = Toast.makeText(result.getContext(), "Deleted this Entry", Toast.LENGTH_LONG);
                alert.show();
            });
    }
    public static ExtendedMovieInformationForm newInstance(int pos){
        ExtendedMovieInformationForm f = new ExtendedMovieInformationForm();
        Bundle b = new Bundle();
        b.putInt("listItemPos",pos);
        f.setArguments(b);
        return f;
    }
    public void setRating(double rating,View cView){
        ImageView rate = cView.findViewById(R.id.movieRatingImg);
        if(rating == 0){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings0));
        }
        if(rating == 0.5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings0_5));
        }
        if(rating == 1){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings1));
        }
        if(rating == 1.5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings1_5));
        }
        if(rating == 2){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings2));
        }
        if(rating == 2.5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings2_5));
        }
        if(rating == 3){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings3));
        }
        if(rating == 3.5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings3_5));
        }if(rating == 4){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings4));
        }if(rating == 4.5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings4_5));
        }if(rating == 5){
            rate.setImageBitmap(BitmapFactory.decodeResource(cView.getResources(),R.drawable.movie_ratings5));
        }

    }

}
