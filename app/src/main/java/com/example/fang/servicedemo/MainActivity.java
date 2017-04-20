package com.example.fang.servicedemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startService,stopService;
    private static final String TAG = "MainActivity";
    private int param=1;

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        iniView();


        bindService(new Intent(MainActivity.this,MyService.class),connection, Context.BIND_AUTO_CREATE);
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE
            },1001);
        }
    }

    private void iniView() {
        startService= (Button) findViewById(R.id.startservice);
        stopService= (Button) findViewById(R.id.stopservice);


        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startservice:
                startService(new Intent(MainActivity.this,MyService.class));
                break;
            case R.id.stopservice:
                stopService(new Intent(MainActivity.this,MyService.class));
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");

        unbindService(connection);

    }
}
