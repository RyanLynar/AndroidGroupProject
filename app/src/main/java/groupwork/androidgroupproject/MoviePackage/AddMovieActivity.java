package groupwork.androidgroupproject.MoviePackage;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import groupwork.androidgroupproject.R;

public class AddMovieActivity extends Activity {
    Toast alertText;
    EditText titleText;
    EditText genreText;
    EditText lenText;
    EditText ratingText;
    EditText urlText;
    EditText actorText;
    EditText descText;
    int finCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleText = findViewById(R.id.movieTitle);
        genreText= findViewById(R.id.movieGenre);
        lenText= findViewById(R.id.movieDuration);
        ratingText = findViewById(R.id.movieRating);
        urlText = findViewById(R.id.movieUrl);
        actorText= findViewById(R.id.movieActor);
        descText = findViewById(R.id.movieDesc);
        setContentView(R.layout.activity_add_movie);
        Button addMovieBtn =findViewById(R.id.confirmBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        alertText = Toast.makeText(this,"",Toast.LENGTH_LONG);

        addMovieBtn.setOnClickListener(t->{
            verify();
            alertText.show();
        });
        cancelBtn.setOnClickListener(t-> finish());


    }
    public void verify(){
        boolean failFlag = false;
        String resMessage = "Your movie was not added: ";

        if(verifyTitle()) {
            resMessage += "Title cannot be empty ";
            failFlag= true;
        }
        if(verifyDuration()){
            resMessage += "Duration cannot be empty ";
            failFlag= true;
        }
        if(verifyGenre()){
            resMessage+= "Genre cannot be empty ";
            failFlag= true;
        }
        if(!verifyRating()) {
            resMessage += "Rating cannot be empty ";
            failFlag= true;
        }
        if(verifyURL()) {
            resMessage += "Url cannot be empty ";
            failFlag= true;
        }
        if(verifyActor()) {
            resMessage += "Actors cannot be empty ";
            failFlag= true;
        }
        if(verifyDesc()){
            resMessage +="Description cannot be empty ";
        }
        if(!failFlag){
            resMessage = "Your movie has been added";

            MovieDBAdapter dba = new MovieDBAdapter(this);
            dba.addItem(titleText.getText().toString(),actorText.getText().toString(),Double.parseDouble(ratingText.getText().toString()),Integer.parseInt(lenText.getText().toString()),genreText.getText().toString(),urlText.getText().toString(),descText.getText().toString());
            titleText.setText("");
            actorText.setText("");
            ratingText.setText("");
            lenText.setText("");
            genreText.setText("");
            actorText.setText("");
            descText.setText("");
            urlText.setText("");
            finCode= 1;
        }
        alertText.setText(resMessage);


    }
    public boolean verifyTitle(){
        titleText = findViewById(R.id.movieTitle);
        return titleText.getText().toString().equals("");
    }
    public boolean verifyGenre(){
        genreText= findViewById(R.id.movieGenre);
        return genreText.getText().toString().equals("");
    }
    public boolean verifyDesc(){
        descText = findViewById(R.id.movieDesc);
        return descText.getText().toString().equals("");
    }
    public boolean verifyDuration(){
        lenText = findViewById(R.id.movieDuration);
        if(!lenText.getText().toString().equals("")) {
            int res = Integer.parseInt(lenText.getText().toString());
            Log.i("check",""+res);
            return res < 0;
        }
        return false;
    }
    public boolean verifyRating() {
        ratingText = findViewById(R.id.movieRating);
        if (!ratingText.getText().toString().equals("")){
            double res = Double.parseDouble(ratingText.getText().toString());
            Log.i("check",""+res);
            return res >= 0 && res <= 5;
        }
        return false;
    }
    public boolean verifyURL(){
        urlText= findViewById(R.id.movieUrl);
        return urlText.getText().toString().equals("");

    } public boolean verifyActor(){
        actorText = findViewById(R.id.movieActor);
        return actorText.getText().toString().equals("");
    }
}
