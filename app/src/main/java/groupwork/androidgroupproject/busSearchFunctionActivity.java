package groupwork.androidgroupproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class busSearchFunctionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_search_function);
        BusHelper instance = BusHelper.getInstance();
        instance.db = new BusDatabase(this);


        Button findBtn = findViewById(R.id.busSearchButton);
        EditText busSearchField = findViewById(R.id.busStopET);
        SetupListView();

        Log.i("BUS","onCreate");
        findBtn.setOnClickListener((View t) ->{
            Toast toast =  Toast.makeText(getApplicationContext(),"Searching routes on stop: " + busSearchField.getText().toString(),Toast.LENGTH_SHORT );
            toast.setMargin(50,50);
            toast.show();
            /*Notification customNotification = new NotificationCompat.Builder(getApplicationContext(),"0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentText("But not yet")
                    .build();
            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            try{
                m.notify("",0,customNotification);
            }catch(NullPointerException e){

            }*/
            BusHelper.getInstance().LockStopNumber(Integer.parseInt(busSearchField.getText().toString()));

            Intent in = new Intent(this,busStopActivity.class);
            startActivity(in);

            Snackbar sb = Snackbar.make(t,"Searching routes on stop: " + busSearchField.getText().toString(),Snackbar.LENGTH_SHORT);
            sb.show();
        });

    }

    void SetupListView()
    {

        ArrayList<BusHelper.BusRouteInfo> routes = BusHelper.getInstance().getSavedRoutes();
        ListView busListView = findViewById(R.id.busListView);
        Context ctx = this;
        SearchingAdapter adapter = new SearchingAdapter(ctx,R.id.busListView,routes);
        busListView.setAdapter(adapter);
        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                   Log.i("CLICK","CLICK");
                                                    BusHelper.getInstance().LockStopNumber(routes.get(i).stopNumber);
                                                    BusHelper.getInstance().LockRoute(routes.get(i).routeNumber,routes.get(i).direction);
                                                   Intent in = new Intent(ctx,busTripActivity.class);
                                                   startActivity(in);
                                               }});


    }
    @Override protected  void onResume() {
        super.onResume();
        SetupListView();
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
                deleteButton.setText("DEL");
                busNumber.setText(Integer.toString(routeInfo.routeNumber));
                stopNumber.setText(Integer.toString(routeInfo.stopNumber));
                routeDir.setText(routeInfo.direction);
                routeDescription.setText(routeInfo.routeDescription);


                deleteButton.setOnClickListener((View t) -> {
                    BusHelper.getInstance().RemoveRouteFromDB(routeInfo);
                    Snackbar sb = Snackbar.make(t.getRootView(),busNumber.getText().toString() + " " + routeDescription.getText().toString() + " deleted.",Snackbar.LENGTH_SHORT);
                    sb.show();
                    SetupListView();


                });
            }
            return convertView;
        }
    }


}
