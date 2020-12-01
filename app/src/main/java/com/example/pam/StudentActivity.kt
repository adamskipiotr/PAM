package com.example.pam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.pam.api.StudentApi
import com.example.pam.dto.StudentDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class StudentActivity : AppCompatActivity() {
    var notificationCounter = 0
    lateinit var notificationsText: TextView
    lateinit var notificationsBox: CardView
    lateinit var checkMessagesButton: Button
    val context: Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val signUpStudentsGroupButton = findViewById<Button>(R.id.openSendMessageActivityButton)

        notificationsText = findViewById(R.id.notificationsCounterText)
        notificationsBox = findViewById(R.id.notificationsCardView)
        checkMessagesButton = findViewById(R.id.recievedMessagesButton)
        notificationsText.visibility = View.INVISIBLE
        notificationsBox.visibility = View.INVISIBLE

        val builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.0.213:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()
        val studentApi: StudentApi = retrofit.create(StudentApi::class.java)
        val studentDTO =  StudentDTO(1L,"Android","Haslo")
        val call: Call<Int> = studentApi.getUnreadMessagesCounter(studentDTO)
        call.enqueue(object : Callback<Int> {

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd pobierania listy grup", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                notificationsText.visibility = View.VISIBLE
                notificationsBox.visibility = View.VISIBLE
                notificationCounter = response.body()!!
                notificationsText.setText(notificationCounter.toString())
            }
        })

        checkMessagesButton.setOnClickListener() {
            notificationCounter = 0
            notificationsText.setText(notificationCounter.toString())
            notificationsText.visibility = View.INVISIBLE
            notificationsBox.visibility = View.INVISIBLE
            val intent = Intent(this, StudentMessages::class.java).apply {};
            startActivity(intent)
        }

        signUpStudentsGroupButton.setOnClickListener {
            val intent = Intent(this, JoinStudentsGroupActivity::class.java).apply {};
            startActivity(intent)
        }

    }
}