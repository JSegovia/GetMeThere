package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<String> eventsAsStrings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
