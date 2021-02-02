package com.example.alarm.View

import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R

const val WIADOMOSC = "com.example.alarm.WIADOMOSC"

class AlarmView :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_alarm_view)


        var wiadomosc = this.intent.extras?.get(WIADOMOSC)
        if (wiadomosc != null) {
            findViewById<TextView>(R.id.wiadomosc).text = wiadomosc.toString();
        }


        val odtwarzacz = MediaPlayer.create(this, R.raw.syrena);
        findViewById<Button>(R.id.potwierdz).setOnClickListener { view ->
            odtwarzacz.stop()
            this.finish();
        };

        findViewById<Button>(R.id.odrzuc).setOnClickListener { view ->
            odtwarzacz.stop()
            this.finish();
        };

        odtwarzacz.start();
    }
}