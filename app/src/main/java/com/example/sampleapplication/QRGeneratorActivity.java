package com.example.sampleapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.FileSystems;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QRGeneratorActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private int CAMERA_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        requestPermissios(QRGeneratorActivity.this);


        try {
         //   String subject=getIntent().getStringExtra("QRCODE");
            SharedPreferences sharedpreferences =getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
            String subject = sharedpreferences.getString("CourseType", "");

            generateQRCode(subject);
            showTimer();
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

    }

    private void showTimer() {
    try {



        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {

                NumberFormat f = new DecimalFormat("00");

                long hour = (millisUntilFinished / 3600000) % 24;

                long min = (millisUntilFinished / 60000) % 60;

                long sec = (millisUntilFinished / 1000) % 60;

                ((TextView) findViewById(R.id.timerr)).setText("seconds remaining: "+f.format(hour) +":" + f.format(min) + ":" + f.format(sec));
                //       ((TextView) findViewById(R.id.timerr)).setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                finish();
            //    mTextField.setText("done!");
            }
        }.start();
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    private void generateQRCode(String subject) {
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
