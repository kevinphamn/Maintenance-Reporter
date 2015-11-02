package com.example.cse110.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity2Activity extends ActionBarActivity {

    String[] buildings = {"102 Marshall College", "Applied Physics and Mathematics","Center Hall",
            "Cognitive Science Building","Copley International Conference Center","Galbraith Hall",
            "Humanities and Social SciencesBuilding","Ledden Auditorium","Mandeville Center",
            "McGill Hall","Pepper Canyon Hall","Peterson Hall","Price Center West","Robinson Building 2",
            "Sequoah Hall","Social Sciences Building","Solis Hall","Warren Lecture Hall","York Hall"};
    String[] marshallRooms ={"1"};
    String[] apmRooms ={"2301"};
    String[] centerRooms ={"101","105","109","113","115","119","201","202","203","204","205","206","207",
    "208","212","214","216","217A","217B","218","220","221","223","224A","224B","224C"};
    String[] csbRooms ={"1","2","4","5"};
    String[] compleyRooms ={"101"};
    String[] galbraithRooms ={"242"};
    String[] hssRooms ={"1106A","1106B","1128A","1128B","1138","1305","1315","1330","210","2152","2154",
    "2250","2305A","2305B","2321","2333A","2333B"};
    String[] leddenRooms ={"2250"};
    String[] mandevilleRooms ={"135","B104","B146","B150","B152","B153"};
    String[] mcgillRooms ={"2315","2322","2330","2334","2342"};
    String[] pcynhRooms ={"106","109","120","121","122"};
    String[] petersonRooms ={"102","103","104","108","110"};
    String[] pcWestRooms ={"402"};
    String[] robinsonRooms ={"2101"};
    String[] sequoahRooms ={"147","148"};
    String[] socialSciencesRooms ={"106"};
    String[] solisRooms ={"104","107","109","110","111"};
    String[] wlhRooms ={"2001","2005","2110","2111","2112","2113","2114","2115","2204","2205","2206",
    "2207","2208","2209"};
    String[] yorkRooms ={"2622","2722","3000A","3050A","3050B","4050A","4050B","4060A","4060A","4080A"};
    String[][] roomNumbers={marshallRooms,apmRooms, centerRooms, csbRooms, compleyRooms, galbraithRooms,
    hssRooms, leddenRooms, mandevilleRooms, mcgillRooms, pcynhRooms, petersonRooms, pcWestRooms, robinsonRooms,
    sequoahRooms, socialSciencesRooms, solisRooms, wlhRooms, yorkRooms};

    ImageView image;
    boolean canBePressed=false;
    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        final AutoCompleteTextView room = (AutoCompleteTextView) findViewById(R.id.roomNumber);
        final EditText problem = (EditText) findViewById(R.id.problem);
        final AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.buildingName);
        final Button report = (Button) findViewById(R.id.report);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildings);
        auto.setThreshold(1);
        auto.setAdapter(adapter);

        image = (ImageView) findViewById(R.id.picture);
        Button pictureButton = (Button) findViewById(R.id.takePhoto);

            report.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (canBePressed) {
                    }
                }
            });
            pictureButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent();
                    myIntent.setType("image/*");
                    myIntent.setAction(Intent.ACTION_GET_CONTENT);

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    String choose="Chose from gallery or take new";
                    Intent theIntent = Intent.createChooser(myIntent,choose);
                    theIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                    startActivityForResult(theIntent, 1);
                }
            });

        room.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                checker(problem, room, auto, report);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        problem.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                checker(problem, room, auto, report);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                checker(problem, room, auto, report);
                for(int i=0; i<buildings.length; i++){
                    if(buildings[i].equals(auto.getText().toString())){
                        ArrayAdapter<String> user = new ArrayAdapter<String>(MainActivity2Activity.this, android.R.layout.simple_list_item_1, roomNumbers[i]);
                        room.setThreshold(1);
                        room.setAdapter(user);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void checker(EditText a, AutoCompleteTextView b, AutoCompleteTextView c, Button d){
        if (!a.getText().toString().equals("") && !b.getText().toString().equals("") &&
                !c.getText().toString().equals("")) {
            d.setBackgroundColor(Color.parseColor("#0000FF"));
            canBePressed=true;
        } else {
            d.setBackgroundColor(Color.parseColor("#FFFFFF"));
            canBePressed=false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == 1) {
                final boolean camera;
                Log.d("poiuy","wgerg");
                if(data==null || data.getData()==null){
                    camera=true;
                    Log.d("oiuyt","gg");
                }
                else{
                    final String use=data.getAction();
                    if(use==null){
                        camera=false;
                    }
                    else{
                        camera=MediaStore.ACTION_IMAGE_CAPTURE.equals(data.getAction());
                    }
                }

                Log.d("oiuytre","gjij");
                if(camera) {
                    Log.d("hepero","ewf");
                    if ( data != null) {
                    Log.d("gingero", "ef");
                    //Bundle extras=data.getData();
                    photo = (Bitmap)data.getExtras().get("data");
                    Log.d("ertyr", "ef");
                    }

                }
                else{
                    Uri stuff = data.getData();
                    try {
                        InputStream user = getContentResolver().openInputStream(stuff);
                        photo =  BitmapFactory.decodeStream(user);
                    } catch(FileNotFoundException e) {}
                }
                Log.d("etet","h");
                image.setImageBitmap(photo);
                image.getLayoutParams().width = 250;
                image.getLayoutParams().height = 250;
                image.setAdjustViewBounds(true);
            }
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
