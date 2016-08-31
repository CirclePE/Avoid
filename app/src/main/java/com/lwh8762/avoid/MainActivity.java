package com.lwh8762.avoid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    private Button startBtn;
    private Button debugBtn;
    private Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }catch (Exception e) {
            Toast.makeText(MainActivity.this,"#0\n" + e.getMessage() + "\n" + e.getStackTrace(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        try {
            /*int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionCheck== PackageManager.PERMISSION_DENIED) {

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PER);
            }
*/
            try {
                GameInfo.setPreference(getSharedPreferences("info", MODE_PRIVATE));
                GameInfo.maxLevel = getAssets().list("mapscript").length;
            } catch (IOException e) {
                e.printStackTrace();
            }

            startBtn = (Button) findViewById(R.id.startBtn);
            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(MainActivity.this, SelectActivity.class));
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this,"#1\n" + e.getMessage() + "\n" + e.getStackTrace(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
            debugBtn = (Button) findViewById(R.id.debugBtn);
            debugBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("debug", true);
                        startActivity(intent);
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this,"#2\n" + e.getMessage() + "\n" + e.getStackTrace(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
            resetBtn = (Button) findViewById(R.id.resetBtn);
            resetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
                        d.setMessage("레벨 초기화");
                        d.setNegativeButton("취소", null);
                        d.setPositiveButton("초기화", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GameInfo.setCurrentLevel(0);
                             }
                        });
                        d.show();
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this,"#3\n" + e.getMessage() + "\n" + e.getStackTrace(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            Toast.makeText(this,e.getMessage() + "\n" + e.getStackTrace(),Toast.LENGTH_LONG).show();
        }
    }
}
