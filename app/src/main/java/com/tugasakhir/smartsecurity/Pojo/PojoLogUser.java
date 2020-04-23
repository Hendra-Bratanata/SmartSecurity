package com.tugasakhir.smartsecurity.Pojo;

import org.json.JSONObject;

public class PojoLogUser {

    private String user = "user";
    private String time = "time";
    private String informasi = "informasi";



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public PojoLogUser(JSONObject object){
        try {

            String user = object.getString("user");
            String time = object.getString("pass");
            String informasi = object.getString("id");


            this.user = user;
            this.time = time;
            this.informasi = informasi;

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
