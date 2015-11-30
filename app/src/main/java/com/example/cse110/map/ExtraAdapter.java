package com.example.cse110.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class ExtraAdapter extends ArrayAdapter<ParseObject> {

    private ArrayList<ParseObject> myObjects;
    private ArrayList<Boolean> canPress = new ArrayList<>();
    private ParseFile photo = null;
    Bitmap bmp = null;
    private Bitmap[] myList;
    private Bitmap holder;
    //private boolean canPress1 =true;

    public ExtraAdapter(Context context, int using, ArrayList<ParseObject> objects) {
        super(context, using, objects);
        this.myObjects = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View emptyView = convertView;

        if(emptyView == null) {
            LayoutInflater theInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            emptyView = theInflater.inflate(R.layout.lister_layout, null);
        }

        final ParseObject myObject = myObjects.get(position);

        if (myObject != null) {
            bmp = null;
            TextView theRoom = (TextView) emptyView.findViewById(R.id.theRoomNumber);
            TextView theProblem = (TextView) emptyView.findViewById(R.id.theProblem);
            final ImageView theImage = (ImageView) emptyView.findViewById(R.id.thePicture);

            final Button confirm = (Button)emptyView.findViewById(R.id.confirmButton);
            final Button deny = (Button)emptyView.findViewById(R.id.denyButton);
            final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DataPoint");

            canPress.add(true);

            confirm.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    if(canPress.get(position)) {
                        query.getInBackground(myObject.getObjectId(),new GetCallback<ParseObject>(){
                            public void done(ParseObject obo, ParseException e) {
                                if(e==null) {
                                    obo.put("count", 0);
                                    obo.saveInBackground();
                                }

                            }
                        });
                        confirm.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        deny.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        canPress.set(position,false);
                    }
                }
            });

            deny.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    if(canPress.get(position)){
                        query.getInBackground(myObject.getObjectId(), new GetCallback<ParseObject>() {
                            public void done(ParseObject obo, ParseException e) {
                                if(e==null) {

                                    obo.put("count", (int) myObject.get("count") - 1);
                                    obo.saveInBackground();
                                }

                            }
                        });
                        confirm.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        deny.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        canPress.set(position,false);
                    }
                }

            });

            theRoom.setText("Room Number: ");
            theRoom.append((String)myObject.get("RoomNumber"));
            theProblem.setText("Problem Description: ");
            theProblem.append((String) myObject.get("reportDescription"));
            photo = myObject.getParseFile("photo");
            theImage.setImageDrawable(null);
            theImage.setVisibility(View.GONE);
            if(photo != null) {
                photo.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            // Decode the Byte[] into bitmap
                            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // Set the Bitmap into the imageView
                            //if(bmp != null) {
                            theImage.setImageBitmap(bmp);
                            //}
                            //myList[position] = bmp;
                        } else {
                            Log.d("test", "There was a problem downloading the data.");
                        }
                    }

                });
            }
            else{
                // Set image to the pic_unavailable drawable resource
                theImage.setImageResource(R.drawable.pic_unavailable);
            }
            theImage.setVisibility(View.VISIBLE);

        }

        return emptyView;
    }
}
