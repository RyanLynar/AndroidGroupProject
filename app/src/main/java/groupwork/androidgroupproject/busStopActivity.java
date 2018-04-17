package groupwork.androidgroupproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class busStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        Log.i("STOP","onCreate");
        SetupListView();

    }


    private void SetupListView()
    {
        ArrayList<BusHelper.BusRouteInfo> routes = BusHelper.getInstance().getRoutesForLockedStopNum();
        Log.i("STOP",Integer.toString(routes.size()));
        ListView busListView = findViewById(R.id.busStopListView);
        SearchingAdapter adapter = new SearchingAdapter(this,R.id.busStopListView,routes);
        busListView.setAdapter(adapter);
        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BusHelper.getInstance().LockStopNumber(routes.get(i).stopNumber);
                BusHelper.getInstance().LockRoute(routes.get(i).routeNumber,routes.get(i).direction);
                Intent in = new Intent(getApplicationContext(),busTripActivity.class);
                startActivity(in);
            }});


    }
    public class SearchingAdapter extends ArrayAdapter<BusHelper.BusRouteInfo> {


        Context con;
        SearchingAdapter(@NonNull Context context, int resource, @NonNull List<BusHelper.BusRouteInfo> objects) {
            super(context, resource, objects);
            con = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_stop_list_row,parent,false);
            }
            BusHelper.BusRouteInfo routeInfo = getItem(position);
            TextView routeDescription = convertView.findViewById(R.id.bus_desc);
            TextView busNumber = convertView.findViewById(R.id.bus_number);
            TextView stopNumber = convertView.findViewById(R.id.stop_num);
            TextView routeDir = convertView.findViewById(R.id.route_dir);
            Button deleteButton = convertView.findViewById(R.id.bus_btn);
            if(routeInfo != null) {
                deleteButton.setText("SAVE");
                busNumber.setText(Integer.toString(routeInfo.routeNumber));
                stopNumber.setText(Integer.toString(routeInfo.stopNumber));
                routeDir.setText(routeInfo.direction);
                routeDescription.setText(routeInfo.routeDescription);


                deleteButton.setOnClickListener((View t) -> {
                    BusHelper.getInstance().AddRouteToDB(routeInfo);
                    Snackbar sb = Snackbar.make(t.getRootView(),busNumber.getText().toString() + " " + routeDescription.getText().toString() + " saved.",Snackbar.LENGTH_SHORT);
                    sb.show();
                });
            }
            return convertView;
        }
    }
}
