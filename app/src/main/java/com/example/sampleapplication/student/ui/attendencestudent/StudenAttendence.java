package com.example.sampleapplication.student.ui.attendencestudent;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentModel;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.ui.gallery.StudentPeopleAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudenAttendence extends Fragment {

    RecyclerView studentPeople_recyclerview;
    StudentPeopleAdapter studentPeopleAdapter;
    ProgressDialog progressDialog;
    private PieChart pieChart;
    CardView cardViewGraph;
    //org.eazegraph.lib.charts.PieChart piechart;
    TextView presentday,absentdays,latedays,no_record,present,absent,late,scoree;

    private void getStudentDate(PieChart piechart) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        String studentID=FirebaseAuth.getInstance().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences sharedpreferences =getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");
        String getsubject="";
                 if(subject.contains("GDP-1 (13800)")) {
                     getsubject="GDP-1 (13800)";
                 } else if(subject.contains("Big Data (13800)")) {
                     getsubject="Big Data (13800)";
                 } else if(subject.contains("Java (13800)")) {
                     getsubject="Java (13800)";
                 } else if(subject.contains("Project Management (13800)")) {
                     getsubject="Project Management (13800)";
                 }

        DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList")
                .child(getsubject).child(studentID);

        //  String studentID = FirebaseAuth.getInstance().getUid();
        studentAttendenceListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());

                if(snapshot.hasChildren()){
                    no_record.setVisibility(View.GONE);
                 //   cardViewGraph.setVisibility(View.VISIBLE);
                    StudentPieModel post = snapshot.getValue(StudentPieModel.class);
                    int pre=Integer.parseInt(post.getPresentDays());
                    int abs=Integer.parseInt(post.getAbsentDays());
                    int la=Integer.parseInt(post.getLateDays());
                    present.setText(post.getPresentDays()+" Present Days");
                    absent.setText(post.getAbsentDays()+" Absent Days");
                    late.setText(post.getLateDays()+" Late Days");
                    int pp=(pre*100)+(la*50);
                    scoree.setText("0%");
                    if(pp>0){
                        int uu=pp/(pre+la+abs);
                        scoree.setText(String.valueOf(uu)+"%");
                    }

                    ArrayList<PieEntry> entries = new ArrayList<>();
                    ArrayList<Integer> colors = new ArrayList<>();
                    final int[] MY_COLORS = { Color.rgb(60, 179, 113),Color.rgb(255, 0, 0), Color.rgb(255,192,0)};

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

                    if(abs>0){
                        entries.add(new PieEntry(abs, "Absent"));
                        colors.add(MY_COLORS[1]);
                    }

                    if(la>0){

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
                    pieChart.setCenterText((pre+abs+la)+" Days");
                    data.setValueFormatter(new PercentFormatter(pieChart));
                    data.setValueTextSize(12f);
                    data.setValueTextColor(Color.BLACK);

                    pieChart.setData(data);
                    pieChart.invalidate();

                    pieChart.animateY(1400, Easing.EaseInOutQuad);

                }else{
               //     no_record.setVisibility(View.VISIBLE);
                    cardViewGraph.setVisibility(View.GONE);
                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_studen_attendence, container, false);
        pieChart=view.findViewById(R.id.activity_main_piechart);
        latedays=view.findViewById(R.id.latedays);
        cardViewGraph=view.findViewById(R.id.cardViewGraph);
        present=view.findViewById(R.id.present_days);
        absent=view.findViewById(R.id.absent_days);
        scoree=view.findViewById(R.id.scoree);
        late=view.findViewById(R.id.late_days);
        no_record=view.findViewById(R.id.no_record);
        absentdays=view.findViewById(R.id.absentdays);
        presentday=view.findViewById(R.id.presentday);

      //  no_record.setVisibility(View.VISIBLE);
        cardViewGraph.setVisibility(View.GONE);
        setupPieChart();
      //  loadPieChartData();


        if (CommonUtils.isConnectedToInternet(getActivity())) {
            try {
                getStudentDate(pieChart);
             //   getStudentDate(pieChart);
             //   getStudentDate(registrationModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadPieChartData() {
    /*    ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(2, "Present"));
        entries.add(new PieEntry(3, "Absent"));
        entries.add(new PieEntry(6, "Late"));

        ArrayList<Integer> colors = new ArrayList<>();
        *//*colors.add(R.color.green);
        colors.add(R.color.red);
        colors.add(R.color.yellow);
        *//*

        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
*//*
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }*//*

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);*/

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
/*
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);*/
    }


}
