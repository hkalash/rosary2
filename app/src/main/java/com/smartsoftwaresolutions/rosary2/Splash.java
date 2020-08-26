package com.smartsoftwaresolutions.rosary2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

//import com.facebook.stetho.Stetho;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.notification.AlarmReceiver;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;

public class Splash extends AppCompatActivity {
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    SharedPreferences SpType ;
    public static final String PREFS_NAME = "Saved_Type";
    String show_help,value_start;
    public PendingIntent pendingIntent;
    int time_interval;
    int SPLASH_DISPLAY_LENGTH;
    /** Called when the activity is first created. */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(BuildConfig.FLAVOR.equals("Free")){
            setContentView(R.layout.activity_splash);
            SPLASH_DISPLAY_LENGTH = 1000;
        }else{
            //all users
            setContentView(R.layout.fatiha);
            SPLASH_DISPLAY_LENGTH = 3500;
        }


     //   Stetho.initializeWithDefaults(this);



        //sqlite data inspection intilisation
        SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Reading from SharedPreferences
      show_help = SpType.getString("show_help", "1");
      time_interval=SpType.getInt("interval",4);
//        value_start=SpType.getString("start","");
//        if (value_start.equals("")){
//            SharedPreferences.Editor editor = SpType.edit();
//            editor.putString("show_help","1");
//            editor.commit();
//        }

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /** Duration of wait **/
        // SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(show_help.equals("1")){
                    Intent mainIntent = new Intent(Splash.this, Help_Activity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }else {
                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);



        // create the database
        // we check if the User table have recordes in it or not to check if it is the first time use the system or not
        String query = "select * from Zeker_Tbl ";
        // the cursor will endicate if there is a result to the select statment or not
        Cursor cursor = AndPOS_db.rawQuery(query, null);
        // the the retain quary is one  that mean there is a record for the user
        if(cursor.getCount()>=1){

          //  Toast.makeText(this, R.string.help_message,Toast.LENGTH_LONG).show();

        }else {

            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('سُـبْـحَـانَ ٱلله','Subḥāna llah ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('ٱلْـحَـمْـدُ للهِ','Al-ḥamdu li-llāh ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('الله أكبر','Allāhu ʾakbar',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('حسبنا الله ونعم الوكيل',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('سبحان الله والحمد لله ولا إله إلا الله والله أكبر',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('سبحان الله وبحمده, سبحان الله العظيم',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('لا حول ولا قوة إلا بالله',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('اللهم صل على سيدنا محمد وعلى آله وصحبه وسلم',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('لا إله إلا الله وحده لا شريك له, له الملك وله الحمد, وهو على كل شيء قدير',' ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('لا إله إلا الله محمد رسول الله ','lā ilāha illā llāhu muḥammadur rasūlu llāh ',0)");

            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('لا حول ولاقوة إلا بالله','La hawla wa la quwwata illa billah ',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('أستغفر الله العظيم','Astaghfir Allah Al-azim',0)");
            AndPOS_db.execSQL("INSERT INTO Zeker_Tbl (Zeker_Name_Arabic,Zeker_Name_native,Zeker_count)VALUES ('سبحان الله و بحمده سبحان الله العظيم ','Subhaan-Allahi wa bihamdihi\n" +
                    "Subhaan-Allahil-Azhim',0)");




            AndPOS_db.execSQL("INSERT INTO Duaa_Tbl (Duaa_Name,Duaa_Dis)VALUES (' دعاء للأم',' ربّ لا تريني بِأُمي ضعفاً يبكيني، واجعلني بها من البارين يا أرحم الراحمين.')");
            AndPOS_db.execSQL("INSERT INTO Duaa_Tbl (Duaa_Name,Duaa_Dis)VALUES ('Prayer for mother','God save my mother')");


            // if it is the first time the app is installed  start the reminder with default every 4 hours
            start();
        }
//Boolean b=CheckAlertService(this);
//if (b ==false){
//    start();
//}
    }
     void start() {
         /* Retrieve a PendingIntent that will perform a broadcast */
         Intent alarmIntent = new Intent(this, AlarmReceiver.class);
         pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // every 1 sccound =1000 mili and every one min =60 sec and every one hour =60 min so number of sec in one hour is 3600 in milisec 3600000
        int interval = time_interval*3600000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, R.string.alarm_set, Toast.LENGTH_SHORT).show();
    }
    // to check if the alarm is running or not
//    public boolean CheckAlertService(Context context)
//    {
//        Intent i = new Intent(context, AlarmReceiver.class);
//        Boolean alarmup=(PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE)!=null);
//        return alarmup;
//    }
    }

