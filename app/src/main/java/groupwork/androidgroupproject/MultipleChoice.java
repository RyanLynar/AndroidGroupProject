package groupwork.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
/**
 * Created by Adam on 2018-03-28.
 */

public class MultipleChoice extends Activity {

    protected static final String ACTIVITY_NAME="MultipleChoice";

    Button newQuiz;
    Button existingQuiz;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        newQuiz= findViewById(R.id.NewQuizButton);
        existingQuiz= findViewById(R.id.ExistingQuizButton);

        newQuiz.setOnClickListener(t->{
            Log.i(ACTIVITY_NAME,"User clicked 'new quiz'");

            intent = new Intent(MultipleChoice.this, NewQuiz.class);
            startActivity(intent);
        });
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

//                Snackbar sb = Snackbar.make(getApplicationContext(),"Hello World",Snackbar.LENGTH_SHORT);
//                sb.show();
//            });


        existingQuiz.setOnClickListener(t->{
            Log.i(ACTIVITY_NAME,"User clicked 'existing quiz'");
        });



    }


}
