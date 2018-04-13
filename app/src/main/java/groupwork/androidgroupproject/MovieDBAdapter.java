package groupwork.androidgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBAdapter extends SQLiteOpenHelper {
    SQLiteDatabase m_DB;
   public static final String DBNAME = "MovieDB";
    public static final String DBTABLE = "Movies";
    public static final String DBKEY = "movieID";
    public static final String cTITLE ="Movie_Title";
    public static final String cACTORS ="Main_Actors";
    public static final String cRATING ="Rating";
    public static final String cLENGTH ="Movie_Length";
    public static final String cGENRE ="Genre";
    public static final String cURL = "URL";
    public static final int VERSIONNUMBER = 0;

    public MovieDBAdapter(Context cx){
        super(cx,DBNAME,null, VERSIONNUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DBTABLE + "( "+ DBKEY + "  INTEGER PRIMARY KEY autoincrement," +
                        cTITLE +"  varchar(255), " +cACTORS+ "  varchar(255), "+ cRATING+ " Integer, "
                        + cLENGTH+ " Integer, " + cGENRE+ "  varchar(255), "+ cURL + " varchar(255));");


    }
    public void addItem(String title, String actors, int rating, int len, String genre, String url){
        ContentValues inItem = new ContentValues();
        inItem.put(cTITLE,title);
        inItem.put(cACTORS,actors);
        inItem.put(cRATING,rating);
        inItem.put(cLENGTH,len);
        inItem.put(cGENRE,genre);
        inItem.put(cURL,url);
        m_DB.insert(DBTABLE,null,inItem);
    }
    public void remItem(int id){
        m_DB.delete(DBTABLE,"movieID = ",new String[]{""+id});
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
