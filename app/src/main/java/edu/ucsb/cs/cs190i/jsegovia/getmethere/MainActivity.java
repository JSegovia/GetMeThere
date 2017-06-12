package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public String GOOGLEAPIKEY = "AIzaSyCStlNWNT7L1QpJbTjq2nobVS25ZMIibv4";
    public static int PLACE_PICKER_REQUEST = 1;
    public static Context context;
    private PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    private ListView lv;
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<String> eventsAsStrings = new ArrayList<>();
    public static Place place;
    public static ArrayAdapter<String> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Get Me There ON TIME");
        context = getApplicationContext();

        //TODO: Hardcoded, remove later
        //events.add(new Event("Workout", "Rec Cen", new Time(11,30,0), new Time(11,45,0)));
        //events.add(new Event("Study", "Libary", new Time(12,15,0), new Time(12,45,0)));
        //events.add(new Event("Yolo", "foobar", new Time(3,20,0), new Time(4,0,0)));
        upDateStringList(events, eventsAsStrings);




        lv = (ListView) findViewById(R.id.myList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventsAsStrings);




        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Event temp = events.get(position);
                eventFragment ef = new eventFragment();

                ef.show(getFragmentManager(), Integer.toString(position));



                //((TextView) ef.getView().findViewById(R.id.location)).setText("test");

            }

        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventFragment ef = new eventFragment();
                ef.show(getFragmentManager(), "Fab");

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



            }
        });
    }

    private void upDateStringList(ArrayList<Event> events, ArrayList<String> eventsAsStrings) {
        eventsAsStrings.clear();
        for (int i = 0; i < events.size(); i++) {
            eventsAsStrings.add(new String(events.get(i).getName() + " at " + events.get(i).getLocation() +
                    "        Time: " + events.get(i).getEventStart().toString() + " - " + events.get(i).getEventEnd().toString()));
        }
    }



}
