package com.example.sampleapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QRGeneratorActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private int CAMERA_PERMISSION_CODE = 2;
    ProgressDialog progressDialog;
    ArrayList<String> absentStudentList = new ArrayList<>();
    ArrayList<StudentRegistrationModel> studentUserIDList = new ArrayList<>();
    long millisInFuture = 300000;
    Button incrementTimer;
    CountDownTimer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        incrementTimer=findViewById(R.id.incrementTimer);
        requestPermissios(QRGeneratorActivity.this);

        incrementTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(millisInFuture <= 300000) {
                    millisInFuture += 30000;
                    showTimer(millisInFuture);
                }
            }
        });
        try {
            SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
            String subject = sharedpreferences.getString("CourseType", "");
            String teacherUniqueID = sharedpreferences.getString("TEACHERID", "");

            // today date
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

         //   String formattedDate="14-Nov-2022";

            try {
                if (CommonUtils.isConnectedToInternet(QRGeneratorActivity.this)) {

                   getstudentList( studentUserIDList,teacherUniqueID,subject,formattedDate);

                } else {
                    Toast.makeText(QRGeneratorActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }

            String data = teacherUniqueID + "," + subject + "," + formattedDate+","+"present";

            generateQRCode(data, "PRESENT");
            showTimer(millisInFuture);


        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

    }

    private void setAllStudentsAbsent(ArrayList<StudentRegistrationModel> allStudentsList, String teacherUniqueID, String subject, String formattedDate) {
        progressDialog = new ProgressDialog(QRGeneratorActivity.this);
        progressDialog.setMessage("Generating QR....");
        progressDialog.show();

        // setting all students has absent

        FirebaseDatabase studentdatabase = FirebaseDatabase.getInstance();
        DatabaseReference setAttendenceReference = studentdatabase.getReference("MarkAttendence").child(teacherUniqueID)
                .child(subject).child(formattedDate);



        setAttendenceReference.child("StudentCount").child("present").setValue("0");
        setAttendenceReference.child("StudentCount").child("absent").setValue(String.valueOf(allStudentsList.size()));
        setAttendenceReference.child("StudentCount").child("late").setValue("0");
        setAttendenceReference.child("present").setValue("0");



        try {
            for (int i=0;i<allStudentsList.size();i++){
                setAttendenceReference.child("absent").child(allStudentsList.get(i).getUniqueKey()).setValue(allStudentsList.get(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        setAttendenceReference.child("late").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.cancel();
            }
        });


    }

    private void getstudentList(ArrayList<StudentRegistrationModel> studentUserIDList, String teacherUniqueID, String subject, String formattedDate) {

        // Get all student details


        progressDialog = new ProgressDialog(QRGeneratorActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        FirebaseDatabase studentdatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRefstudent = studentdatabase.getReference("UserDetails").child("Student");
        myRefstudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                studentUserIDList.clear();
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    StudentRegistrationModel model=postsnapshot.getValue(StudentRegistrationModel.class);
                //    String studentKey = postsnapshot.getKey();
                  //  String studentName=postsnapshot.child("fname").getValue(String.class) + postsnapshot.child("lname").getValue(String.class);
                    studentUserIDList.add(model);
                }
                setAllStudentsAbsent(studentUserIDList,teacherUniqueID,subject,formattedDate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
                progressDialog.cancel();

            }
        });


    }

    public class myTimer extends CountDownTimer  {

        private long millisActual;

        public myTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            millisActual = millisInFuture - 3000;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //v start showing the tick after 3 seconds.
            if (millisUntilFinished <= millisActual) {
                ((TextView) findViewById(R.id.timerr)).setText("" + millisUntilFinished / 1000);
            }
        }

        @Override
        public void onFinish() {
            ((TextView) findViewById(R.id.timerr)).setText("Countdown Finished");
        }
    }
    private void showTimer(Long formattedDate) {
        try {


          //  CountDownTimer wavetimer = new myTimer(millisInFuture + 30000, 1000).start();

            if(timer!=null){
                timer.cancel();
            }

         timer = new CountDownTimer(formattedDate, 1000) {
                public void onTick(long millisUntilFinished) {
                    millisInFuture=millisUntilFinished;

                    NumberFormat f = new DecimalFormat("00");

                    long hour = (millisUntilFinished / 3600000) % 24;

                    long min = (millisUntilFinished / 60000) % 60;

                    long sec = (millisUntilFinished / 1000) % 60;

                    if(millisUntilFinished<=120000){
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                        String subject = sharedpreferences.getString("CourseType", "");
                        String teacherUniqueID = sharedpreferences.getString("TEACHERID", "");

                        // today date
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);
                        String data = teacherUniqueID + "," + subject + "," + formattedDate+","+"late";

                        generateQRCode(data,"LATE");
                    }

                          ((TextView) findViewById(R.id.timerr)).setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    //       ((TextView) findViewById(R.id.timerr)).setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    if (CommonUtils.isConnectedToInternet(QRGeneratorActivity.this)) {
                        try {
                            setAbsentforStudents();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        //   getAbsentList(formattedDate);

                    } else {
                        Toast.makeText(QRGeneratorActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                    }

                    //    mTextField.setText("done!");
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAbsentforStudents() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Marking Absent....");
        progressDialog.show();
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");
        String teacherUniqueID = sharedpreferences.getString("TEACHERID", "");

        // today date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String data = teacherUniqueID + "," + subject + "," + formattedDate+","+"late";



        // get absentList
        FirebaseDatabase studentdatabase = FirebaseDatabase.getInstance();
        DatabaseReference getAttendenceReference = studentdatabase.getReference("MarkAttendence").child(teacherUniqueID)
                .child(subject).child(formattedDate).child("absent");
        ArrayList<String> absentList=new ArrayList<>();
        getAttendenceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){


                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    absentList.add(snapshot1.getKey());
                }

                if(absentList.size()>0){
                    for (int i=0;i<absentList.size();i++){

                        // set absent for students
                        // set attendence for student
                        DatabaseReference studentAttendenceListReference = studentdatabase.getReference("Attendence").child("studentAttendenceList")
                                .child(subject).child(absentList.get(i));
                        studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                StudentPieModel studentPieModel = snapshot.getValue(StudentPieModel.class);
                                String present=studentPieModel.getPresentDays();
                                String late=studentPieModel.getLateDays();
                                String absent=studentPieModel.getAbsentDays();
                                 int pre=Integer.parseInt(absent)+1;
                                 studentAttendenceListReference.setValue(new StudentPieModel(present,String.valueOf(pre),late)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         finish();
                                     }
                                 });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
/*
                        try {
                       //     Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                    progressDialog.cancel();

                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


   /*     FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList")
                .child(subject).child(studentIsD);
        studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentPieModel studentPieModel = snapshot.getValue(StudentPieModel.class);
                String present=studentPieModel.getPresentDays();
                String late=studentPieModel.getLateDays();
                String absent=studentPieModel.getAbsentDays();

                if(presLate.equalsIgnoreCase("present")){
                    int pre=Integer.parseInt(present)+1;
                    studentAttendenceListReference.setValue(new StudentPieModel(String.valueOf(pre),absent,late));
                }else{
                    int pre=Integer.parseInt(late)+1;
                    studentAttendenceListReference.setValue(new StudentPieModel(present,absent,String.valueOf(pre)));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }


    private void generateQRCode(String subject, String late) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(subject, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private void requestPermissios(QRGeneratorActivity mainActivity) {
        if (ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestcameraPermission();
        }
    }

    private void requestcameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(QRGeneratorActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


}