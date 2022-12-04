package com.example.sampleapplication.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.R;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.ui.gallery.StudentPeopleAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class InstructorStudentProfile extends AppCompatActivity {


    RecyclerView studentPeople_recyclerview;
    StudentPeopleAdapter studentPeopleAdapter;
    ProgressDialog progressDialog;
    private com.github.mikephil.charting.charts.PieChart pieChart;
    CardView cardViewGraph;
    //org.eazegraph.lib.charts.PieChart piechart;
    TextView presentday, absentdays, latedays, no_record, present, absent, late, scoree;

    private void getStudentDate(PieChart pieChart) {
        progressDialog = new ProgressDialog(InstructorStudentProfile.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String subject = getIntent().getStringExtra("COURSE");
        String studentID = getIntent().getStringExtra("STUDENTKEY");
        String NAME = getIntent().getStringExtra("NAME");

        String getsubject = "";
        if (subject.contains("GDP-1 (13800)")) {
            getsubject = "GDP-1 (13800)";
        } else if (subject.contains("Big Data (13800)")) {
            getsubject = "Big Data (13800)";
        } else if (subject.contains("Java (13800)")) {
            getsubject = "Java (13800)";
        } else if (subject.contains("Project Management (13800)")) {
            getsubject = "Project Management (13800)";
        }

        DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList")
                .child(getsubject).child(studentID);


        //  String studentID = FirebaseAuth.getInstance().getUid();
        studentAttendenceListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());

                if (snapshot.hasChildren()) {
                    no_record.setVisibility(View.GONE);
                    //   cardViewGraph.setVisibility(View.VISIBLE);
                    StudentPieModel post = snapshot.getValue(StudentPieModel.class);
                    int pre = Integer.parseInt(post.getPresentDays());
                    int abs = Integer.parseInt(post.getAbsentDays());
                    int la = Integer.parseInt(post.getLateDays());
                    present.setText(post.getPresentDays() + " Present Days");
                    absent.setText(post.getAbsentDays() + " Absent Days");
                    late.setText(post.getLateDays() + " Late Days");
                    int prese = 0;
                    int absen = 0;
                    int lates = 0;
                    if (pre > 0) {
                        prese = (pre * 100) / (pre + abs + la);
                    }

                    if (la > 0) {
                        lates = (la * 50) / (pre + abs + la);
                    }

                    int pp = prese + lates;
                    scoree.setText("0%");
                    if (pp > 0) {
                        //  int uu=pp/(pre+la);
                        scoree.setText(String.valueOf(pp) + "%");
                    }

                    ArrayList<PieEntry> entries = new ArrayList<>();
                    ArrayList<Integer> colors = new ArrayList<>();
                    final int[] MY_COLORS = {Color.rgb(60, 179, 113), Color.rgb(255, 0, 0), Color.rgb(255, 192, 0)};

/*
                        entries.add(new PieEntry(22, "Present"));
                        colors.add(MY_COLORS[0]);
                        entries.add(new PieEntry(3, "Absent"));
                        colors.add(MY_COLORS[1]);
                        entries.add(new PieEntry(3, "Late"));
                        colors.add(MY_COLORS[2]);

*/

                    if (pre > 0) {
                        entries.add(new PieEntry(pre, "Present"));
                        colors.add(MY_COLORS[0]);
                    }

                    if (abs > 0) {
                        entries.add(new PieEntry(abs, "Absent"));
                        colors.add(MY_COLORS[1]);
                    }

                    if (la > 0) {

                        entries.add(new PieEntry(la, "Late"));
                        colors.add(MY_COLORS[2]);
                    }

                    /*
                    for (int color: ColorTemplate.MATERIAL_COLORS) {
                        colors.add(color);
                    }*/

                    PieDataSet dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(colors);

                    PieData data = new PieData(dataSet);
                    data.setDrawValues(true);
                    pieChart.setCenterText((pre + abs + la) + " Days");
                    data.setValueFormatter(new PercentFormatter(pieChart));
                    data.setValueTextSize(12f);
                    data.setValueTextColor(Color.BLACK);

                    pieChart.setData(data);
                    pieChart.invalidate();

                    pieChart.animateY(1400, Easing.EaseInOutQuad);

                } else {
                    //  no_record.setVisibility(View.VISIBLE);
                    cardViewGraph.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(InstructorStudentProfile.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_student_profile);

        pieChart = findViewById(R.id.activity_main_piechart);
        latedays = findViewById(R.id.latedays);
        cardViewGraph = findViewById(R.id.cardViewGraph);
        present = findViewById(R.id.present_days);
        absent = findViewById(R.id.absent_days);
        scoree = findViewById(R.id.scoree);
        late = findViewById(R.id.late_days);
        no_record = findViewById(R.id.no_record);
        absentdays = findViewById(R.id.absentdays);
        presentday = findViewById(R.id.presentday);

        //  no_record.setVisibility(View.VISIBLE);
        cardViewGraph.setVisibility(View.GONE);
        setupPieChart();
        //  loadPieChartData();

        if (CommonUtils.isConnectedToInternet(this)) {
            try {
                getStudentDate(pieChart);
                //   getStudentDate(pieChart);
                //   getStudentDate(registrationModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }


    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
    }


}