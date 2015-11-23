package com.example.cse110.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity3Activity extends AppCompatActivity {

    private ArrayList<ParseObject> lists = new ArrayList<>();
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);

        Intent intent = getIntent();
        String buildingName = intent.getStringExtra("buildingName");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DataPoint");
        query.whereEqualTo("Building",buildingName);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                lists.addAll(objects);
                myList = (ListView) findViewById(R.id.lister);
                ExtraAdapter used = new ExtraAdapter(MainActivity3Activity.this,R.layout.lister_layout,lists );
                myList.setAdapter(used);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
