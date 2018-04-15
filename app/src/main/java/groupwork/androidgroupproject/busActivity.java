package groupwork.androidgroupproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class busActivity extends AppCompatActivity {
    public enum BusState
    {
        START,
        STOP,
        ROUTE
    }
    public class BusStopInfo
    {
        public String routeName;
        public Integer routeNumber;
        public Integer stopNumber;
        public String toString()
        {
            return routeNumber+"|"+routeName+"|"+stopNumber;
        }

    }
    public class BusRouteInfo
    {
        public String routeName;
        public Integer routeNumber;
        public Integer eta = 0;
        public String toString()
        {
            return routeNumber+"|"+routeName+"|"+eta+" min(s)";
        }
    }
    ArrayList<BusStopInfo> listedStops;
    ArrayAdapter<String> adapter;
    BusState busState = BusState.START;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        Button findBtn = findViewById(R.id.busSearchButton);
        EditText busSearchField = findViewById(R.id.busStopET);
        ListView busListView = findViewById(R.id.busListView);
        adapter = new ArrayAdapter<String>(this,R.layout.listrowitem, R.id.row_text);
        busListView.setAdapter(adapter);
        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                   switch (busState)
                                                   {
                                                       case START:
                                                           //click on database elements
                                                           break;

                                                       case STOP:
                                                           //click on stops
                                                           SearchBusRoute(listedStops.get(i).stopNumber.toString(), listedStops.get(i).routeNumber.toString());
                                                           break;

                                                       case ROUTE:
                                                           break;
                                                   }


                                               }
                                           });
                listedStops = new ArrayList<BusStopInfo>();

        /*add database stuff later*/

        adapter.notifyDataSetChanged();

        Log.i("BUS","onCreate");
        findBtn.setOnClickListener((View t) ->{
            Toast toast =  Toast.makeText(getApplicationContext(),"Searching routes on stop: " + busSearchField.getText().toString(),Toast.LENGTH_SHORT );
            toast.setMargin(50,50);
            toast.show();
            SearchBusStop(busSearchField.getText().toString());
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



            Snackbar sb = Snackbar.make(t,"Hello World",Snackbar.LENGTH_SHORT);
            sb.show();
        });



    }

    void SearchBusRoute(String stopNumber, String routeNumber)
    {
        class BusRouteQuery extends AsyncTask<String, Integer, ArrayList<BusRouteInfo>> {

            @Override
            protected ArrayList<BusRouteInfo> doInBackground(String... strings) {
                ArrayList<BusRouteInfo> returnArray = new ArrayList<BusRouteInfo>();
                try {
                    URL url = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + stopNumber + "&routeNo=" + routeNumber);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();

                    java.io.InputStream stream = conn.getInputStream();
                    try {
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(stream, null);
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            String name = parser.getName();
                            if(name == null)
                                continue;
                            if(parser.getEventType() == XmlPullParser.END_TAG)
                                continue;


                            if(name.equals("TripDestination"))
                            {
                                BusRouteInfo inf = new BusRouteInfo();
                                inf.routeNumber = Integer.parseInt(routeNumber);
                                inf.routeName = parser.nextText();
                                while(true)
                                {

                                    if(name != null)
                                        if(name.equals("AdjustedScheduleTime"))
                                            break;

                                    parser.next();
                                    name = parser.getName();
                                }
                                inf.eta = Integer.parseInt(parser.nextText());
                                returnArray.add(inf);


                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                return returnArray;
            }

        }

        BusRouteQuery busRoute  = new BusRouteQuery();
        ArrayList<BusRouteInfo> ingo = new ArrayList<BusRouteInfo>();

        try {
            ingo.addAll(busRoute.execute().get());

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        adapter.clear();
        for (BusRouteInfo inf : ingo) {
            adapter.add(inf.toString());
        }
        adapter.notifyDataSetChanged();
        busState = BusState.ROUTE;






    }



    void SearchBusStop(String stopNumber)
    {
        class BusRouteQuery extends AsyncTask<String, Integer, ArrayList<BusStopInfo>> {

            @Override
            protected ArrayList<BusStopInfo> doInBackground(String... strings) {
                ArrayList<BusStopInfo> returnArray = new ArrayList<BusStopInfo>();
                try {
                    URL url = new URL("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + stopNumber);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();

                    java.io.InputStream stream = conn.getInputStream();
                        try {
                            XmlPullParser parser = Xml.newPullParser();
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(stream, null);
                            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                                String name = parser.getName();
                                if(name == null)
                                    continue;
                                if(parser.getEventType() == XmlPullParser.END_TAG)
                                    continue;
                                Log.i("BUS", name);

                                if(name.equals("RouteNo"))
                                {
                                    BusStopInfo rtrn = new BusStopInfo();
                                    rtrn.routeNumber = Integer.parseInt(parser.nextText());
                                    while(true)
                                    {
                                        if(name != null)
                                            if(name.equals("RouteHeading"))
                                                break;

                                        parser.next();
                                        name = parser.getName();
                                    }
                                    if(name.equals("RouteHeading"))
                                    {
                                        rtrn.routeName = parser.nextText();
                                    }
                                    rtrn.stopNumber = Integer.parseInt(stopNumber);
                                    returnArray.add(rtrn);
                                }
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                    return returnArray;

            }
        }

        BusRouteQuery busRoute = new BusRouteQuery();



        listedStops.clear();
        try {
            listedStops.addAll(busRoute.execute().get());

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        adapter.clear();
        for (BusStopInfo inf : listedStops) {
            adapter.add(inf.toString());
        }
        adapter.notifyDataSetChanged();
        busState = BusState.STOP;
        Log.i("BUS", "Done");
    }


}
