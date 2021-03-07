package com.example.alarm.View

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.alarm.DataAccess.OdpowiedzWebApi
import com.example.alarm.Model.Odpowiedz
import com.example.alarm.R
import com.google.android.gms.location.LocationServices

const val WIADOMOSC = "com.example.alarm.WIADOMOSC"

class AlarmView :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_alarm_view)

        var ostatniaLokalizacja :Location? = null;

        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                       ostatniaLokalizacja = location;
                    }
        }


        var numerZgloszenia = 0;
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
                Toast.makeText(applicationContext, "potwierdzono", Toast.LENGTH_SHORT).show()
            })
            odpowiedz.execute(Odpowiedz(numerZgloszenia,"potwierdzony", if( ostatniaLokalizacja != null)  "${ostatniaLokalizacja!!.latitude} ${ostatniaLokalizacja!!.longitude}" else "" ))


        };

        findViewById<Button>(R.id.odrzuc).setOnClickListener { view ->
            odtwarzacz.stop()
            this.finish();

            var odpowiedz = OdpowiedzWebApi(fun(sukces){
                Toast.makeText(applicationContext, "odrzucono", Toast.LENGTH_SHORT).show()
            })
            odpowiedz.execute(Odpowiedz(numerZgloszenia,"odrzucony", if( ostatniaLokalizacja != null)  "${ostatniaLokalizacja!!.latitude} ${ostatniaLokalizacja!!.longitude}" else "" ))

    };

        odtwarzacz.start();
    }
}