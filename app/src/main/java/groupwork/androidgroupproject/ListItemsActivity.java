package groupwork.androidgroupproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class ListItemsActivity extends Activity {
    private ArrayAdapter<String> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_intake);

        String[] patientInfo = new String[] { "Name", "Address", "Birthday", "Phone Number",
                "Health Card Number", "Description of visit"};
        ArrayList<String> PatientList = new ArrayList<String>();
        PatientList.addAll( Arrays.asList(patientInfo) );

           /* Button toastBtn = findViewById(R.id.someBtn);

            toastBtn.setOnClickListener((View t) -> {
                Toast toast = Toast.makeText(getApplicationContext(), "One Day I dream of doing something", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
                Notification customNotification = new NotificationCompat.Builder(getApplicationContext(), "0")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setContentText("But not yet")
                        .build();
                NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                try {
                    m.notify("", 0, customNotification);
                } catch (NullPointerException e) {

                }

                Snackbar sb = Snackbar.make(t, "Hello World", Snackbar.LENGTH_SHORT);
                sb.show();
            });
*/
        //listAdapter = new ArrayAdapter<String>(this, R.layout.patient_intake,PatientList);

        //listView.setAdapter(listAdapter);

    }
}