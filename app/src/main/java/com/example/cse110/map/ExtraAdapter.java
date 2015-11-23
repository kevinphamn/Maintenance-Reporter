package com.example.cse110.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;


public class ExtraAdapter extends ArrayAdapter<ParseObject> {

    private ArrayList<ParseObject> myObjects;
    private ParseFile photo;

    public ExtraAdapter(Context context, int using, ArrayList<ParseObject> objects) {
        super(context, using, objects);
        this.myObjects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View emptyView = convertView;

        if(emptyView == null) {
            LayoutInflater theInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            emptyView = theInflater.inflate(R.layout.lister_layout, null);
        }

        ParseObject myObject = myObjects.get(position);

        if (myObject != null) {

            TextView theRoom = (TextView) emptyView.findViewById(R.id.theRoomNumber);
            TextView theProblem = (TextView) emptyView.findViewById(R.id.theProblem);
            final ImageView theImage = (ImageView) emptyView.findViewById(R.id.thePicture);

            theRoom.setText((String)myObject.get("RoomNumber"));
            theProblem.setText((String) myObject.get("reportDescription"));
            photo = myObject.getParseFile("photo");
            theImage.setImageDrawable(null);
            if(photo != null) {
                photo.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null && data != null) {
                            // Decode the Byte[] into bitmap
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // Set the Bitmap into the imageView
                            theImage.setImageBitmap(bmp);
                        } else {
                            Log.d("test", "There was a problem downloading the data.");
                        }
                    }
                });
            }
        }

        return emptyView;
    }
}
