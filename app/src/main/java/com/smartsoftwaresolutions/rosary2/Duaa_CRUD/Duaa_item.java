package com.smartsoftwaresolutions.rosary2.Duaa_CRUD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.shared_Menu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Duaa_item extends AppCompatActivity {
    // EditText Et_Name,Et_Search;
    EditText Et_Name;
    Button But_Add,But_Save,But_Delete;
    ListView Lv_Pay;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    Duaa_Search_Adapter ADA;
    Boolean checked=false;
    String Selected_Button="",discription,discription_e,discription_native,discription_native_e;
    int Sel_Id,Sel_Id_e;
    MenuItem myItem;
    TextView textView;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duaa_item);
        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
      //  if(BuildConfig.FLAVOR.equals("Free")){
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView15);


            // AdRequest adRequest = new AdRequest.Builder().build();
            //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("onAdLoaded","AdLoaded");

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    Log.e("onAdFailedToLoad",""+errorCode);

                }
            });
       //     Toast.makeText(Duaa_item.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
     //   }


        textView = new TextView(this);
        // textView.setText("Select an option");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(getResources().getColor(R.color.four));
        textView.setTextColor(Color.WHITE);

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();


//        Et_Name= (EditText) findViewById(R.id.et_Name);
//      //  Et_Search=(EditText)findViewById(R.id.et_Name) ;
//        But_Add=(Button)findViewById(R.id.btn_Add);
//        But_Save=(Button)findViewById(R.id.btn_Save);
//        But_Delete=(Button)findViewById(R.id.btn_Delete);

        Lv_Pay = findViewById(R.id.lv_IngD);
        FillList fillList=new FillList();
        fillList.execute("");


        Lv_Pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duaa_info2 SelectUnit;
                SelectUnit=ADA.getItem(position);
                // Et_Name.setText(SelectUnit.getU_nameList());
                Sel_Id= Integer.parseInt(SelectUnit.getU_id_Listd());
            }
        });
    }
    public class FillList extends AsyncTask<String, String, String> {

        String z = "";

       List<Duaa_info2> Tasbeh_list = new ArrayList<Duaa_info2>();


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(String r) {


            Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
            // reading from the keys that we will use to raed from the customer list they must be the same as a hash map keys


            // the ada is binding all together
            // ADA = new SimpleAdapter(SearchCustomer.this, Tasbeh_list, R.layout.customer_element, from, views);
            ADA=new Duaa_Search_Adapter(Tasbeh_list,Duaa_item.this);
            // put them in the list view

            Lv_Pay.setAdapter(ADA);





        }

        @Override
        protected String doInBackground(String... params) {

            // we specify the database helper and the database and set the database to readable
            // then we write a select statment
            String query_SQL = "select * from Duaa_Tbl ";
            // we run this select statment by creating a cursor and execute it
            Cursor cursor=AndPOS_db.rawQuery(query_SQL,null);
            // if the result of the execute statment have data then the if statment is true and we move the cursor to the first row
            if(cursor.moveToFirst()){
                do{
                    // we create a hash map with string for the key and string for the data
                    HashMap<String, String> map = new HashMap<String, String>();
                    String Duaa_id;
                    String Duaa_name;
                    String Duaa_native;


                    // we use put method to save the data in that map and we use the column name as the key
                    Duaa_id= cursor.getString(cursor.getColumnIndex("Duaa_Id"));
                    Duaa_name=cursor.getString(cursor.getColumnIndex("Duaa_Name"));
                    Duaa_native=cursor.getString(cursor.getColumnIndex("Duaa_Dis"));


                    Duaa_info2 customerInfo=new Duaa_info2(Duaa_id,Duaa_name,Duaa_native);




                    // we add the saved map to the list one by one
                    this.Tasbeh_list.add(customerInfo);
                    // this.Tasbeh_listSQL.add(map);

                }while (cursor.moveToNext());
                cursor.close();
                z = "Success";
            }else{
                z = "Error retrieving data from table";
            }



            return z;
        }


    }

    public void remove_Tasbeh_OnClickHandlerd(View v) {
        Duaa_info2 itemTo = (Duaa_info2) v.getTag();
        Sel_Id= Integer.parseInt(itemTo.getU_id_Listd());
        DeleteTesbeh delete_Tas =new DeleteTesbeh();
        delete_Tas.execute("");

    }
    public void Add_Tasbeh_OnClickHandlerd(View v) {
        Selected_Button="a";
        if (textView.getParent()!=null){
            ((ViewGroup)textView.getParent()).removeView(textView); // <- fix
        }
        inflate_dialog(Selected_Button);

    }

    public void Edit_Tasbeh_OnClickHandlerd(View v) {
        // Duaa_info2 itemTo1 = (Duaa_info2)ADA);
        Duaa_info2 itemTo1 = (Duaa_info2) v.getTag();
        //int index=  (int) v.getTag();

        Sel_Id_e= Integer.parseInt(itemTo1.getU_id_Listd());
        discription_e=itemTo1.getU_nameListd();
        discription_native_e=itemTo1.getU_name_native_Listd();

        Selected_Button="e";
        if (textView.getParent()!=null){
            ((ViewGroup)textView.getParent()).removeView(textView); // <- fix
        }
        inflate_dialog(Selected_Button);

    }

    public void inflate_dialog(String B){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_duaa_layout, null);
        final EditText et_Discription = alertLayout.findViewById(R.id.et_Discriptiond);
        final EditText et_native_discription = alertLayout.findViewById(R.id.et_native_discriptind);
