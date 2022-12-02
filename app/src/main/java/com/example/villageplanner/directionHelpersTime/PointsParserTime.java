package com.example.villageplanner.directionHelpersTime;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vishal on 10/20/2018.
 */

public class PointsParserTime extends AsyncTask<String, Integer, String> {
    TaskLoadedCallbackTime taskCallback;
    String directionMode = "driving";

    public PointsParserTime(Context mContext, String directionMode) {
        this.taskCallback = (TaskLoadedCallbackTime) mContext;
        this.directionMode = directionMode;
    }

    // Parsing the data in non-ui thread
    @Override
    protected String doInBackground(String... jsonData) {

        JSONObject jObject;
        String routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            Log.d("mylog", jsonData[0].toString());
            DataParserTime parser = new DataParserTime();
            Log.d("mylog", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("mylog", "Executing routes");
            Log.d("mylog", routes.toString());

        } catch (Exception e) {
            Log.d("mylog", e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            taskCallback.onTaskDoneTime(result);//edit here

        } else {
            Log.d("mylog", "without Polylines drawn");
        }
    }
}