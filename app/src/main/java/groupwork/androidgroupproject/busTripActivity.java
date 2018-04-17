package groupwork.androidgroupproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class busTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_trip);
        SetupListView();
    }

    private void SetupListView()
    {
        ArrayList<BusHelper.BusTripInfo> trips = BusHelper.getInstance().getTimesForLockedStopNumAndRoute();
        ListView busListView = findViewById(R.id.busTripListView);
        SearchingAdapter adapter = new SearchingAdapter(this,R.id.busTripListView,trips);
        busListView.setAdapter(adapter);



    }
    public class SearchingAdapter extends ArrayAdapter<BusHelper.BusTripInfo> {


        Context con;

        SearchingAdapter(@NonNull Context context, int resource, @NonNull List<BusHelper.BusTripInfo> objects) {
            super(context, resource, objects);
            con = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_route_list_row, parent, false);
            }
            BusHelper.BusTripInfo tripInfo = getItem(position);
            TextView busRouteHeading = convertView.findViewById(R.id.busRouteHeading);
            TextView busLongitude = convertView.findViewById(R.id.busLongitude);
            TextView busLatitude = convertView.findViewById(R.id.busLatitude);
            TextView bus_gpsspeed = convertView.findViewById(R.id.bus_gpsspeed);
            TextView bus_tripStartTime = convertView.findViewById(R.id.bus_tripStartTime);
            TextView bus_adjustedScheduleTime = convertView.findViewById(R.id.bus_adjustedScheduleTime);
            if (tripInfo != null) {
                busRouteHeading.setText(tripInfo.routeDescription);
                busLongitude.setText(Double.toString(tripInfo.Longitude) + " E");
                busLatitude.setText(Double.toString(tripInfo.Latitude) + " N");
                bus_gpsspeed.setText(Double.toString(tripInfo.Speed)+ " km/h");
                bus_tripStartTime.setText(tripInfo.Time);
                bus_adjustedScheduleTime.setText(tripInfo.Adj);


            }



            return convertView;
        }
    }
}
