package groupwork.androidgroupproject.MoviePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBAdapter extends SQLiteOpenHelper {
    private SQLiteDatabase m_DB;
   private static final String DBNAME = "MovieDB";
    public static final String DBTABLE = "Movies";
    public static final String DBKEY = "movieID";
    public static final String cTITLE ="Movie_Title";
    public static final String cACTORS ="Main_Actors";
    public static final String cRATING ="Rating";
    public static final String cLENGTH ="Movie_Length";
    public static final String cGENRE ="Genre";
    public static final String cURL = "URL";
    public static final String cDESC = "Description";
    private static final int VERSIONNUMBER = 1;

    MovieDBAdapter(Context cx){
        super(cx,DBNAME,null, VERSIONNUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DBTABLE + "( "+ DBKEY + "  INTEGER PRIMARY KEY autoincrement," +
                        cTITLE +"  varchar(255), " +cACTORS+ "  varchar(255), "+ cRATING+ " double, "
                        + cLENGTH+ " integer, " + cGENRE+ "  varchar(255), "+ cURL + " varchar(255),"+ cDESC + " varchar(255));");


    }
    public void addItem(String title, String actors, double rating, int len, String genre, String url,String desc){
        m_DB = this.getWritableDatabase();
        ContentValues inItem = new ContentValues();
        inItem.put(cTITLE,title);
        inItem.put(cACTORS,actors);
        inItem.put(cRATING,rating);
        inItem.put(cLENGTH,len);
        inItem.put(cGENRE,genre);
        inItem.put(cURL,url);
        inItem.put(cDESC,desc);
        m_DB.insert(DBTABLE,null,inItem);
        m_DB.close();
    }
    public void remItem(int id){
        m_DB= getWritableDatabase();
        m_DB.delete(DBTABLE,"movieID = "+id,null);
        m_DB.close();
        Log.i("Delete entry","Del");
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
