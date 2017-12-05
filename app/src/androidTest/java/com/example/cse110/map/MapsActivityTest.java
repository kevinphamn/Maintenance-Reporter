package com.example.cse110.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.robotium.solo.Solo;
import junit.framework.Assert;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import java.util.List;

/**
 * Created by AndrewK on 12/4/2015.
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2<MapsActivity> {
    private Solo solo;

    public MapsActivityTest(){
        super(MapsActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    /**Test Scenario:
     Given I just opened up the app on my phone
     when I’m on the main activity page with the map
     then I should see pins in the locations of all buildings that have valid reports on them
     and when I click add report button
     then I am directed to the report page
     and when I fill in all the fields with valid input and click report
     then current page closes to main activity
     and when I am back on main activity page
     then map is refreshed with my report added to existing pin or new pin if necessary
     and when I click on a pin
     then I should see an information label that shows all rooms for that building that have valid             reports on them
     and when I click on the information label
     then a new activity should come up with all the detailed reports for those rooms**/

    public void testMapsActivityFunctions() throws Exception {
        /* Given I just opened up the app on my phone when I’m on the main activity page with the map
        then I should see pins in the locations of all buildings that have valid reports on them*/
        solo.assertCurrentActivity("Current Activity", MapsActivity.class);
        //final GoogleMap mMap = ((SupportMapFragment)((FragmentActivity)solo.getCurrentActivity()).getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        //when I click "Report a Problem" button then I am directed to the report page
        solo.clickOnButton("New Report");
        solo.assertCurrentActivity("Current Activity", MainActivity2Activity.class);
        /*when I fill in all the fields with valid input and click report then
        current page closes to main activity*/
        solo.enterText((EditText)solo.getView(R.id.buildingName), "Center Hall");
        solo.enterText((EditText)solo.getView(R.id.roomNumber), "101");
        solo.enterText((EditText)solo.getView(R.id.problem), "projector doesn't work");
        solo.clickOnButton("Report");
        solo.waitForActivity(MapsActivity.class, 2000);
        solo.assertCurrentActivity("Current Activity", MainActivity2Activity.class);
        /* when i am back on main activity page then map is refreshed with my report added to existing
        pin or new pin if necessary */

        solo.clickOnButton("Center Hall");
       /* and when I click on the pin where the building I reported is
        then I should see an information label that shows all rooms for that building that have valid
        reports on them
        and when I click on the information label
        then a new activity should come up with all the detailed reports for those rooms*/


    }
}
