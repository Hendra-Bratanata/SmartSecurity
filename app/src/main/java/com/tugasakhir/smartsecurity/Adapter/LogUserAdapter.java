package com.tugasakhir.smartsecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasakhir.smartsecurity.Pojo.PojoLogUser;
import com.tugasakhir.smartsecurity.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogUserAdapter extends RecyclerView.Adapter<LogUserAdapter.ViewHolder> {
    ArrayList<PojoLogUser> listUserLog;
    Context context;

    public LogUserAdapter(Context context){
        this.context = context;
    }

    public ArrayList<PojoLogUser> getListUserLog() {
        return listUserLog;
    }

    public void setListUserLog(ArrayList<PojoLogUser>listUserLog){
        this.listUserLog = listUserLog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loguser_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.time.setText(getListUserLog().get(i).getTime());
        viewHolder.user.setText(getListUserLog().get(i).getUser());
        viewHolder.informasi.setText(getListUserLog().get(i).getInformasi());

    }

    @Override
    public int getItemCount() {
        return getListUserLog().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtWaktu)
        TextView time;
        @BindView(R.id.txtUser)
        TextView user;
        @BindView(R.id.txtinformation)
        TextView informasi;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
