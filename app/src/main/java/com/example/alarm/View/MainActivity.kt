package com.example.alarm.View

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.alarm.R

class MainActivity : AppCompatActivity() {
    val idZadania = 3839;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        odswiezUprawnienia();
        findViewById<Button>(R.id.btnUprawnienia).setOnClickListener{
            zadajUpranien();
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==idZadania){
            odswiezUprawnienia();
        }
    }

    fun odswiezUprawnienia(){
        var checkbox = findViewById<CheckBox>(R.id.cbUprawnienia)
        var przyciskUprawnienia = findViewById<Button>(R.id.btnUprawnienia)
        if(sprawdzUprawnienia()){
            checkbox.isChecked = true;
            checkbox.text = "OK"
            przyciskUprawnienia.isVisible = false
        }else{
            checkbox.isChecked = false;
            checkbox.text = "Brak"
            przyciskUprawnienia.isVisible = true
        }
    }

    fun sprawdzUprawnienia(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.DISABLE_KEYGUARD) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
          }

    fun zadajUpranien(){
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.DISABLE_KEYGUARD,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION

                ), idZadania)
    }

}