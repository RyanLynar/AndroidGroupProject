package groupwork.androidgroupproject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
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
        patientAdapter = new PatientAdapter(this, R.id.patientList, patients);
        ListView patientList = findViewById(R.id.patientList);
        patientList.setAdapter(patientAdapter);
        Button addPatient = findViewById(R.id.patientAdding);
        Button delPatient = findViewById(R.id.delPatient);

        addPatient.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPatient.class);
            startActivity(intent);

        });


    }

    protected void onResume() {
        super.onResume();
        dbChecker();
        patientAdapter.notifyDataSetChanged();

    }

    public void dbChecker() {
        PatientDBHelper db = new PatientDBHelper(this);
        patientDB = db.getWritableDatabase();
        Cursor crsor = patientDB.rawQuery("SELECT * FROM " + PatientDBHelper.PTABLE + ";", null);
        crsor.moveToFirst();
        while (!crsor.isAfterLast()) {
            Patient p = new Patient(crsor.getInt(crsor.getColumnIndex(db.PATIENTKEY)), crsor.getString(crsor.getColumnIndex(db.cName))
                    , crsor.getString(crsor.getColumnIndex(db.cAddress))
                    , crsor.getString(crsor.getColumnIndex(db.cBirthday)), crsor.getDouble(crsor.getColumnIndex(db.cPhoneNumber))
                    , crsor.getString(crsor.getColumnIndex(db.cHealthCard))
                    , crsor.getString(crsor.getColumnIndex(db.cDesc)), crsor.getString(crsor.getColumnIndex(db.cReason))
                    , crsor.getString(crsor.getColumnIndex(db.cReasonTwo)));
            if (!patients.contains(p)) {
                patients.add(p);
                patientAdapter.notifyDataSetChanged();
            }

            crsor.moveToNext();
        }
        patientAdapter.notifyDataSetChanged();
        crsor.close();
        patientDB.close();

    }

    public class SiteParser extends AsyncTask<Void, Integer, ArrayList<Patient>> {

        ProgressBar pBar;
        Context con;
        String name;
        int key;
        String address;
        String birth;
        Double phone;
        String health;
        String reasonOne;
        String reasonTwo;
        String desc;
        int accessCount;


        @Override
        protected ArrayList<Patient> doInBackground(Void... voids) {
            accessCount = 0;
            ArrayList<Patient> tempList = new ArrayList<>();
            try {
                URL webHook = new URL("http://torunski.ca/CST2335/PatientList.xml");
                URLConnection connecter = webHook.openConnection();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(connecter.getInputStream(), null);
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getName() != null && parser.getEventType() == XmlPullParser.END_TAG) {
                        if (parser.getName().equals("Patient")) {
                            accessCount++;
                            Patient temp = new Patient(key, name, address, birth, phone, health, desc, reasonOne, reasonTwo);
                            if (!tempList.contains(temp)) {
                                tempList.add(temp);
                            }
                        }
                    }
                    if (parser.getName() != null && parser.getEventType() == XmlPullParser.START_TAG) {
                        publishProgress(0);
                        if (parser.getName().equals("Name") && parser.next() == XmlPullParser.TEXT) {
                            name = parser.getText();
                            onProgressUpdate(14);
                        } else if (parser.getName().equals("Address") && parser.next() == XmlPullParser.TEXT) {
                            address = parser.getText();
                            onProgressUpdate(28);
                        } else if (parser.getName().equals("Birthday") && parser.next() == XmlPullParser.TEXT) {
                            birth = parser.getText();
                            onProgressUpdate(42);
                        } else if (parser.getName().equals("PhoneNumber") && parser.next() == XmlPullParser.TEXT) {
                            phone = Double.parseDouble(parser.getText());
                            onProgressUpdate(56);
                        } else if (parser.getName().equals("HealthCard") && parser.next() == XmlPullParser.TEXT) {
                            health = parser.getText();
                            onProgressUpdate(60);
                        } else if (parser.getName().equals("Description") && parser.next() == XmlPullParser.TEXT) {
                            desc = parser.getText();
                            onProgressUpdate(78);

                        }
                    }

                }

            } catch (IOException e) {
                Log.e("IOE", e.getMessage());
            } catch (XmlPullParserException e) {
                Log.e("XMLERROR", "XML error " + e.getMessage());
            }
            return tempList;
        }


        @Override
        protected void onPostExecute(ArrayList<Patient> innerPatient) {
            pBar.setVisibility(ProgressBar.INVISIBLE);
            Notification customNotification = new NotificationCompat.Builder(getApplicationContext(), "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentText("Loaded " + accessCount + " items from the web")
                    .setContentTitle("Patient List")
                    .build();
            PatientDBHelper dbHelp = new PatientDBHelper(con);
            for (Patient patient : innerPatient) {
                dbHelp.addItem(patient.getFName(), patient.getAddress(), patient.getBirth(), patient.getPhone(),
                        patient.getHealth(), patient.getDesc(), patient.getReason(), patient.getReasonTwo());
                if (!patients.contains(patient))
                    patients.add(patient);
                patientAdapter.notifyDataSetChanged();
            }
            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            try {
                if (m != null) {
                    m.notify("", 0, customNotification);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }


}

