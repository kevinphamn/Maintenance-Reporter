package com.example.cse110.map;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by AndrewK on 11/22/2015.
 */
public class Main2Report extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("buildingName");
        final TextView textView = (TextView) findViewById(R.id.textView1);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        textView.setTextSize(20);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DataPoint");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    for (ParseObject entries : objects) {
                        if (entries.getString("Building").equals(message)) {
                            ParseFile photo = entries.getParseFile("photo");
                            if(photo != null) {
                                photo.getDataInBackground(new GetDataCallback() {
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null) {
                                            // Decode the Byte[] into bitmap
                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            // Set the Bitmap into the imageView
                                            imageView.setImageBitmap(bmp);
                                        } else {
                                            Log.d("test", "There was a problem downloading the data.");
                                        }
                                    }
                                });
                            }
                            String name = entries.getString("Building");
                            String room = entries.getString("RoomNumber");
                            String desc = entries.getString("reportDescription");
                            String toDisplay = name + " " + room + "\n\n" + "Problem Description: " + desc + "\n\n\n";
                            textView.append(toDisplay);
                        }
                    }
                } else {
                    Log.d("data", "Error: " + e.getMessage());
                }
            }
        });
    }
}
