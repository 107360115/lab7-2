package com.example.myapplication_1

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2_1.R

class MainActivity : AppCompatActivity() {
    private var rabprograss = 0
    private var turprograss = 0
    private var seekBar: SeekBar? = null
    private var seekBar2: SeekBar? = null
    private var btn_start: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.seekBar)
        seekBar2 = findViewById(R.id.seekBar2)
        btn_start = findViewById(R.id.btn_start)
        val btn_startnew=btn_start
        val seekBarnew=seekBar
        val seekBar2new=seekBar2
        if (btn_startnew != null) {
            btn_startnew.setOnClickListener(View.OnClickListener {
                if (btn_startnew != null) {
                    btn_startnew.setEnabled(false)
                }
                rabprograss = 0
                turprograss = 0
                if (seekBarnew != null) {
                    seekBarnew.setProgress(0)
                }
                if (seekBar2new != null) {
                    seekBar2new.setProgress(0)
                }
                runThread()
                runAsyncTask()
            })
        }
    }

    private fun runThread() {
        Thread {
            while (rabprograss <= 100 && turprograss <= 100) {
                try {
                    Thread.sleep(100)
                    rabprograss += (Math.random() * 3).toInt()
                    val msg = Message()
                    msg.what = 1
                    mHandler.sendMessage(msg)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private val mHandler = Handler { msg ->
        when (msg.what) {
            1 -> seekBar!!.progress = rabprograss
        }
        if (rabprograss >= 100 && turprograss < 100) {
            Toast.makeText(this@MainActivity, "rabbit win", Toast.LENGTH_SHORT).show()
            btn_start!!.isEnabled = true
        }
        false
    }

    private fun runAsyncTask() {
        object : AsyncTask<Void?, Int?, Boolean>() {
            @SuppressLint("StaticFieldLeak")
            protected override fun doInBackground(vararg params: Void?): Boolean? {
                while (turprograss <= 100 && rabprograss <= 100) {
                    try {
                        Thread.sleep(100)
                        turprograss += (Math.random() * 3).toInt()
                        publishProgress(turprograss)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                return true
            }

            protected override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                seekBar2!!.progress = values[0]!!
            }

            override fun onPostExecute(aBoolean: Boolean) {
                super.onPostExecute(aBoolean)
                if (turprograss <= 100 && rabprograss <= 100) {
                    Toast.makeText(this@MainActivity, "turtle win", Toast.LENGTH_SHORT).show()
                    btn_start!!.isEnabled = true
                }
            }
        }.execute()
    }
}