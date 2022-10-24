package com.example.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sampleapplication.student.StudentHomePage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StudentOptionsActivity extends AppCompatActivity {

    ConstraintLayout id_scanQRCode,id_showAttendence;
    TextView id_selectedCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);


        id_showAttendence = findViewById(R.id.id_showAttendence);
        id_selectedCourse = findViewById(R.id.id_selectedCourse);
        id_scanQRCode = findViewById(R.id.id_scanQRCode);

        try {
            String course=getIntent().getStringExtra("CourseType");
            id_selectedCourse.setText(course);
        }catch (Exception e){
            e.printStackTrace();
        }

        id_scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
   /*             Intent intent =new Intent(StudentOptionsActivity.this, QRScannerActivity.class);
                startActivity(intent);
   */
                IntentIntegrator intentIntegrator=new IntentIntegrator(StudentOptionsActivity.this);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });

        id_showAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(StudentOptionsActivity.this, StudentHomePage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            if(intentResult.getContents()==null){

            }else{
              makeAttendence(intentResult.getContents().toString());
            }
        }else{

        }
    }

    private void makeAttendence(String toString) {

    }
}
