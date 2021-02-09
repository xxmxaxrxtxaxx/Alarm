package com.example.alarm.DataAccess

import android.os.AsyncTask
import com.example.alarm.Model.Odpowiedz
import com.google.gson.Gson
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

open class OdpowiedzWebApi(val funkcjaZwrotna: (Boolean) -> Unit) : AsyncTask<Odpowiedz, Void, Boolean>() {
    override fun doInBackground(vararg params: Odpowiedz?): Boolean {
        val adres = URL("http://ospalarm.herokuapp.com/api/wezwanie/" + params[0]!!.id)
        val json = Gson().toJson(params[0])
        val polaczenie: HttpURLConnection = adres.openConnection() as HttpURLConnection

        polaczenie.setDoInput(true)
        polaczenie.setRequestProperty("Content-Type", "application/json");
        polaczenie.requestMethod = "PUT"
        val out = OutputStreamWriter(
                polaczenie.getOutputStream())
        out.write(json);
        out.close();
        if (polaczenie.responseCode == 200) {
            return true
        } else {//bład
            return false
        }
    }

    override fun onPostExecute(wynik: Boolean?) {
        super.onPostExecute(wynik)
        funkcjaZwrotna(wynik == true)
    }
}


