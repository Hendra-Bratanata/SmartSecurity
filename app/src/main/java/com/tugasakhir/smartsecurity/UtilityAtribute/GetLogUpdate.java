package com.tugasakhir.smartsecurity.UtilityAtribute;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.smartsecurity.Pojo.LogUserNotif;
import com.tugasakhir.smartsecurity.Notification.Notif;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GetLogUpdate extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("LOG", "onStartJob: ");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("LOG", "onStopJob: ");
        return true;
    }

    public void getLogUpdate (JobParameters job){
        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formater.format(date);

        String URL = "";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result");

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object1 = array.getJSONObject(i);
                        LogUserNotif logUser = new LogUserNotif(object1);

                        if (logUser.getDate().equals(todayDate)){
                            Notif notif = new Notif();
                            notif.sendNotif(getApplicationContext(),logUser.getInformation(), "now",logUser.getInformation(), i);
                        }


                            jobFinished(job, false);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jobFinished(job, true);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
