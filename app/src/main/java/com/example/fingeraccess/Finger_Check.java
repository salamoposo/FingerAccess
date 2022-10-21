package com.example.fingeraccess;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Finger_Check extends AppCompatActivity {

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private MaterialButton selesai;
    TextView pilihId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usesId_ref, user_ref, cekjari1, cekjari2, newId_ref, enroll_ref;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    NumberPicker numberPicker;

    String userid;

    Integer sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_check);

        user = auth.getCurrentUser();
        userid = user.getUid();

        selesai = findViewById(R.id.selesai);
        pilihId = findViewById(R.id.pilihid);
        checkBox1 = findViewById(R.id.cbox1);
        checkBox2 = findViewById(R.id.cbox2);
        checkBox3 = findViewById(R.id.cbox3);
        checkBox4 = findViewById(R.id.cbox4);
        checkBox1.setChecked(true);

        cekjari1 = database.getReference("fingerprint").child("feedback").child("cekJari1");
        cekjari1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer data = snapshot.getValue(Integer.class);
                if (data == 1) {
                    checkBox2.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cekjari2 = database.getReference("fingerprint").child("feedback").child("cekJari2");
        cekjari2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer data = snapshot.getValue(Integer.class);
                if (data == 1) {
                    checkBox3.setChecked(true);
                    checkBox4.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox4.isChecked() || !checkBox1.isChecked() || !checkBox2.isChecked() || !checkBox3.isChecked()) {
                    Toast.makeText(Finger_Check.this, "Selesaikan Registrasi!!!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Finger_Check.this, MainActivity.class));
                    finish();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        user_ref = database.getReference("User");
        user_ref.child(userid).removeValue();
        enroll_ref = database.getReference("fingerprint").child("enroll");
        enroll_ref.setValue(0);
        newId_ref = database.getReference("fingerprint").child("new_id");
        newId_ref.setValue(0);
        user.delete();
        auth.signOut();
        super.onBackPressed();
    }
}