package com.example.cse110.map;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GpsStatus;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExtraAdapter extends ArrayAdapter<ParseObject> {

    private ArrayList<ParseObject> myObjects;
    private ArrayList<Boolean> canPress = new ArrayList<>();
    private ParseFile photo;
    Bitmap bmp = null;

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
            if(myObject.getInt("count") > -3) {
                TextView theRoom = (TextView) emptyView.findViewById(R.id.theRoomNumber);
                TextView theProblem = (TextView) emptyView.findViewById(R.id.theProblem);
                final ImageView theImage = (ImageView) emptyView.findViewById(R.id.thePicture);
                Button theInvalidButton = (Button) emptyView.findViewById(R.id.theInvalidButton);
                canPress.add(true);

                theRoom.setText("Room Number: ");
                theRoom.append((String) myObject.get("RoomNumber"));
                theProblem.setText("Problem Description: ");
                theProblem.append((String) myObject.get("reportDescription"));
                photo = myObject.getParseFile("photo");
                theImage.setImageDrawable(null);
                if (photo != null) {
                    photo.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                // Decode the Byte[] into bitmap
                                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                // Set the Bitmap into the imageView
                                if (bmp != null) {
                                    theImage.setImageBitmap(bmp);
                                    bmp = null;
                                }
                            } else {
                                Log.d("test", "There was a problem downloading the data.");
                            }
                        }

                    });
                } else {
                    // Set image to the pic_unavailable drawable resource
                    theImage.setImageResource(R.drawable.pic_unavailable);
                }

                theInvalidButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (canPress.get(position)) {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("DataPoint");
                            ParseObject object;

                            ConnectivityManager cm = (ConnectivityManager) getContext().
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo info = cm.getActiveNetworkInfo();
                            if (info == null || !info.isConnectedOrConnecting()) {
                                createAlert("Unable to Submit", "Unable to submit input. Try again later.");
                                return;
                            }

                            try {
                                object = query.get(myObject.getObjectId());
                            } catch (ParseException e) {
                                createAlert("Unable to Submit", "Unable to submit input. Try again later.");
                                return;
                            }

                            object.put("count", (int) myObject.get("count") - 1);
                            if(object.getInt("count") <= -3) {
                                try {
                                    object.delete();
                                } catch (ParseException el) {}
                            }
                            object.saveEventually();
                            createAlert("Submission Success", "Input Successful");
                            canPress.set(position, false);
                        }
                        else{
                            createAlert("Feedback Submitted", "Your feedback has already been submitted");
                        }
                    }
                });
            }
        }

        return emptyView;
    }

    private void createAlert(String title, String message){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
