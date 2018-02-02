//java side Initial activity 
package com.example.cash.sdp;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.Space;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    public View decorView ;
    private MapView mapView;
    int inde;
    ActionBarDrawerToggle toggle;
    private MapboxMap map;
    protected ArrayList<com.example.cash.sdp.EventLoactions>marker_loc=new ArrayList<>();
    public SearchView searchView;
    public LatLng latlg2;
    public double coord;
    public double coord2;
    public IconFactory iconFactory;
    public Icon icon;
    public ArrayList<LatLng>kl=new ArrayList<>();
    public ArrayList<String>events;
    public ArrayList<String>saved;
    public ArrayList<String>added;
    ArrayAdapter<String> adapter;
    Dialog dialog;
    DatabaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new DatabaseHelper(this);
        decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN// hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         iconFactory = IconFactory.getInstance(MainActivity.this);
        icon = iconFactory.fromResource(R.drawable.marker);
        //this is a place holder for our database
        events=new ArrayList<>();

        db.insertData("Cherry Hall","Mass Midterm Study Session","9:00pm-12pm","2017-12-31","8 participants","Description"," "," ","events");
        db.insertData("Cherry Hall","Senior Design Expo","7:00pm-12pm","2017-04-31","102 participants","Description"," "," ","events");
        db.insertData("Cherry Hall","How to make mobile apps","8:00pm-12pm","2017-04-28","13 participants","Description"," "," ","events");

        getTableData("events");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        MapboxAccountManager.start(this, getString(R.string.accesstoken1));

        // This contains the MapView in XML and needs to be called after the account manager
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapview);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.list);
        mapView.onCreate(savedInstanceState);
        try {
            // Load GeoJSON file
            InputStream inputStream = getAssets().open("features2.geojson");
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            inputStream.close();

            // Parse JSON
            JSONObject json = new JSONObject(sb.toString());
            JSONArray features = json.getJSONArray("features");
            for(int y=0; y<features.length(); y++){
            JSONObject feature = features.getJSONObject(y);
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;
                String ans=features.getJSONObject(y).toString();
                ans=ans.replace("[","");
                ans=ans.replace(" ","");
                String []locs=ans.split(",");
                Toast toast = Toast.makeText(context, features.getJSONObject(y).toString(), duration);
               // toast.show();
            JSONObject geometry = feature.getJSONObject("geometry");
            JSONObject properties= feature.getJSONObject("properties");

                String type = geometry.getString("type");

                // Our GeoJSON only has one feature: a line string


                    // Get the Coordinates
                Log.d("loadinggeo2", "Exception Loading GeoJSON1: " );
                    JSONArray coords = geometry.getJSONArray("coordinates");
                //Context= getApplicationContext();
               // CharSequence text = "Hello toast!";
                int duration2 = Toast.LENGTH_SHORT;

                Toast toastj = Toast.makeText(context, geometry.getJSONArray("coordinates").toString(), duration2);
               // toastj.show();

                         coord = geometry.getJSONArray("coordinates").getDouble(0);
                        toastj = Toast.makeText(context, coord+"", duration2);
                       // toastj.show();
                         coord2=geometry.getJSONArray("coordinates").getDouble(1);
                        toastj = Toast.makeText(context, coord2+"", duration2);
                       // toastj.show();

                        LatLng latLng = new LatLng(coord, coord2);
                  if(properties.has("Building")) {
                      marker_loc.add(new EventLoactions(coord2, coord, properties.getString("Building")));
                  }else{ marker_loc.add(new EventLoactions(coord2, coord));}
                        kl.add(new LatLng(coord2, coord));
                       // toastj= Toast.makeText(getApplicationContext(), latlg2.getLongitude()+"   "+latlg2.getLatitude()+"", Toast.LENGTH_SHORT);
                        //toastj.show();
                        latlg2 = latLng;





            }

        } catch (Exception exception) {
            Log.e("loadinggeo", "Exception Loading GeoJSON: " + exception.toString());
        }


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
               map=mapboxMap;

                mapboxMap.setOnInfoWindowClickListener(new MapboxMap.OnInfoWindowClickListener() {
                    @Override
                    public boolean onInfoWindowClick(@NonNull final Marker marker) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.list, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle(marker.getTitle());

                        final ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                        lv.setOnItemClickListener(new OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                                // When clicked, show a toast with the TextView text
                                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainActivity.this);
                                //LayoutInflater inflater = getLayoutInflater();
                                final String b=lv.getItemAtPosition(position).toString();
                                final String place=marker.getTitle();
                                alertDialog2.setMessage("Would you like to save this event?");
                                alertDialog2.setTitle("save");

                                alertDialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        String[] h=b.split("\n");
                                        Toast toastj= Toast.makeText(getApplicationContext(),h[2], Toast.LENGTH_SHORT);
                                        //toastj.show();
                                        db.insertData(place,h[0],h[1],h[2],h[3],h[4]," "," ","saved");


                                    }
                                });
                                alertDialog2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                                alertDialog2.show();
                            }
                        });

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getAllevents(marker.getTitle().toString(),events));

                        lv.setAdapter(adapter);

                        alertDialog.show();
                        return false;
                    }
                });

                map.getUiSettings().setScrollGesturesEnabled(true);
               // map.setMinZoom(14.0);
                map.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(marker.isInfoWindowShown()){

                            Toast toastj= Toast.makeText(getApplicationContext(), "rkjfbdkjfbdfkjgbdrjk", Toast.LENGTH_SHORT);
                            toastj.show();
                        }else {
                            marker.setSnippet(getLastestevent(marker.getTitle(),events));
                            marker.showInfoWindow(mapboxMap, mapView);
                        }
                       // marker.

                        return true;
                    }


                });

                // Customize map with markers, polylines, etc.
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(36.077, -77.7702344))
                        .title("Hello World!")
                        .snippet("Welcome to my marker.")
                        .icon(icon));



            }
        });

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.

                for(int u=0; u<marker_loc.size();u++){
                    inde=u;

                            // Customize map with markers, polylines, etc.


                            mapboxMap.addMarker(new MarkerOptions()

                                    .position(new LatLng(marker_loc.get(inde).latitude+.00010,marker_loc.get(inde).logitude))
                                    .title(""+marker_loc.get(inde).getBuilding())
                                    .snippet("")
                                    .icon(icon));
                     Toast toastj= Toast.makeText(getApplicationContext(), marker_loc.get(inde).latitude+"   "+marker_loc.get(inde).logitude+"", Toast.LENGTH_SHORT);
                  //  toastj.show();

                            Log.d("loadinggeo", "Exception Loading GeoJSON: " + latlg2.getLongitude() + "  " + latlg2.getLatitude());




                }

            }
        });
        //addDrawerItems();
       // mPlanetTitles = getResources().getStringArray(R.array.planets_array);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle =new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }
    public void user_foucus(String h){

      for(int i=0; i<marker_loc.size(); i++){
          if(marker_loc.get(i).getBuilding().equalsIgnoreCase(h)){
              CameraPosition position = new CameraPosition.Builder()
                      .target(new LatLng(marker_loc.get(i).getLatitude(),marker_loc.get(i).getLogitude())) // Sets the new camera position
                      .zoom(17) // Sets the zoom
                      .bearing(180) // Rotate the camera
                      .tilt(30) // Set the camera tilt
                      .build(); // Creates a CameraPosition from the builder

              map.animateCamera(CameraUpdateFactory
                      .newCameraPosition(position), 7000);


          }

                }


    }
    public void getTableData(String table_name){
        SQLiteDatabase d=db.getReadableDatabase();
         events=new ArrayList<>();
        Cursor cur = d.rawQuery("SELECT * FROM " + table_name, null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 1 ; i < cur.getColumnCount(); i++){
                    if(i!=cur.getColumnCount()-1) {
                        row_values = row_values + cur.getString(i)+",";
                    }else{row_values = row_values + cur.getString(i);}
                }

                events.add(row_values);

                Log.d("LOG_TAG_HERE", row_values);

            }while (cur.moveToNext());
        }
    }
    public void getTableData(){
        SQLiteDatabase d=db.getReadableDatabase();
        saved=new ArrayList<>();
        Cursor cur = d.rawQuery("SELECT * FROM " +"saved", null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 1 ; i < cur.getColumnCount(); i++){
                    if(i!=cur.getColumnCount()-1) {
                        row_values = row_values + cur.getString(i)+'\n';
                    }else{row_values = row_values + cur.getString(i);}
                }

                saved.add(row_values);

                Log.d("LOG_TAG_HERE", row_values);

            }while (cur.moveToNext());
        }
    }
    public void getTableData2(){
        SQLiteDatabase d=db.getReadableDatabase();
        added=new ArrayList<>();
        Cursor cur = d.rawQuery("SELECT * FROM " +"added", null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 1 ; i < cur.getColumnCount(); i++){
                    if(i!=cur.getColumnCount()-1) {
                        row_values = row_values + cur.getString(i)+'\n';
                    }else{row_values = row_values + cur.getString(i);}
                }

                added.add(row_values);

                Log.d("LOG_TAG_HERE", row_values);

            }while (cur.moveToNext());
        }
    }
    public void add_map(final double lat, final double ling,  final ArrayList<String> b){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                double lati=lat;
                double longi=ling;
                ArrayList<String>event_info=b;
                // Customize map with markers, polylines, etc.


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lati, longi))
                        .title(event_info.get(0))
                        .snippet(event_info.get(1)+'\n'+event_info.get(2)+'\n'+"Participants: "+event_info.get(3)));


            }
        });
    }

    public String getLastestevent(String Building, ArrayList<String> event) {
        String []d=new String[6];
        String current_event="";
        Boolean noevents=false;
        int totaleventcount=0;
       /* SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = sdf.parse("2009-12-31");
        Date date2 = sdf.parse("2010-01-31");*/
        for(int u=0; u<event.size(); u++){

            String f=event.get(u).split(",")[0];
            if(f.equalsIgnoreCase(Building)){
                totaleventcount++;
                d=event.get(u).split(",");// get last event added with this building name
            }


        }
        if(totaleventcount==0){
            noevents=true;

        }
        if(!noevents) {
            for (int r = 1; r < 6; r++) {
                current_event += d[r] + '\n';
            }
        }else{
            current_event="no events today";
        }
        return current_event+'\n'+totaleventcount+" total added events for:"+Building;

    }

    public ArrayList<String> getAllevents(String Building, ArrayList<String> event){
        ArrayList<String> h=new ArrayList<>();
        ArrayList<String>g=new ArrayList<>();
        String []d=new String[6];

        int count=0;
        for(int u=0; u<event.size(); u++){

            String f=event.get(u).split(",")[0];

            if(f.equalsIgnoreCase(Building)){
              g.add(event.get(u));
               // get last event added with this building name

            }


        }
        for (int r = 0; r<g.size(); r++) {
            String current_event="";
            d=g.get(r).split(",");
           for(int kg=1; kg<d.length; kg++) {
               current_event += d[kg] + '\n';
           }
            h.add(current_event);
        }

       return h;
    }


    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.MyEvent) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.list, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("My Events");
            getTableData();
            ListView a=(ListView) convertView.findViewById(R.id.listView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,saved);

            a.setAdapter(adapter);
            alertDialog.setView(convertView);
            alertDialog.show();

            // Handle the camera action
        } else if(id==R.id.MyEvents){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.list, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("My Scheduled Events");
            getTableData2();
            ListView a=(ListView) convertView.findViewById(R.id.listView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,added);

            a.setAdapter(adapter);
            alertDialog.setView(convertView);
            alertDialog.show();

        }else if (id == R.id.AddEvent) {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();

            View convertView = (View) inflater.inflate(R.layout.eventmacker, null);
            final TimePicker timePicker = (TimePicker) convertView.findViewById(R.id.timePicker2);
            final TimePicker timePicker2 = (TimePicker) convertView.findViewById(R.id.timePicker3);
            final EditText event = (EditText) convertView.findViewById(R.id.event);
            final EditText des = (EditText) convertView.findViewById(R.id.de);
            final EditText loca =(EditText) convertView.findViewById(R.id.location);
            final String[] pmam1 = {"am"};
            final String[] pmam2 = {"am"};

            final DatePicker datePicker = (DatePicker) convertView.findViewById(R.id.datePicker);

            timePicker.setIs24HourView(false);
            timePicker2.setIs24HourView(false);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                  /*  if(i<12){

                        pmam1[0] ="am";
                    }else{
                        pmam1[0] ="pm";}*/

                }
            });
            timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                    /*if(i<12){

                        pmam2[0] ="am";
                    }else{
                        pmam2[0] ="pm";}*/

                }
            });
            alertDialog.setView(convertView);
            // Set the action buttons
           alertDialog.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK, so save the mSelectedItems results somewhere
                    // or return them to the component that opened the dialog
                   // timePicker.setIs24HourView(false);
                   // timePicker2.setIs24HourView(false);
                    int hour;
                    int hour2;
                    String pmam1;
                    String pmam2;
                    if(timePicker.getHour()>12){
                         hour=timePicker.getHour()-12;
                        pmam1="pm";
                    }else{ hour=timePicker.getHour();
                    pmam1="am";
                    }

                    int minuete=timePicker.getMinute();

                    if(timePicker2.getHour()>12){
                        hour2=timePicker2.getHour()-12;
                        pmam2="pm";
                    }else{ hour2=timePicker2.getHour();
                        pmam2="am";
                    }

                    int minuete2=timePicker2.getMinute();


                    String timeofday="";


                    String time=hour+":"+minuete+pmam1+"-"+hour2+":"+minuete2+pmam2;


                    Context context = getApplicationContext();
                    CharSequence text = "Hello toast!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                   // toast.show();
                    db.insertData(loca.getText().toString(),event.getText().toString(),time,datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+"-"+datePicker.getYear(),"0 participants",des.getText().toString()," "," ","events");
                    db.insertData(loca.getText().toString(),event.getText().toString(),time,datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+"-"+datePicker.getYear(),"0 participants",des.getText().toString()," "," ","added");

                    getTableData("events");

                }
            });
                    alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });



            alertDialog.show();
        } else if (id == R.id.LocalizeView) {
            Intent h=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(h);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
       // MenuItem searchItem = menu.findItem(R.id.menu_search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

       searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                user_foucus(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                user_foucus(newText);
                return false;
            }
        });
        return true;
    }

    public void addevent(){

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Your event will be posted, Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            db.insertData("Craig Hall","Default","9:00-12pm","2016-12-31","20 participants","Description"," "," ","events");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();




    }

}