package groupwork.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.util.Log;
import android.widget.Button;

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

    }
}
