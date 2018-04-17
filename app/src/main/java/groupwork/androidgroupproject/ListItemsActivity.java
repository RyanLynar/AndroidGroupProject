package groupwork.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import static groupwork.androidgroupproject.PatientDBHelper.PTABLE;
import groupwork.androidgroupproject.R;


public class ListItemsActivity extends Activity {
    SQLiteDatabase patientDB;
    PatientAdapter patientAdapter;
    public static ArrayList<Patient> patients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_db);
        patientAdapter = new PatientAdapter(this,R.id.patientList,patients);
        ListView patientList = findViewById(R.id.patientList);
        patientList.setAdapter(patientAdapter);
        Button addPatient = findViewById(R.id.patientAdding);
        addPatient.setOnClickListener(v -> {
            Intent intent = new Intent(this  , AddPatient.class);
            startActivity(intent);
        });
    }
    protected void onResume() {
        super.onResume();
        checkDB();
        patientAdapter.notifyDataSetChanged();

    }

    public void checkDB(){
        PatientDBHelper db = new PatientDBHelper(this);
        patientDB = db.getWritableDatabase();
            Cursor crsor = patientDB.rawQuery("SELECT * FROM "+ PatientDBHelper.PTABLE+";",null);
            crsor.moveToFirst();
            while(!crsor.isAfterLast()){
                patients.add(new Patient(crsor.getInt(crsor.getColumnIndex(db.PATIENTKEY)),crsor.getString(crsor.getColumnIndex(db.cName))
                        ,crsor.getString(crsor.getColumnIndex(db.cAddress))
                        ,crsor.getString(crsor.getColumnIndex(db.cBirthday)),crsor.getDouble(crsor.getColumnIndex(db.cPhoneNumber))
                        ,crsor.getString(crsor.getColumnIndex(db.cHealthCard))
                        ,crsor.getString(crsor.getColumnIndex(db.cDesc)), crsor.getString(crsor.getColumnIndex(db.cReason))
                        , crsor.getString(crsor.getColumnIndex(db.cReasonTwo))));
                crsor.moveToNext();
            }
            patientAdapter.notifyDataSetChanged();
            crsor.close();
            patientDB.close();


    }

    }

