package com.tugasakhir.smartsecurity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.SyncHttpClient;
import com.tugasakhir.smartsecurity.Adapter.LogUserAdapter;
import com.tugasakhir.smartsecurity.Pojo.PojoLogUser;
import com.tugasakhir.smartsecurity.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleViewUserLog)
    RecyclerView recyclerView;
    LogUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        adapter = new LogUserAdapter(this);

        String Url_Log = "http://khoerul.96.lt/users.php";
        DemoAsync demoAsync = new DemoAsync();
        demoAsync.execute(Url_Log);
    }
    public class DemoAsync extends AsyncTask<String, Void, ArrayList<PojoLogUser>> {
        @Override
        protected ArrayList<PojoLogUser> doInBackground(String... strings) {
            String uri = strings[0];
            final ArrayList<PojoLogUser> pojoUsers = new ArrayList<>();
            SyncHttpClient client = new SyncHttpClient();

            client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
            client.get(uri, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try{
                        String hasil = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(hasil);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length(); i++){
                            JSONObject userObj = jsonArray.getJSONObject(i);
                            PojoLogUser pojoUser = new PojoLogUser(userObj);

                            pojoUsers.add(pojoUser);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Tag","onFailure: " +statusCode);
                }
            });

            return pojoUsers;
        }
        @Override
        protected  void  onPostExecute (ArrayList<PojoLogUser> user){
            super.onPostExecute(user);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter.setListUserLog(user);
            recyclerView.setAdapter(adapter);
        }
    }
    public void onBackPressed () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingin Keluar Aplikasi?").setCancelable(false).setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
