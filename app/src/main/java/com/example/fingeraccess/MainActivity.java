package com.example.fingeraccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private MaterialButton registerBT;
    private EditText edtNama, edtEmail, edtnim, edtID;
    private ImageView about_btn;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refUser, usesid_ref, newId_ref, enroll_ref;
    private FirebaseUser firebaseUser;

    Integer stat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();


        about_btn = findViewById(R.id.about);
        progressBar = findViewById(R.id.pbarrmain);
        edtEmail = findViewById(R.id.edt_email);
        edtNama = findViewById(R.id.edt_nama);
        edtnim = findViewById(R.id.edt_nim);
        edtID = findViewById(R.id.idjari);

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutDeveloper.class));
                finish();
            }
        });

        registerBT = findViewById(R.id.register_btn);
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nama = edtNama.getText().toString().trim();
                String Email = edtEmail.getText().toString().trim();
                String Nim = edtnim.getText().toString().trim();
                String Id = edtID.getText().toString().trim();

                if (Nama.isEmpty()) {
                    edtNama.setError("Kosong!!!");
                    edtNama.requestFocus();
                    return;
                }
                if (Email.isEmpty()) {
                    edtEmail.setError("Kosong!!!");
                    edtEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    edtEmail.setError("Email tidak Valid");
                    edtEmail.requestFocus();
                    return;
                }
                if (Nim.isEmpty()) {
                    edtnim.setError("Kosong!!!");
                    edtnim.requestFocus();
                    return;
                }
                if (Id.isEmpty()) {
                    edtID.setError("Kosong!!!");
                    edtID.requestFocus();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                stat = 0;
                Integer iID = Integer.valueOf(Id);

                auth.createUserWithEmailAndPassword(Email, Nim)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User newUser = new User(Nama, Email, Nim, Id);
                                    String userid = auth.getUid();
                                    refUser = database.getReference("User");
                                    refUser.child(userid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            enroll_ref = database.getReference("fingerprint").child("enroll");
                                            enroll_ref.setValue(1);
                                            usesid_ref = database.getReference("usesId").push();
                                            usesid_ref.setValue(iID);
                                            newId_ref = database.getReference("fingerprint").child("new_id");
                                            newId_ref.setValue(iID);
                                            startActivity(new Intent(MainActivity.this, Finger_Check.class));
                                            Toast.makeText(MainActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                                            finish();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });


//                Query query = database.getReference("User")
//                        .orderByChild("Id")
//                        .equalTo(Id);
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            User user = dataSnapshot.getValue(User.class);
//                            String sid = user.Id;
//                            if (Objects.equals(sid, Id)) {
//                                stat = 1;
//                                Toast.makeText(MainActivity.this, "id sudah ada", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//                            }
//
//                        }
//                        if (stat != 1) {
//                            auth.createUserWithEmailAndPassword(Email, Nim)
//                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if (task.isSuccessful()) {
//                                                User newUser = new User(Nama, Email, Nim, Id);
//                                                String userid = auth.getUid();
//                                                refUser = database.getReference("User");
//                                                refUser.child(userid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        enroll_ref = database.getReference("fingerprint").child("enroll");
//                                                        enroll_ref.setValue(1);
//                                                        usesid_ref = database.getReference("usesId").push();
//                                                        usesid_ref.setValue(iID);
//                                                        newId_ref = database.getReference("fingerprint").child("new_id");
//                                                        newId_ref.setValue(iID);
//                                                        startActivity(new Intent(MainActivity.this, Finger_Check.class));
//                                                        Toast.makeText(MainActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
//                                                        finish();
//                                                        progressBar.setVisibility(View.GONE);
//                                                    }
//                                                });
//                                            } else {
//                                                Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
//                                                progressBar.setVisibility(View.GONE);
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


            }
        });

    }

    int a = 0;

    @Override
    public void onBackPressed() {
        a++;
        if (a == 1) {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        } else if (a == 2) {
            finish();
        }
    }
}