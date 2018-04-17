package groupwork.androidgroupproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by DellPC on 4/15/2018.
 */

public class AddPatient extends Activity {

    EditText firstText;
    EditText addressText;
    EditText birthText;
    EditText phoneText;
    EditText healthText;
    EditText descText;
    EditText reasonText;
    EditText reasontwoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        firstText = findViewById(R.id.firstText);
        addressText = findViewById(R.id.addressText);
        birthText = findViewById(R.id.birthText);
        phoneText = findViewById(R.id.phoneText);
        healthText = findViewById(R.id.healthText);
        descText = findViewById(R.id.descText);
        reasonText = findViewById(R.id.reasonText);
        reasontwoText = findViewById(R.id.reasontwoText);
        Button addDoctor = findViewById(R.id.addDoctor);


        addDoctor.setOnClickListener(t->{
            addingPatient();
        });
    }

    public void addingPatient(){
        PatientDBHelper db = new PatientDBHelper(this);
        db.addItem(firstText.getText().toString(),addressText.getText().toString(),birthText.getText().toString(),Double.parseDouble(phoneText.getText().toString())
                ,healthText.getText().toString(),descText.getText().toString(),reasonText.getText().toString(),reasontwoText.getText().toString());
        firstText.setText("");
        addressText.setText("");
        birthText.setText("");
        phoneText.setText("");
        healthText.setText("");
        descText.setText("");
        reasonText.setText("");
        reasontwoText.setText("");


    }

}