//        final TextView tv_counter = alertLayout.findViewById(R.id.tv_counter);
//        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

    //    AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(Selected_Button=="a"){
            textView.setText(R.string.add_new);
            //  String s =textView.setText(getResources().getText(R.string.add_new));
            alert.setCustomTitle(textView);

           // cbToggle.setVisibility(View.INVISIBLE);

        }else {
            // we fill the textview to edit them
            // alert.setTitle(getString(R.string.edit));
            textView.setText(R.string.edit);
            //  String s =textView.setText(getResources().getText(R.string.add_new));
            alert.setCustomTitle(textView);
         //   cbToggle.setVisibility(View.VISIBLE);
            et_Discription.setText(discription_e);
            et_native_discription.setText(discription_native_e);
                    }
//        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    checked=true;
//                    // we change the counter to 0
//                    tv_counter.setText("0");
//
//                }
//
//            }
//        });





        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {



                if(Selected_Button=="a"){
// this is the save of add
                    discription = et_Discription.getText().toString();
                    discription_native = et_native_discription.getText().toString();
                  Add_tesbeh add_tesbeh=new Add_tesbeh();
                    add_tesbeh.execute("");
                }else {
                    // this is the save of edit
                    discription = et_Discription.getText().toString();
                    discription_native = et_native_discription.getText().toString();
                    Save_Tesbeh save_tesbeh=new Save_Tesbeh();
                    save_tesbeh.execute("");

                }
                Toast.makeText(getBaseContext(), "description Duaa : " + discription + " description Duaa : " + discription_native, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public class Add_tesbeh extends AsyncTask<String, String, String> {


        Boolean isSuccess = false;
        String z = "";

        // String Duaa_name_ar = Et_Name.getText().toString();
        String Duaa_name_ar = discription;
        String Duaa_name_native = discription_native;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {

            if (r == "Added Successfully") {
                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
                FillList fillList=new FillList();
                fillList.execute("");
                //Et_Name.setText("");


            } else if (r=="exist"){
                Toast.makeText( Duaa_item.this,R.string.tazkeer_already_exist,Toast.LENGTH_LONG ).show();
            }else {

                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected String doInBackground(String... params) {

            if (Duaa_name_ar.trim().equals(""))
                z = getString(R.string.please_enter_tasbeh_name);
            else {


                //Search if that family is exist
                String SearchQuary = "Select * from Duaa_Tbl where Duaa_Name='" + Duaa_name_ar + "'";


                Cursor cursor1 = AndPOS_db.rawQuery(SearchQuary, null);
                // if the result of the execute statment have data then the if statment is true and we move the cursor to the first row
                if (cursor1.moveToFirst()) {
                    z="exist";
                    // Toast.makeText(Payment.this, "the Payment name is already exist Please enter another one ", Toast.LENGTH_LONG).show();
                } else {
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    // the customer Id is auto increament

                    values.put("Duaa_Name", Duaa_name_ar);
                    values.put("Duaa_Dis", Duaa_name_native);
                    //the insert() will give -1 if the statment failed and id number if successfully inserted
                    // it took table name and the value we make a content value to put all the column names in it
                    try {
                        long rowInserted = AndPOS_db.insertOrThrow("Duaa_Tbl", null, values);
                        if (rowInserted != -1) {
                            //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                            z = "Added Successfully";
                        } else {
                            //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            z = "Something wrong";
                        }
                    } catch (SQLiteConstraintException e1) {
                        //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                        // z = e1.toString();
                        z = "The Duaa name  must be unique Please check it";
                    }


                }

            }
            return z;
        }
    }

    public class Save_Tesbeh extends AsyncTask<String,String,String>{
        Boolean isSuccess = false;
        String z = "";

        //  String Duaa_name_ar = Et_Name.getText().toString();
        String Duaa_name_ar = discription;
        String Duaa_name_native = discription_native;

        @Override
        protected void onPostExecute(String r) {
            if (r == "Updated Successfully") {
                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
               FillList fillList=new FillList();
                fillList.execute("");
                //  Et_Name.setText("");


            } else {

                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(r);
        }

        @Override
        protected String doInBackground(String... params) {
            if (Duaa_name_ar.trim().equals(""))
                z = "Please Enter Or Select Duaa Name  ";
            else {



                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                // the customer Id is auto increament

                values.put("Duaa_Name", Duaa_name_ar);
                values.put("Duaa_Dis", Duaa_name_native);



                //the insert() will give -1 if the statment failed and id number if successfully inserted
                // it took table name and the value we make a content value to put all the column names in it
                try {
                    //the update commend will take (1,2,3,4) 1 database name, 2 values , 3 condition ===> where statmente
                    long rowUpdate = AndPOS_db.update("Duaa_Tbl",values,"Duaa_Id="+Sel_Id_e,null);
                    if (rowUpdate != -1) {
                        //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                        z = "Updated Successfully";
                    } else {
                        //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        z = "Something wrong";
                    }
                } catch (SQLiteConstraintException e1) {
                    //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                    // z = e1.toString();
                    z = "The Payment name  must be unique Please check it";
                }


            }


            return z;
        }

    }

    public class DeleteTesbeh extends AsyncTask<String,String,String>{
        Boolean isSuccess = false;
        String z = "";



        @Override
        protected void onPostExecute(String r) {
            if (r.equals("Deleted Successfully")) {
                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
                FillList fillList=new FillList();
                fillList.execute("");



            } else {

                Toast.makeText(Duaa_item.this, r, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(r);
        }

        @Override
        protected String doInBackground(String... params) {
            // int SelectedId= Integer.parseInt( Sel_Id );
            int SelectedId= Sel_Id;

//            if(SelectedId==1 ||SelectedId==2||SelectedId==3 ||SelectedId==4){
//                z = "These default Tasbeh Cannot Be Deleted ";
//            }
//
//            else{

                try {
                    //the update commend will take (1,2,3) 1 database name, 2 condition ===> where statmente
                    long rowDelete = AndPOS_db.delete( "Duaa_Tbl", "Duaa_Id=" + Sel_Id, null );
                    if (rowDelete != -1) {
                        //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                        z =  getString(R.string.delete_successfuly);
                    } else {
                        //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        z = getString(R.string.somthing_wrong);
                    }
                } catch (SQLiteConstraintException e1) {
                    //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                    // z = e1.toString();
                    z = getString(R.string.the_duaa);
                }


        //    }


            return z;
        }


    }

    //menu
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        myItem = menu.findItem(R.id.menu_counter);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
        //    myItem.setTitle("count  "+t_counter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";

                shared_Menu sharedMenu=new shared_Menu();
                String flaver =BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:

                finish();
                return true;
            case R.id.action_Arabic:
                setLocale("ar");

                Toast.makeText(this, R.string.la, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_English:
                setLocale("en_US");

                Toast.makeText(this, R.string.le, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_setting:
                Intent intent=new Intent(Duaa_item.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Duaa_item.this, Help_Activity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        /// change the direction of the app from left to right to right to left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(myLocale);
        }
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
    }

