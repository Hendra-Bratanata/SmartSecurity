package com.tugasakhir.smartsecurity.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tugasakhir.smartsecurity.EncryptsMD5;
import com.tugasakhir.smartsecurity.R;
import com.tugasakhir.smartsecurity.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

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
                Log.d("onClick", "onClick: ");
                String email = emailRegist.getText().toString();
                String user = userRegist.getText().toString();
                String phone = phoneRegist.getText().toString();
                String password = passRegist.getText().toString();
                String passEncript = EncryptsMD5.MD5(password);

                Server.Daftar(passEncript, user, Register.this);
            }
        });
    }

}
