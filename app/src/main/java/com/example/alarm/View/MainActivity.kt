package com.example.alarm.View

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alarm.R

class MainActivity : AppCompatActivity() {
    val idZadania = 383939;

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
        if(sprawdzUprawnienia()){
            checkbox.isChecked = true;
            checkbox.text = "OK"
        }else{
            checkbox.isChecked = false;
            checkbox.text = "Brak"
        }
    }

    fun sprawdzUprawnienia(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }

    fun zadajUpranien(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE), idZadania)
    }

}