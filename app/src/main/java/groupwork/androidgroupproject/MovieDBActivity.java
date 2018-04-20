package groupwork.androidgroupproject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MovieDBActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);

        Button toastBtn = findViewById(R.id.searchBtn);
        toastBtn.setOnClickListener((View t) ->{
            Toast toast =  Toast.makeText(getApplicationContext(),"One Day I dream of doing something",Toast.LENGTH_SHORT );
            toast.setMargin(50,50);
            toast.show();
            Notification customNotification = new NotificationCompat.Builder(getApplicationContext(),"0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentText("But not yet")
                    .build();
            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            try{
                m.notify("",0,customNotification);
            }catch(NullPointerException e){

            }

            Snackbar sb = Snackbar.make(t,"Hello World",Snackbar.LENGTH_SHORT);
            sb.show();
        });
    }
}