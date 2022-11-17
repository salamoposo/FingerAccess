package com.example.fingeraccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView recyclerView;
    ArrayList<RiwayatAkses> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUser;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycleview);

        back_btn = findViewById(R.id.backBtnHistory);
        dbUser = database.getReference("riwayatAkses");
        list = new ArrayList<RiwayatAkses>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                finish();
            }
        });

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RiwayatAkses riwayatAkses;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    riwayatAkses = dataSnapshot.getValue(RiwayatAkses.class);
                    if (riwayatAkses != null){
                        list.add(riwayatAkses);
                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HistoryActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}