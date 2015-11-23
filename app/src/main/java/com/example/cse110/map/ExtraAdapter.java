package com.example.cse110.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;


public class ExtraAdapter extends ArrayAdapter<ParseObject> {

    private ArrayList<ParseObject> myObjects;

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
            ImageView theImage = (ImageView) emptyView.findViewById(R.id.thePicture);

            theRoom.setText((String)myObject.get("RoomNumber"));
            theProblem.setText((String) myObject.get("reportDescription"));
            /*if(myObject.get("photo") != null) {
                theImage.setImageBitmap((Bitmap) myObject.get("photo"));
            }*/
        }

        return emptyView;
    }
}
