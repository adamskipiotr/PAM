package com.example.pam

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url =  "https://pam-polsl.herokuapp.com/hello"
        AsyncTaskHandleJson().execute(url)

        val registerActivityButton = findViewById<Button>(R.id.loginButton)
        registerActivityButton.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java).apply {};
            startActivity(intent)
        }

        val loginActivityButton = findViewById<Button>(R.id.loginLayoutButton)
        loginActivityButton.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java).apply {};
            startActivity(intent)
        }
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text:String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try{
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return "OK";
        }

    }
}