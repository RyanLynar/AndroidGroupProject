package groupwork.androidgroupproject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import groupwork.androidgroupproject.R;

public class PatientAdapter extends ArrayAdapter<Patient> {
    private Patient patient;
    public PatientAdapter(@NonNull Context context, int resource, @NonNull List<Patient> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        patient = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_patient_list,parent,false);
        }
        TextView patientTitle = convertView.findViewById(R.id.patientTitle);
        patientTitle.setText(Patient.getFName());
        return convertView;
    }
}
