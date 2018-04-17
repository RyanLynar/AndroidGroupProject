package groupwork.androidgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase patientDB;
    public static final String DBNAME = "PatientFormDB";
    public static final String PTABLE = "DoctorTableCreate";
    public static final String PATIENTKEY = "PatientKey";
    public static final String cName ="First_Name";
    public static final String cAddress ="Address";
    public static final String cBirthday ="Birthday";
    public static final String cPhoneNumber ="Phone_Number";
    public static final String cHealthCard = "Health_Card";
    public static final String cDesc = "Reason";
    public static final String cReason = "Reason";
    public static final String cReasonTwo = "Reason";
    public static final int VERSIONNUMBER = 1;

    public PatientDBHelper(Context ctx){
        super(ctx,DBNAME,null, VERSIONNUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + PTABLE + "( "+ PATIENTKEY + "  INTEGER PRIMARY KEY autoincrement," +
                        cName +"  varchar(255), "+ cAddress+ " varchar(255), "
                        + cBirthday + " varchar(255), " + cPhoneNumber+ " double, "+ cHealthCard
                        + " varchar(255),"+ cDesc+ " varchar(255)," +cReason + " varchar(255),"
                        +cReasonTwo + " varchar(255));");


    }
    public void addItem(String first, String address, String birth, double phone, String healthcard,String reason, String surgeries, String allergies){
        patientDB = this.getWritableDatabase();
        ContentValues info = new ContentValues();
        info.put(cName,first);
        info.put(cAddress,address);
        info.put(cBirthday,birth);
        info.put(cPhoneNumber,phone);
        info.put(cHealthCard,healthcard);
        info.put(cDesc,reason);
        info.put(cReason,surgeries);
        info.put(cReasonTwo,allergies);
        patientDB.insert(PTABLE,null,info);
        patientDB.close();
    }

   /* public void addItem2(String first, String address, String birth, double phone, String healthcard,String reason, String benefits,String hadbraces){
        patientDB = this.getWritableDatabase();
        ContentValues info = new ContentValues();
        info.put(cFirstName,first);
        info.put(cAddress,address);
        info.put(cBirthday,birth);
        info.put(cPhoneNumber,phone);
        info.put(cHealthCard,healthcard);
        info.put(cReason,reason);
        info.put(cBenefit,benefits);
        info.put(cBraces,hadbraces);
        patientDB.insert(DTTABLE,null,info);
        patientDB.close();
    }

    public void addItem3(String first, String last, String address, String birth, double phone, String healthcard,String reason,String dateBought, String store){
        patientDB = this.getWritableDatabase();
        ContentValues info = new ContentValues();
        info.put(cFirstName,first);
        info.put(cAddress,address);
        info.put(cBirthday,birth);
        info.put(cPhoneNumber,phone);
        info.put(cHealthCard,healthcard);
        info.put(cReason,reason);
        patientDB.insert(OPTABLE,null,info);
        patientDB.close();
    }*/
    public void remItem(int id){
        patientDB= getWritableDatabase();
        patientDB.delete(PTABLE,"PATIENTID = ",new String[]{""+id});
        patientDB.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVerr, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + PTABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PTABLE);
        onCreate(db);
    }

}
