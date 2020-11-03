package com.example.pam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pam.api.StudentApi
import com.example.pam.dto.StudentDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    lateinit var dbHandler: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val usernameInput = findViewById<TextView>(R.id.registerUsernameInput)
        val passwordInput = findViewById<TextView>(R.id.registerPasswordInput)
        val passwordRepeatInput = findViewById<TextView>(R.id.registerPasswordRepeatInput)
        val username = usernameInput.text.toString()

        dbHandler = DatabaseHelper(this)

        registerButton.setOnClickListener {
            /*  if (passwordInput != passwordRepeatInput) {
                  Toast.makeText(this, "Hasła nie są identyczne", Toast.LENGTH_SHORT).show()
              } */
            val builder = Retrofit.Builder()
            builder.baseUrl("http://192.168.0.213:8080/")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit: Retrofit
            retrofit = builder.build()
            val studentApi: StudentApi = retrofit.create(StudentApi::class.java)
            val newStudent =
                StudentDTO(1L, usernameInput.text.toString(), passwordInput.text.toString())

            val call: Call<StudentDTO> = studentApi.createStudent(newStudent)
            call.enqueue(object : Callback<StudentDTO> {

                override fun onFailure(call: Call<StudentDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<StudentDTO>, response: Response<StudentDTO>) {
                    Toast.makeText(applicationContext, "YEA", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}