package groupwork.androidgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by denni on 4/16/2018.
 */

public class BusDatabase extends SQLiteOpenHelper {
        private SQLiteDatabase m_DB;
        private static final String DBNAME = "BusDB";
        public static final String DBTABLE = "Bus";

        public static final String cDESC = "Description";
        public static final String cSTOPNUM = "StopNumber";
        public static final String cROUTENUM = "RouteNumber";
    public static final String cDIRECTION = "Direction";
        private static final int VERSIONNUMBER = 1;

        BusDatabase(Context cx){
            super(cx,DBNAME,null, VERSIONNUMBER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + DBTABLE + "( "+cSTOPNUM+" INTEGER, "+cROUTENUM+" INTEGER,"+cDIRECTION+ " varchar(20), " + cDESC + " varchar(1000), PRIMARY KEY ("+cSTOPNUM+" ,"+cROUTENUM+" ,"+cDIRECTION+" ,"+cDESC+"));");


        }
        public void addItem(BusHelper.BusRouteInfo INinfo)
        {
            this.addItem(INinfo.stopNumber,INinfo.routeNumber,INinfo.direction,INinfo.routeDescription);
        }

        public void addItem(Integer INstopNumber, Integer INrouteNumber, String INdirection, String INrouteDescription){
            m_DB = this.getWritableDatabase();
            ContentValues inItem = new ContentValues();
            inItem.put(cSTOPNUM,INstopNumber);
            inItem.put(cROUTENUM,INrouteNumber);
            inItem.put(cDESC,INrouteDescription);
            inItem.put(cDIRECTION,INdirection);
            try {
                m_DB.insertOrThrow(DBTABLE, null, inItem);
            }catch(Exception e){

            }
            m_DB.close();
        }
        public void remItem(String INstopNumber, String INrouteNumber, String INrouteDir, String INrouteDesc){
            m_DB= getWritableDatabase();
            m_DB.delete(DBTABLE,cSTOPNUM+" = ? AND "+cROUTENUM+" = ? AND "+cDIRECTION+" = ? AND "+cDESC+" = ?",new String[]{INstopNumber,INrouteNumber, INrouteDir, INrouteDesc});
            m_DB.close();

        }
        public ArrayList<BusHelper.BusRouteInfo> getItemsFromDB()
        {
            /*sample item*/
            ArrayList<BusHelper.BusRouteInfo> returnList = new ArrayList<BusHelper.BusRouteInfo>();
            m_DB = this.getWritableDatabase();
            Cursor curse = m_DB.rawQuery("SELECT * FROM "+DBTABLE+" ;",null);
            curse.moveToFirst();
            while(!curse.isAfterLast()) {
                BusHelper.BusRouteInfo toAdd =  BusHelper.getInstance().new BusRouteInfo();
                toAdd.routeNumber = curse.getInt(curse.getColumnIndex(cROUTENUM));
                toAdd.direction = curse.getString(curse.getColumnIndex(cDIRECTION));
                toAdd.routeDescription = curse.getString(curse.getColumnIndex(cDESC));
                toAdd.stopNumber = curse.getInt(curse.getColumnIndex(cSTOPNUM));
                if(!returnList.contains(toAdd))
                    returnList.add(toAdd);
                Log.i("query",Integer.toString(curse.getInt(curse.getColumnIndex(cROUTENUM))));
                curse.moveToNext();
            }

            curse.close();
            m_DB.close();
            return returnList;
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVerr, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + DBTABLE);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBTABLE);
            onCreate(db);
        }

    }
