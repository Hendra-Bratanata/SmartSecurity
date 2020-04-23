package com.tugasakhir.smartsecurity.Pojo;

import org.json.JSONException;
import org.json.JSONObject;

public class LogUserNotif {
    String Date;
    String Information;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public LogUserNotif(JSONObject obj) {
        try {
            String date = obj.getString("date");
            String information = obj.getString("information");


            this.Date = date;
            this.Information = information;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
