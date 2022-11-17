package com.example.fingeraccess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<RiwayatAkses> list;

    public MyAdapter(Context context, ArrayList<RiwayatAkses> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userhistory,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        RiwayatAkses riwayatAkses = list.get(position);
         holder.name.setText(riwayatAkses.Nama);
//         holder.id.setText(riwayatAkses.Id.toString());
         holder.time.setText(riwayatAkses.Waktu);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, id, time;
         public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namahistory);
            id = itemView.findViewById(R.id.idhistory);
            time = itemView.findViewById(R.id.waktuhistory);
        }
    }
}
