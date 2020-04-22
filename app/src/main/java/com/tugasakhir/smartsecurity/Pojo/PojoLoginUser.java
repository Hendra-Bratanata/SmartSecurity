package com.tugasakhir.smartsecurity.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PojoLoginUser implements Parcelable {
    public String user;
    public String pass;
    public String id;

    protected PojoLoginUser(Parcel in) {
        user = in.readString();
        pass = in.readString();
        id = in.readString();
    }

    public static final Creator<PojoLoginUser> CREATOR = new Creator<PojoLoginUser>() {
        @Override
        public PojoLoginUser createFromParcel(Parcel in) {
            return new PojoLoginUser(in);
        }

        @Override
        public PojoLoginUser[] newArray(int size) {
            return new PojoLoginUser[size];
        }
    };

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

    public PojoLoginUser(JSONObject obj) {
        try {
            String namaUser = obj.getString("user");
            String passUser = obj.getString("pass");
            String idUser = obj.getString("id");
            user = namaUser;
            pass = passUser;
            id = idUser;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user);
        parcel.writeString(pass);
        parcel.writeString(id);
    }
}
