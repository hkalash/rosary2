package com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

//import com.facebook.stetho.Stetho;
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
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Tasbeh_item extends AppCompatActivity {
   // EditText Et_Name,Et_Search;
    EditText Et_Name;
    Button But_Add,But_Save,But_Delete;
    ListView Lv_Pay;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    Tasbeh_Search_Adapter ADA;
Boolean checked=false;
    String Selected_Button="",discription,discription_e,discription_native,discription_native_e,counter,counter_e;
    int Sel_Id,Sel_Id_e;
    MenuItem myItem;
    TextView textView;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbeh_item);
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


            mAdView = findViewById(R.id.adView6);


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
         //   Toast.makeText(Tasbeh_item.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
      //  }


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

        Lv_Pay = findViewById(R.id.lv_Ing);
        FillList fillList=new FillList();
        fillList.execute("");
//        But_Add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Add_tesbeh addPay = new Add_tesbeh();
//                addPay.execute("");
//            }
//        });
//        But_Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Save_Tesbeh savePay =new Save_Tesbeh();
//                savePay.execute("");
//            }
//        });
//        But_Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DeleteTesbeh deletePay =new DeleteTesbeh();
//                deletePay.execute("");
//            }
//        });
//        Lv_Pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Tasbeh_info SelectUnit;
//                SelectUnit=ADA.getItem(position);
//               // Et_Name.setText(SelectUnit.getU_nameList());
//                Sel_Id= Integer.parseInt(SelectUnit.getU_id_List());
//            }
//        });
    }
    public class FillList extends AsyncTask<String, String, String> {

        String z = "";

        // List<Map<String, String>> Tasbeh_list  = new ArrayList<Map<String, String>>();
        List<Tasbeh_info> Tasbeh_list = new ArrayList<Tasbeh_info>();
        // List<Map<String ,String >> Tasbeh_listSQL=new ArrayList<Map<String,String>>;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(String r) {


            Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
            // reading from the keys that we will use to raed from the customer list they must be the same as a hash map keys


            // the ada is binding all together
            // ADA = new SimpleAdapter(SearchCustomer.this, Tasbeh_list, R.layout.customer_element, from, views);
            ADA=new Tasbeh_Search_Adapter(Tasbeh_list,Tasbeh_item.this);
            // put them in the list view

            Lv_Pay.setAdapter(ADA);


// Capture Text in EditText
//            Et_Search.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void afterTextChanged(Editable arg0) {
//                    // TODO Auto-generated method stub
//                    /*  String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
//                   // ADA.filter.get(text);
//                    ((SimpleAdapter)SearchCustomer.this.ADA ).getFilter().filter(text);*/
//                    //String text = editsearch.getText().toString().toLowerCase(Locale.getDefault()).contains();
//                    String text = Et_Search.getText().toString().toLowerCase(Locale.getDefault());
//                    Log.i("Searchable text", text);
//
//                    (Tasbeh_item.this.ADA).getFilter().filter(text);
//                    Lv_Pay.setAdapter(ADA);
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence arg0, int arg1,
//                                              int arg2, int arg3) {
//
//
//                    // TODO Auto-generated method stub
//                }
//
//                @Override
//                public void onTextChanged(CharSequence cs, int start, int before,
//                                          int count) {
//                    /*// TODO Auto-generated method stub
//                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
//                    Log.i("Searchable text", text);
//
//                    (SearchCustomer.this.ADA).getFilter().filter(text);
//                    LV.setAdapter(ADA);*/
//                    if (count < before) {
//                        // We're deleting char so we need to reset the adapter data
//                        ADA.resetData();
//                    }
//
//                }
//            });


        }

        @Override
        protected String doInBackground(String... params) {

            // we specify the database helper and the database and set the database to readable
            // then we write a select statment
            String query_SQL = "select * from Zeker_Tbl ";
            // we run this select statment by creating a cursor and execute it
            Cursor cursor=AndPOS_db.rawQuery(query_SQL,null);
            // if the result of the execute statment have data then the if statment is true and we move the cursor to the first row
            if(cursor.moveToFirst()){
                do{
                    // we create a hash map with string for the key and string for the data
                    HashMap<String, String> map = new HashMap<String, String>();
                    String Zeker_id;
                    String Zeker_name;
                    String Zeker_native;
                    String Zeker_counter;

                    // we use put method to save the data in that map and we use the column name as the key
                    Zeker_id= cursor.getString(cursor.getColumnIndex("Zeker_Id"));
                    Zeker_name=cursor.getString(cursor.getColumnIndex("Zeker_Name_Arabic"));
                    Zeker_native=cursor.getString(cursor.getColumnIndex("Zeker_Name_native"));
                    Zeker_counter= cursor.getString(cursor.getColumnIndex("Zeker_count"));

                    Tasbeh_info customerInfo=new Tasbeh_info(Zeker_id,Zeker_name,Zeker_native,Zeker_counter);




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

    public void remove_Tasbeh_OnClickHandler(View v) {
        Tasbeh_info itemTo = (Tasbeh_info) v.getTag();
       Sel_Id= Integer.parseInt(itemTo.getU_id_List());
       DeleteTesbeh delete_Tas =new DeleteTesbeh();
              delete_Tas.execute("");

    }
    public void Add_Tasbeh_OnClickHandler(View v) {
     Selected_Button="a";
        if (textView.getParent()!=null){
            ((ViewGroup)textView.getParent()).removeView(textView); // <- fix
        }
     inflate_dialog(Selected_Button);

    }

    public void Edit_Tasbeh_OnClickHandler(View v) {
       // Tasbeh_info itemTo1 = (Tasbeh_info)ADA);
        Tasbeh_info itemTo1 = (Tasbeh_info) v.getTag();
       //int index=  (int) v.getTag();

        Sel_Id_e= Integer.parseInt(itemTo1.getU_id_List());
        discription_e=itemTo1.getU_nameList();
        discription_native_e=itemTo1.getU_name_native_List();
        counter_e=itemTo1.getU_counterList();
        Selected_Button="e";
        if (textView.getParent()!=null){
            ((ViewGroup)textView.getParent()).removeView(textView); // <- fix
        }
        inflate_dialog(Selected_Button);

    }

    public void inflate_dialog(String B){
        //LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater =this.getLayoutInflater();
      final  View alertLayout = inflater.inflate(R.layout.add_tasbeh_layout, null);
        final EditText et_Discription = alertLayout.findViewById(R.id.et_Discription);
        final EditText et_native_discription = alertLayout.findViewById(R.id.et_native_discriptin);
        final TextView tv_counter = alertLayout.findViewById(R.id.tv_counter);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);
        //final Button share_counter=alertLayout.findViewById(R.id.sc);

       /// AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if(Selected_Button=="a"){
            textView.setText(R.string.add_new);
          //  String s =textView.setText(getResources().getText(R.string.add_new));
            alert.setCustomTitle(textView);

            cbToggle.setVisibility(View.INVISIBLE);
//share_counter.setVisibility(View.INVISIBLE);
        }else {
            // we fill the textview to edit them
           // alert.setTitle(getString(R.string.edit));
            textView.setText(R.string.edit);
            //  String s =textView.setText(getResources().getText(R.string.add_new));
            alert.setCustomTitle(textView);
            cbToggle.setVisibility(View.VISIBLE);
       //     share_counter.setVisibility(View.VISIBLE);
            et_Discription.setText(discription_e);
            et_native_discription.setText(discription_native_e);
            tv_counter.setText(counter_e);

//            share_counter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    String shareBodyText = getString(R.string.descip)+discription_e +"\n"+getString(R.string.counter_to_share)+counter_e;
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"for the soul of :");
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//                    startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
//                }
//            });
        }
        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked=true;
                    // we change the counter to 0
                    tv_counter.setText("0");

                }
                // to encode password in dots
                // et_native_discription.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    // to display the password in normal text
