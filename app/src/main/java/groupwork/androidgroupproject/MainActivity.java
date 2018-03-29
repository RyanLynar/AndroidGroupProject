package groupwork.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
    private ProgressBar progress;
    Button movieBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar)findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);

        Button patientButton = (Button) findViewById(R.id.patientButton);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 5);
            }
        });
        movieBtn = findViewById(R.id.movieLaunch);
        movieBtn.setOnClickListener(t-> {
            Intent in = new Intent(this,MovieDBActivity.class);
            startActivity(in);
        });
        Button patientButton =  findViewById(R.id.patientButton);
        patientButton.setOnClickListener(t -> {

                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 5);

        });
    }
}
