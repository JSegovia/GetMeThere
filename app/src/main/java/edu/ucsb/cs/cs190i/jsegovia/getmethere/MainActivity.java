package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.sql.Time;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public String GOOGLEAPIKEY = "AIzaSyCStlNWNT7L1QpJbTjq2nobVS25ZMIibv4";
    public static int PLACE_PICKER_REQUEST = 1;
    public static Context context;
    private PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    private ListView lv;
    public static ArrayList<Event> events = new ArrayList<>();
    private ArrayList<String> eventsAsStrings = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        //TODO: Hardcoded, remove later
        events.add(new Event("Workout", "Rec Cen", new Time(11,30,0), new Time(11,45,0)));
        events.add(new Event("Study", "Libary", new Time(12,15,0), new Time(12,45,0)));
        events.add(new Event("Yolo", "foobar", new Time(3,20,0), new Time(4,0,0)));
        upDateStringList(events, eventsAsStrings);




        lv = (ListView) findViewById(R.id.myList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventsAsStrings);




        lv.setAdapter(arrayAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                eventFragment ef = new eventFragment();
                ef.show(getFragmentManager(), "Random");

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                int PLACE_PICKER_REQUEST = 1;


                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    System.out.println("This one");
                    Log.d("This one", "THis one ");
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    System.out.println("no This one");
                    Log.d("no This one", "no THis one ");
                }


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

    public void startLocationIntent() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int PLACE_PICKER_REQUEST = 1;
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
