package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.sql.Time;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.PLACE_PICKER_REQUEST;
import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.arrayAdapter;
import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.events;
import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.eventsAsStrings;
import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.place;


public class eventFragment extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

      final View view = inflater.inflate(R.layout.event_fragment, container, false);



        Button location = (Button) view.findViewById(R.id.location);

        final EditText activity = (EditText) view.findViewById(R.id.EventName);
        final EditText eventLocation = (EditText) view.findViewById(R.id.eventLocation);

        location.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });







        Button add = (Button) view.findViewById(R.id.ADD);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePicker starter = (TimePicker) view.findViewById(R.id.StartTime);
                TimePicker ender = (TimePicker) view.findViewById(R.id.EndTime);

                Time startTime = new Time(starter.getHour(),starter.getMinute(),0);
                Time endTime = new Time(ender.getHour(),ender.getMinute(),0);

                Event e = new Event(activity.getText().toString(), eventLocation.getText().toString(), startTime, endTime);
                e.setEventLat(place.getLatLng().latitude);
                e.setEventLng(place.getLatLng().longitude);

                events.add(e);
                upDateStringsList(events, eventsAsStrings);
                arrayAdapter.notifyDataSetChanged();


            }

            private void upDateStringsList(ArrayList<Event> events, ArrayList<String> eventsAsStrings) {
                eventsAsStrings.clear();
                for (int i = 0; i < events.size(); i++) {
                    eventsAsStrings.add(new String(events.get(i).getName() + " at " + events.get(i).getLocation() +
                            "        Time: " + events.get(i).getEventStart().toString() + " - " + events.get(i).getEventEnd().toString()));
                }
            }

        });




        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
