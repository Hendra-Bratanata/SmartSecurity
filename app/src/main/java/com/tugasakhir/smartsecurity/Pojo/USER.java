package com.tugasakhir.smartsecurity.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class USER {
    String user;
    String pass;
    String id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public USER(JSONObject obj) {
        try {
            String namaUser = obj.getString("user");
            String passUser = obj.getString("pass");
            String idUser = obj.getString("id");

            this.user = namaUser;
            this.pass = passUser;
            this.id = idUser;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
