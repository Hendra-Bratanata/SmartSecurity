package com.tugasakhir.smartsecurity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.smartsecurity.Pojo.USER;
import com.tugasakhir.smartsecurity.UtilityAtribute.EncryptsMD5;
import com.tugasakhir.smartsecurity.R;
import com.tugasakhir.smartsecurity.UtilityAtribute.ClassUrl;
import com.tugasakhir.smartsecurity.UtilityAtribute.GetLogUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtUser;
    EditText edtPass;
    private TextView mSignUp;
    int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("TAG", "onCreate: ");

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.user_login);
        edtPass = findViewById(R.id.password_login);
        mSignUp = findViewById(R.id.no_account);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");
                String UserEnter = edtUser.getText().toString();
                String PassEnter = edtPass.getText().toString();
                String PassMd5 = EncryptsMD5.MD5(PassEnter);

                if (TextUtils.isEmpty(edtUser.getText())){
                    edtPass.setError("Wajib di isi");
                }
                if(TextUtils.isEmpty(edtPass.getText())){
                    edtPass.setError("Wajib Di isi");
                }
                else {
                    String URL = ClassUrl.Url_Login;
                    StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object =  new JSONObject(response);
                                JSONArray arr = object.getJSONArray("data");

                                for(int i  = 0; i < arr.length(); i++){
                                    JSONObject obj2 = arr.getJSONObject(i);
                                    USER user = new USER(obj2);

                                    if(user.getUser().equals(UserEnter) || user.getPass().equals(PassMd5)){
                                        goToMainActivity();
                                    }
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(stringRequest);
                }
            }
        });




    }
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

