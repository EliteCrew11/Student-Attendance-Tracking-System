package com.example.sampleapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sampleapplication.MainActivity;
import com.example.sampleapplication.R;
import com.example.sampleapplication.StudentLogin;

public class AdminLogin extends AppCompatActivity {

    EditText enter_paswword, _enter_username;
    ImageView showpassword;
    ConstraintLayout login;
    ImageView id_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        enter_paswword = findViewById(R.id._id_admin_enter_paswword);
        id_back = findViewById(R.id.id_back);
        showpassword = findViewById(R.id.id_showpassword);
        _enter_username = findViewById(R.id._id_admin_enter_username);
        login = findViewById(R.id.id_admin_login);

        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassword.getTag().equals("close")) {
                    showpassword.setTag("open");
                    enter_paswword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_open_password));

                } else {
                    showpassword.setTag("close");
                    enter_paswword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_close_password));

                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=_enter_username.getText().toString().trim();
                String password=enter_paswword.getText().toString().trim();

             /*   username="admin@nwmissouri.edu";
                password="123456";
             */   if(TextUtils.isEmpty(username)){
                    Toast.makeText(AdminLogin.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(AdminLogin.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(username.equals("admin@nwmissouri.edu") && password.equals("123456")){
                        Intent intent=new Intent(AdminLogin.this,AdminDashboard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(AdminLogin.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}