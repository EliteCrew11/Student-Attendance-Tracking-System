package com.example.sampleapplication.professor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.R;
import com.example.sampleapplication.databinding.ActivityIntructorHomePageBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



public class IntructorHomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityIntructorHomePageBinding binding;
    NavigationView navigationView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntructorHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarIntructorHomePage.toolbar);
        String course=getIntent().getStringExtra("CourseType");
        String coursecrn="";
        if(course.contains("Java")){
            coursecrn="Java (13204)";
        } else  if(course.contains("Project")){
            coursecrn="Project Management (23123)";
        }
        else  if(course.contains("GDP")){
            coursecrn="GDP (13896)";
        } else  if(course.contains("Big")){
            coursecrn="Big Data (13900)";
        } else {
            coursecrn=course;
        }

        getSupportActionBar().setTitle(coursecrn);
        DrawerLayout drawer = binding.drawerLayout;
         navigationView = binding.navView;
        TextView name = navigationView.getHeaderView(0).findViewById(R.id.studentName);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("TEACHERNAME", "");


        name.setText(subject);



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_instructorAttendence,R.id.id_instructor_profile,R.id.nav_instructorAttendence)
                .setOpenableLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_intructor_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_intructor_home_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);

            if (resultCode == 1111) {
                Toast.makeText(IntructorHomePage.this, "SELECT DATA", Toast.LENGTH_SHORT).show();
             //   navController.navigate(R.id.nav_instructorAttendence);
                navController.popBackStack(R.id.nav_home, true);
                navController.navigate(R.id.nav_instructorAttendence);

            }
        }
    }

}