//                   // et_native_discription.setTransformationMethod(null);
//                }
            }
        });





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
                Toast.makeText(getBaseContext(), "description zeker : " + discription + " description zeker : " + discription_native, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
    dialog.show();
    }

    public class Add_tesbeh extends AsyncTask<String, String, String> {


        Boolean isSuccess = false;
        String z = "";

       // String Zeker_name_ar = Et_Name.getText().toString();
        String Zeker_name_ar = discription;
        String Zeker_name_native = discription_native;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {

            if (r == "Added Successfully") {
                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
                FillList fillList=new FillList();
                fillList.execute("");
                //Et_Name.setText("");


            } else if (r=="exist"){
                Toast.makeText( Tasbeh_item.this,R.string.tazkeer_already_exist,Toast.LENGTH_LONG ).show();
            }else {

                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected String doInBackground(String... params) {

            if (Zeker_name_ar.trim().equals(""))
                z = getString(R.string.please_enter_tasbeh_name);
            else {


                //Search if that family is exist
                String SearchQuary = "Select * from Zeker_Tbl where Zeker_Name_Arabic='" + Zeker_name_ar + "'";


                Cursor cursor1 = AndPOS_db.rawQuery(SearchQuary, null);
                // if the result of the execute statment have data then the if statment is true and we move the cursor to the first row
                if (cursor1.moveToFirst()) {
                    z="exist";
                    // Toast.makeText(Payment.this, "the Payment name is already exist Please enter another one ", Toast.LENGTH_LONG).show();
                } else {
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    // the customer Id is auto increament

                    values.put("Zeker_Name_Arabic", Zeker_name_ar);
                    values.put("Zeker_Name_native", Zeker_name_native);
                    //the insert() will give -1 if the statment failed and id number if successfully inserted
                    // it took table name and the value we make a content value to put all the column names in it
                    try {
                        long rowInserted = AndPOS_db.insertOrThrow("Zeker_Tbl", null, values);
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
                        z = "The Zeker name  must be unique Please check it";
                    }


                }

            }
            return z;
        }
    }

    public class Save_Tesbeh extends AsyncTask<String,String,String>{
        Boolean isSuccess = false;
        String z = "";

      //  String Zeker_name_ar = Et_Name.getText().toString();
      String Zeker_name_ar = discription;
        String Zeker_name_native = discription_native;

        @Override
        protected void onPostExecute(String r) {
            if (r == "Updated Successfully") {
                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
                FillList fillList=new FillList();
                fillList.execute("");
              //  Et_Name.setText("");


            } else {

                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(r);
        }

        @Override
        protected String doInBackground(String... params) {
            if (Zeker_name_ar.trim().equals(""))
                z = "Please Enter Or Select zeker Name  ";
            else {



                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                // the customer Id is auto increament

                values.put("Zeker_Name_Arabic", Zeker_name_ar);
                values.put("Zeker_Name_native", Zeker_name_native);
                if (checked==true){
                    values.put("Zeker_count", 0);
                }


                //the insert() will give -1 if the statment failed and id number if successfully inserted
                // it took table name and the value we make a content value to put all the column names in it
                try {
                    //the update commend will take (1,2,3,4) 1 database name, 2 values , 3 condition ===> where statmente
                    long rowUpdate = AndPOS_db.update("Zeker_Tbl",values,"Zeker_Id="+Sel_Id_e,null);
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

        //String zeker_name_arabic = Et_Name.getText().toString();

        @Override
        protected void onPostExecute(String r) {
            if (r.equals("Deleted Successfully")) {
                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
                FillList fillList=new FillList();
                fillList.execute("");
               // Et_Name.setText("");


            } else {

                Toast.makeText(Tasbeh_item.this, r, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(r);
        }

        @Override
        protected String doInBackground(String... params) {
           // int SelectedId= Integer.parseInt( Sel_Id );
            int SelectedId= Sel_Id;
//            if (zeker_name_arabic.trim().equals(""))
//                z = "Please Enter Or Select Tasbeh Name  ";
//                // cannot delete the default zeker
//            else
            if(SelectedId==1 ||SelectedId==2||SelectedId==3 ||SelectedId==4){
                z = "These default Tasbeh Cannot Be Deleted ";
            }

            else{

//                String querys = "Select * from Invoice_Tbl where Zeker_Id=" + Sel_Id;
//                Cursor cursorfsearch = AndPOS_db.rawQuery( querys, null );
//                if (cursorfsearch.getCount() > 0) {
//                    z = "We Can not Delete this Record It have a Transaction ";
//                } else {
                    // Create a new map of values, where column names are the keys
                   // ContentValues values = new ContentValues();
                    // the customer Id is auto increament

                  //  values.put( "Zeker_Name_Arabic", zeker_name_arabic);
                   // values.put( "Zeker_Id", Sel_Id);
                    //the insert() will give -1 if the statment failed and id number if successfully inserted
                    // it took table name and the value we make a content value to put all the column names in it
                    try {
                        //the update commend will take (1,2,3) 1 database name, 2 condition ===> where statmente
                        long rowDelete = AndPOS_db.delete( "Zeker_Tbl", "Zeker_Id=" + Sel_Id, null );
                        if (rowDelete != -1) {
                            //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                            z =  getString(R.string.delete_successfuly);
                        } else {
                            //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            z = "Something wrong";
                        }
                    } catch (SQLiteConstraintException e1) {
                        //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                        // z = e1.toString();
                        z = "The Zeker name  must be unique Please check it";
                    }


                }


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
               // String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free  ";

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
                Intent intent=new Intent(Tasbeh_item.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Tasbeh_item.this, Help_Activity.class);
                startActivity(intent2);
                return true;
//            case R.id.action_reminder:
//                Intent intent3=new Intent(this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
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
