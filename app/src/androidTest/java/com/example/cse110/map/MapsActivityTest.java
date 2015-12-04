package com.example.cse110.map;

import com.robotium.solo.Solo;
import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

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
        //when I click "Report a Problem" button then I am directed to the report page
        solo.clickOnButton(R.id.toReport);
        solo.assertCurrentActivity("Current Activity", MainActivity2Activity.class);
        /*when I fill in all the fields with valid input and click report then
        current page closes to main activity*/
        solo.enterText(R.id.buildingName, "Center");
        solo.enterText(R.id.roomNumber, "101");
        solo.enterText(R.id.problem, "projector doesn't work");
        solo.clickOnButton("report");
        solo.assertCurrentActivity("Current Activity", MapsActivity.class);
        /* when i am back on main activity page then map is refreshed with my report added to existing
        pin or new pin if necessary */


    }
}
