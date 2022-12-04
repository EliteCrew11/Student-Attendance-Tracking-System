package com.example.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleapplication.admin.AdminLogin;

public class MainActivity extends AppCompatActivity {

    LinearLayout studentLayout,professorLayout;
    TextView adminbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentLayout=findViewById(R.id.id_student_layout);
        professorLayout=findViewById(R.id.id_instructor_layout);
        adminbtn=findViewById(R.id.adminbtn);

        studentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StudentLogin.class);
                startActivity(intent);
            }
        });

        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AdminLogin.class);
                startActivity(intent);
            }
        });

        professorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfessorLogin.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onBackPressed() {

    }
}
