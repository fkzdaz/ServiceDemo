package com.example.fang.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Fkz on 2017/4/19.
 */

public class MyService extends Service {

    //录音
    private MediaRecorder recorder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        getService();
    }

    private void getService() {
        //服务一创建就监听
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(new myListener(), PhoneStateListener.LISTEN_CALL_STATE);
        getRecorder();

    }

    class myListener extends PhoneStateListener {

        //电话状态改变的回调
        //暂时无法改变回调
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            //判断当前的状态
            switch (state) {
                //电话空闲
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i("myListener", "电话空闲");
                    break;
                //电话响铃
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("myListener", "电话响铃");

                    break;
                //正在通话
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i("myListener", "电话通话");

                    break;
            }
        }
    }

    /**
     * 通话录音
     */
    private void getRecorder() {
        recorder = new MediaRecorder();
        //麦克风
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置格式 3GP
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //设置保存目录 权限
        recorder.setOutputFile("sdcard/audio.3gp");
        //设置音频编码
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            //准备录音
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
}
