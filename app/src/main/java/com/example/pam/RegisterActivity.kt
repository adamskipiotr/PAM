package com.example.pam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    lateinit var dbHandler: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val usernameInput = findViewById<TextView>(R.id.registerUsernameInput)
        val passwordInput = findViewById<TextView>(R.id.registerPasswordInput)
        val passwordRepeatInput = findViewById<TextView>(R.id.registerPasswordRepeatInput)
        var username =  usernameInput.text.toString()

        dbHandler = DatabaseHelper(this)

        registerButton.setOnClickListener {
            if (passwordInput != passwordRepeatInput) {
                Toast.makeText(this, "Hasła nie są identyczne", Toast.LENGTH_SHORT).show()
            }
            val insertUserResult = dbHandler.insertUserData(login = usernameInput.text.toString(),password = passwordInput.text.toString() )

            if(insertUserResult){
                Toast.makeText(this, "Użytkownik $username został zapisany", Toast.LENGTH_LONG).show()
            }
        }
    }
}