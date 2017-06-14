package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static String GOOGLEAPIKEY = "AIzaSyC_fIizx3QXVdP18uZKiucrnz5w4UCa_nw";
    public static int PLACE_PICKER_REQUEST = 1;
    public static Context context;
    private PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    private ListView lv;
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<String> eventsAsStrings = new ArrayList<>();
    public static Place place;
    public static Place currentPlace;
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
        //events.add(new Event("Workout", "Rec Cen", new Time(11,0,0), new Time(11,45,0)));
        //events.add(new Event("Study", "Libary", new Time(12,15,0), new Time(12,45,0)));
        //events.add(new Event("Dinner", "Blaze Pizza", new Time(5,20,0), new Time(6,0,0)));
        //events.get(0).setStartLat(3);
        //upDateStringList(events, eventsAsStrings);




        Button startLoc = (Button) findViewById(R.id.startLocation);
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
            }
        });




        startLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                //String time = timeBetweenPlaces(currentPlace, place);
                //System.out.println(time);

            }

        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                currentPlace = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", currentPlace.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                //String time = timeBetweenPlaces(currentPlace, place);
                //System.out.println(time);

            }
        }
    }

    private void upDateStringList(ArrayList<Event> events, ArrayList<String> eventsAsStrings) {
        eventsAsStrings.clear();
        for (int i = 0; i < events.size(); i++) {
            eventsAsStrings.add(new String(events.get(i).getName() + " at " + events.get(i).getLocation() +
                    "        Time: " + events.get(i).getEventStart().toString() + " - " + events.get(i).getEventEnd().toString()));
        }
    }



}
