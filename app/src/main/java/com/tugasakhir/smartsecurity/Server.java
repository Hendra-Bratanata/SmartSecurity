package com.tugasakhir.smartsecurity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.smartsecurity.Activity.MainActivity;
import com.tugasakhir.smartsecurity.Pojo.PojoLoginUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Server {

    public static String Url = BuildConfig.BASE_URL;
    public static RequestQueue queue;
    public static StringRequest stringRequest;

//    public static void Login (final String user, final String pass, final Context context){
//        queue = Volley.newRequestQueue(context);
//        stringRequest = new StringRequest(Request.Method.GET, Url + "User.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject object = new JSONObject(response);
//                    JSONArray array = object.getJSONArray("data");
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject obj2 = array.getJSONObject(i);
//                        PojoLoginUser pojoLoginUser = new PojoLoginUser(obj2);
//
//                        if (pojoLoginUser.user.equals(user) && pojoLoginUser.pass.equals(pass)){
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(stringRequest);
//    }

    public static void Daftar(String passEncript, String s, String pass, String user, Context context){
        String UrlDaftar = Url+"Login.php?user="+user+"&pass="+pass;
        stringRequest = new StringRequest(Request.Method.GET, UrlDaftar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String kode = obj.getString("kode");
                    String pesan = obj.getString("pesan");
                    Log.d("TAG", "onResponse: " + pesan);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
