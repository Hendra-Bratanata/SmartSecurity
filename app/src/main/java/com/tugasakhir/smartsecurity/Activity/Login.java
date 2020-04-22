package com.tugasakhir.smartsecurity.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tugasakhir.smartsecurity.R;
import com.tugasakhir.smartsecurity.Server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtUser;
    EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.user_login);
        edtPass = findViewById(R.id.password_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtUser.getText())){
                    edtUser.setError("Wajib Isi User");
                }
                if (TextUtils.isEmpty(edtPass.getText())) {
                    edtPass.setError("Wajib Isi Password");
                }
                else {
                    String user = edtUser.getText().toString();
                    String pass = edtPass.getText().toString();
                    String passMD5 = MD5(pass);
                    Log.d("Pass", "Password MDS Login"+MD5(pass));
                    Server.Login(user,passMD5,Login.this);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String MD5(String pass) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}
