package com.example.alarm.View

import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.DataAccess.OdpowiedzWebApi
import com.example.alarm.Model.Odpowiedz
import com.example.alarm.R

const val WIADOMOSC = "com.example.alarm.WIADOMOSC"

class AlarmView :  AppCompatActivity() {
    private var numerZgloszenia: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_alarm_view)



        var wiadomosc = this.intent.extras?.get(WIADOMOSC)

        if (wiadomosc != null) {
            var regex = Regex("\\[(\\d+)\\](.+)",RegexOption.MULTILINE)

            var wynik = regex.find(wiadomosc.toString(),0)
            if(wynik != null){
                findViewById<TextView>(R.id.wiadomosc).text = wynik.groups[2]!!.value
                numerZgloszenia =  wynik.groups[1]!!.value.toInt();
            }
        }
        val odtwarzacz = MediaPlayer.create(this, R.raw.syrena);

        findViewById<Button>(R.id.potwierdz).setOnClickListener { view ->
            odtwarzacz.stop()
            this.finish();
            
            var odpowiedz = OdpowiedzWebApi(fun(sukces){
                Toast.makeText(applicationContext, "sukcess", Toast.LENGTH_SHORT).show()
            })
            odpowiedz.execute(Odpowiedz(numerZgloszenia,"potwierdzony", "53.45006651204762, 17.08570578291182"))
        };

        findViewById<Button>(R.id.odrzuc).setOnClickListener { view ->
            odtwarzacz.stop()
            this.finish();
            var odpowiedz = OdpowiedzWebApi(fun(sukces){
                Toast.makeText(applicationContext, "sukcess", Toast.LENGTH_SHORT).show()
            })
            odpowiedz.execute(Odpowiedz(numerZgloszenia,"odrzucony", ""))
        };

        odtwarzacz.start();
    }
}