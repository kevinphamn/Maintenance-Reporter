package com.example.cse110.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseObject;

public class MainActivity2Activity extends ActionBarActivity {

    String[] buildings = {"102 Marshall College", "Applied Physics and Mathematics","Center Hall",
            "Cognitive Science Building","Copley International Conference Center","Galbraith Hall",
            "Humanities and Social SciencesBuilding","Ledden Auditorium","Mandeville Center",
            "McGill Hall","Pepper Canyon Hall","Peterson Hall","Price Center West","Robinson Building 2",
            "Sequoah Hall","Social Sciences Building","Solis Hall","Warren Lecture Hall","York Hall"};
    ImageView image;
    boolean canBePressed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.buildingName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildings);
        auto.setThreshold(1);
        auto.setAdapter(adapter);

        image = (ImageView) findViewById(R.id.picture);
        Button pictureButton = (Button) findViewById(R.id.takePhoto);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });

        Button reportButton = (Button) findViewById(R.id.report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText descriptionText = (EditText) findViewById(R.id.problem);
                EditText buildingText = (EditText) findViewById(R.id.buildingName);
                EditText numberText = (EditText) findViewById(R.id.roomNumber);
                String problemString = descriptionText.getText().toString();
                String buildingString = buildingText.getText().toString();
                String numberString = numberText.getText().toString();

                ParseObject newReport = new ParseObject("DataPoint");
                newReport.put("reportDescription", problemString);
                newReport.put("Building", buildingString);
                newReport.put("RoomNumber", numberString);
                newReport.saveInBackground();

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    public void buttonClick(View v){
        EditText here = (EditText) findViewById(R.id.roomNumber);
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
