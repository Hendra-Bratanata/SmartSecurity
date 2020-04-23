package com.tugasakhir.smartsecurity.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.smartsecurity.Pojo.PojoLoginUser;
import com.tugasakhir.smartsecurity.UtilityAtribute.EncryptsMD5;
import com.tugasakhir.smartsecurity.R;
import com.tugasakhir.smartsecurity.UtilityAtribute.ClassUrl;
import com.tugasakhir.smartsecurity.UtilityAtribute.GetLogUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtUser;
    EditText edtPass;
    private TextView mSignUp;
    int jobId = 10;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.user_login);
        edtPass = findViewById(R.id.password_login);
        mSignUp = findViewById(R.id.no_account);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            // text button menuju register
            public void onClick(View v) {
                final String user = edtUser.getText().toString();
                final String pass = edtPass.getText().toString();
                final String passEncrypt = EncryptsMD5.MD5(pass);
                Log.d("Password ", "Password MD5 Login " + EncryptsMD5.MD5(pass));

                if (TextUtils.isEmpty(edtUser.getText())) {
                    edtUser.setError("User harus diisi");
                    edtUser.requestFocus();
                } else if (TextUtils.isEmpty(edtPass.getText())) {
                    edtPass.setError("Password harus diisi");
                    edtPass.requestFocus();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ClassUrl.Url_Login,
                            response -> {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray array = obj.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        PojoLoginUser user1 = new PojoLoginUser(jsonObject);
                                        final String user2 = user1.getUser();
                                        final String pass1 = user1.getPass();

                                        if (user2.equals(user2) && pass1.equals(pass1)) {
                                            Toast.makeText(Login.this, "Selamat Datang " + user2 + "\n" + passEncrypt, Toast.LENGTH_LONG).show();
//                                                SharedData.getInstance(getApplicationContext()).storeUserEmail(email);
//                                                SharedData.getInstance(getApplicationContext()).storeUserPassword(password);
                                                goToMainActivity();
                                        } else if (pass1.equals(pass1)) {
                                            Toast.makeText(Login.this, "USer salah", Toast.LENGTH_LONG).show();
                                        } else if (user2.equals(user2)) {
                                            Toast.makeText(Login.this, "Password salah", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            },
                            error -> {
                                Toast.makeText(getApplicationContext(), "Connection Error" + error, Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("user", user);
                            params.put("password", passEncrypt);

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                    requestQueue.add(stringRequest);

                }
            }

            //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String MD5(String pass) {
//        String digest = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder(2 * hash.length);
//
//            for (byte b : hash) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            digest = sb.toString();
//
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return digest;
//    }

        });startJob();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startJob(){
        Log.d("LOG", "startJob: ");
        if (cekLog(this)){
            Toast.makeText(this, "LoginLogin", Toast.LENGTH_LONG).show();
        }else {
            ComponentName componentName = new ComponentName(this, GetLogUpdate.class);
            JobInfo.Builder builder = new JobInfo.Builder(jobId, componentName);
            builder.setRequiresCharging(false);
            builder.setRequiresDeviceIdle(false);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                builder.setPeriodic(15*60*1000);
            }else {
                builder.setPeriodic(3*60*1000);
            }
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean cekLog (Context context){
        Log.d("LOG", "cekLog: " );
        boolean isLoged = false;

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null){
            for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()){
                if (jobInfo.getId() == jobId){
                    isLoged = true;
                }
            }

        }
        return isLoged;
    }

    private void goToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Ingin Keluar Aplikasi?").setCancelable(false).setPositiveButton(
                "Yes", (dialogInterface, i) -> Login.super.onBackPressed())
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

