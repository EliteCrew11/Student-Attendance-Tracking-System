package com.example.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InstructorOptionsActivity extends AppCompatActivity {


    ConstraintLayout id_scanQRCode,id_showAttendence;
    TextView id_selectedCourse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_options);

        id_showAttendence = findViewById(R.id.id_showAttendence);
        id_scanQRCode = findViewById(R.id.id_scanQRCode);
        id_selectedCourse = findViewById(R.id.id_selectedCourse);

        try {
            String course=getIntent().getStringExtra("CourseType");
            id_selectedCourse.setText(course);
        }catch (Exception e){
            e.printStackTrace();
        }


        id_scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InstructorOptionsActivity.this,QRGeneratorActivity.class);
                intent.putExtra("QRCODE",id_selectedCourse.getText().toString());
                startActivity(intent);

            }
        });

        id_showAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InstructorOptionsActivity.this,AllStudentsAttendence.class);
                startActivity(intent);
            }
        });
    }

}
