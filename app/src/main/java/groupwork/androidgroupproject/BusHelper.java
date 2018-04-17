package groupwork.androidgroupproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by denni on 4/16/2018.
 */

public class BusHelper
{
    private int StopNumberToSearch;
    private int RouteNumberToSearch;
    private String RouteDirectionToSearch;
    public BusDatabase db;

    private static BusHelper s_Instance;
    public static BusHelper getInstance()
    {
        if(s_Instance == null)
        {
            s_Instance = new BusHelper();
        }
        return s_Instance;
    }
    private BusHelper()
    {
    }

    public ArrayList<BusRouteInfo> getSavedRoutes()
    {   return db.getItemsFromDB();    }
    public void RemoveRouteFromDB(BusRouteInfo routeInfo)
    {   db.remItem(Integer.toString(routeInfo.stopNumber),Integer.toString(routeInfo.routeNumber), routeInfo.direction, routeInfo.routeDescription);    }
    public void AddRouteToDB(BusRouteInfo routeInfo)
    {   db.addItem(routeInfo);    }
    public void LockStopNumber(int INstopToSearch)
    {   StopNumberToSearch = INstopToSearch;    }
    public void LockRoute(int INrouteNumToSearch, String INrouteDirToSearch)
    {
        RouteNumberToSearch= INrouteNumToSearch;
        RouteDirectionToSearch= INrouteDirToSearch;
    }

    public ArrayList<BusRouteInfo> getRoutesForLockedStopNum()
    {
        //Create XML query for locked stop number
        class BusRouteQuery extends AsyncTask<String, Integer, ArrayList<BusRouteInfo>> {
            @Override
            protected ArrayList<BusRouteInfo> doInBackground(String... strings) {
                ArrayList<BusRouteInfo> returnArray = new ArrayList<BusRouteInfo>();
                try {
                    URL url = new URL("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + StopNumberToSearch);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(500000 /* milliseconds */);
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
                        while(parser.next() != XmlPullParser.END_DOCUMENT) {
                            if(parser.getName() != null) {
                                while (parser.getName().equals("Route")) // reads next route
                                {
                                    int event = parser.getEventType();
                                    parser.next();
                                    if (event == XmlPullParser.END_TAG) {
                                        continue;
                                    }
                                    BusRouteInfo toAdd = new BusRouteInfo();
                                    toAdd.stopNumber = StopNumberToSearch;
                                    while (!parser.getName().equals("Route"))
                                    //reads until end tag of route
                                    {

                                        if (parser.getEventType() == XmlPullParser.END_TAG) {
                                        }//Exclude ending tags
                                        else if (parser.getName().equals("RouteNo"))
                                            toAdd.routeNumber = Integer.parseInt(parser.nextText().toString());
                                        else if(parser.getName().equals(("DirectionID")))
                                        {
                                            parser.nextText();
                                        }
                                        else if (parser.getName().equals("Direction"))
                                            toAdd.direction = parser.nextText().toString();
                                        else if (parser.getName().equals("RouteHeading"))
                                            toAdd.routeDescription = parser.nextText().toString();
                                        parser.next();
                                    }
                                    returnArray.add(toAdd);
                                }
                            }
                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return returnArray;
            }
        }
        BusRouteQuery query = new BusRouteQuery();

        try {
            return query.execute().get();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<BusTripInfo> getTimesForLockedStopNumAndRoute()
    {
        //Create XML query
        class BusRouteQuery extends AsyncTask<String, Integer, ArrayList<BusTripInfo>> {
            @Override
            protected ArrayList<BusTripInfo> doInBackground(String... strings) {
                ArrayList<BusTripInfo> returnArray = new ArrayList<BusTripInfo>();
                try {
                    URL url = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + StopNumberToSearch + "&routeNo=" + RouteNumberToSearch);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(500000 /* milliseconds */);
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
                        String Direction = "";
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            if(parser.getName()!= null) {
                                if (parser.getEventType() != XmlPullParser.START_TAG) {
                                } else if (parser.getName().equals("Direction")) {
                                    Direction = parser.nextText();
                                } else if (Direction.equals(RouteDirectionToSearch)) {
                                    if (parser.getName().equals("TripDestination")) {
                                        returnArray.add(new BusTripInfo());
                                        returnArray.get(returnArray.size() - 1).routeDescription = parser.nextText();
                                    } else if (parser.getName().equals("TripStartTime")) {
                                        returnArray.get(returnArray.size() - 1).Time = parser.nextText();
                                    } else if (parser.getName().equals("AdjustedScheduleTime")) {
                                        returnArray.get(returnArray.size() - 1).Adj = parser.nextText();
                                    } else if (parser.getName().equals("Latitude")) {
                                        returnArray.get(returnArray.size() - 1).Latitude = Double.parseDouble(parser.nextText());
                                    } else if (parser.getName().equals("Longitude")) {
                                        returnArray.get(returnArray.size() - 1).Longitude = Double.parseDouble(parser.nextText());
                                    } else if (parser.getName().equals("GPSSpeed")) {
                                        returnArray.get(returnArray.size() - 1).Speed = Double.parseDouble(parser.nextText());

                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return returnArray;
            }
        }

            BusRouteQuery query = new BusRouteQuery();


        try {
            return query.execute().get();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }




    public class BusRouteInfo
    {
        public int stopNumber;
        public String direction;
        public int routeNumber;
        public String routeDescription;
    }
    public class BusTripInfo
    {
        public String routeDescription = "null";
        public Double Longitude = 0.0;
        public Double Latitude = 0.0;
        public Double Speed = 0.0;
        public String Time = "null";
        public String Adj = "null";
        public String toString()
        {
            return routeDescription+" "+Longitude.toString()+" "+Latitude.toString()+" "+Speed.toString()+" "+Time+" "+Adj;
        }

    }
}


