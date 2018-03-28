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
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] patientInfo = new String[] { "Name", "Address", "Birthday", "Phone Number",
                "Health Card Number", "Description of visit"};
        ArrayList<String> PatientList = new ArrayList<String>();
        PatientList.addAll( Arrays.asList(patientInfo) );

        listAdapter = new ArrayAdapter<String>(this, R.layout.list_items,PatientList);

        listView.setAdapter(listAdapter);

    }}