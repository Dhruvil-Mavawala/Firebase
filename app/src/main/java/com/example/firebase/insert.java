package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class insert extends AppCompatActivity {
    EditText etnameadd,etcourseadd,etemailadd,etmobadd,etpicadd;
    Button btnadd;
    LinearLayout main;
    String name,course,email,mobileno,pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert);

        main=findViewById(R.id.main);
        etnameadd=findViewById(R.id.etnameadd);
        etcourseadd=findViewById(R.id.etcourseadd);
        etemailadd=findViewById(R.id.etemailadd);
        etmobadd=findViewById(R.id.etmobadd);
        etpicadd=findViewById(R.id.etpicadd);
        btnadd=findViewById(R.id.btninsert);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etnameadd.getText().toString();
                course = etcourseadd.getText().toString();
                email= etemailadd.getText().toString();
                mobileno=etmobadd.getText().toString();
                pic=etpicadd.getText().toString();

                if (name.trim().length()==0)
                {
                    Toast.makeText(insert.this, "Enter the name", Toast.LENGTH_SHORT).show();
                } else if (course.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the name Fo course", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                } else if (mobileno.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the mobile no", Toast.LENGTH_SHORT).show();
                } else if (pic.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the picture ", Toast.LENGTH_SHORT).show();
                }else
                    insertData();

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void insertData(){
        Map<String,Object> map=new HashMap<>();
        map.put(Constantdata.KEY_NAME,etnameadd.getText().toString());
        map.put(Constantdata.KEY_COURSE,etcourseadd.getText().toString());
        map.put(Constantdata.KEY_EMAIL,etemailadd.getText().toString());
        map.put(Constantdata.KEY_MOBILE,etmobadd.getText().toString());
        map.put(Constantdata.KEY_PIC,etpicadd.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Person")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(main,"Successfully Inserted",Snackbar.LENGTH_SHORT).show();
                        Intent intent=new Intent(insert.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(insert.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}