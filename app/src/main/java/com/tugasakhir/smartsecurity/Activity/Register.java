package com.tugasakhir.smartsecurity.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.smartsecurity.UtilityAtribute.ClassUrl;
import com.tugasakhir.smartsecurity.UtilityAtribute.EncryptsMD5;
import com.tugasakhir.smartsecurity.R;
import com.tugasakhir.smartsecurity.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private Button btn_Regist;
    private EditText emailRegist, userRegist, passRegist, phoneRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_Regist = findViewById(R.id.btn_register);
        emailRegist = findViewById(R.id.email_regist);
        userRegist = findViewById(R.id.user_regist);
        passRegist = findViewById(R.id.password_regist);
        phoneRegist = findViewById(R.id.phone_regist);

        btn_Regist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.d("onclik", "onClick : ");
                final String email = emailRegist.getText().toString();
                final String user = userRegist.getText().toString();
                final String pass = passRegist.getText().toString();
                final String phone = phoneRegist.getText().toString();

                final String passwordEncrypt = EncryptsMD5.MD5(pass);

                if (TextUtils.isEmpty(email)) {
                    emailRegist.setError("Masukan email");
                    emailRegist.requestFocus();
                } else if (TextUtils.isEmpty(user)) {
                    userRegist.setError("Masukan user");
                    userRegist.requestFocus();
                } else if (TextUtils.isEmpty(pass)) {
                    passRegist.setError("Masukan password");
                    passRegist.requestFocus();
                } else if (TextUtils.isEmpty(phone)) {
                    phoneRegist.setError("Masukan nomor ponsel");
                    phoneRegist.requestFocus();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassUrl.Url_Register,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (obj.getBoolean("error")) {
                                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                        } else {

                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Connection Error" + error, Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("user", user);
                            params.put("pass", passwordEncrypt);
                            params.put("phone", phone);

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
