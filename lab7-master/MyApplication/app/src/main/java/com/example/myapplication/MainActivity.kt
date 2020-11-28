package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int rabprograss=0,turprograss=0;

    private SeekBar seekBar,seekBar2;
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        seekBar2=findViewById(R.id.seekBar2);
        btn_start=findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setEnabled(false);
                rabprograss = 0;
                turprograss = 0;
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                runThread();
                runAsyncTask();
            }

        });
    }

    private void runThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rabprograss<=100&&turprograss<=100){
                    try {
                        Thread.sleep(100);
                        rabprograss += (int) (Math.random() * 3);
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage( Message msg) {
            switch (msg.what){
                case 1:
                seekBar.setProgress(rabprograss);
                break;
            }
            if(rabprograss>=100&&turprograss<100){
                Toast.makeText(MainActivity.this,"rabbit win",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            return false;
        }
    });
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
        @Override
        protected Boolean doInBackground(Void...voids) {
        while (turprograss <= 100 && rabprograss <= 100) {
            try {
                Thread.sleep(100);
                turprograss += (int) (Math.random() * 3);

                publishProgress(turprograss);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

        @Override
        protected void onProgressUpdate(Integer...values){
        super.onProgressUpdate(values);
        seekBar2.setProgress(values[0]);
    }
        @Override
        protected void onPostExecute(Boolean aBoolean){
            super.onPostExecute(aBoolean);
            if(turprograss <= 100 && rabprograss <= 100) {
                Toast.makeText(MainActivity.this,"turtle win",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
        }
    }.execute();

    }
}






