package com.example.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.pam.api.StudentApi
import com.example.pam.api.TeacherApi
import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class User : AppCompatActivity() {
    var notificationCounter = 0
    lateinit var notificationsText: TextView
    lateinit var notificationsBox: CardView
    lateinit var spinner: Spinner
    lateinit var checkMessagesButton: Button
    lateinit var messageTextField: EditText
    lateinit var sendMessageButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mapViewsToReferences();

        val myStrings = arrayOf("Select", "One", "Two", "Three", "Four")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, myStrings)
        spinner.adapter = adapter

        checkMessagesButton.setOnClickListener() {
            notificationCounter = 0
            notificationsText.setText(notificationCounter.toString())
            notificationsText.visibility = View.INVISIBLE
            notificationsBox.visibility = View.INVISIBLE
            val intent = Intent(this, UserMessages::class.java).apply {};
            startActivity(intent)
        }

        sendMessageButton.setOnClickListener() {
            val builder = Retrofit.Builder()
            builder.baseUrl("http://IP-KOMPUTERA-UZUPELNIC:8080/")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit : Retrofit
            retrofit = builder.build()
            val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
            val newMessageDTO = MessageDTO(1L,messageTextField.text.toString())

            val call: Call<MessageDTO> = teacherApi.sendNewMessage(newMessageDTO)
            call.enqueue(object: Callback<MessageDTO> {

                override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<MessageDTO>, response: Response<MessageDTO>) {
                    Toast.makeText(applicationContext, "YEA", Toast.LENGTH_LONG).show()
                }

            })

            Toast.makeText(this, messageTextField.text.toString(), Toast.LENGTH_LONG).show()
            notificationCounter = notificationsText.text.toString().toInt()
            notificationCounter += 1
            if (notificationCounter == 0) {
                notificationsText.visibility = View.INVISIBLE
                notificationsBox.visibility = View.INVISIBLE
            } else {
                notificationsText.visibility = View.VISIBLE
                notificationsBox.visibility = View.VISIBLE
            }
            notificationsText.setText(notificationCounter.toString())
        }
    }

    private fun mapViewsToReferences() {
        notificationsText = findViewById<TextView>(R.id.notificationsCounterText)
        notificationsBox = findViewById<CardView>(R.id.notificationsCardView)
        spinner = findViewById<Spinner>(R.id.spinnerRecieverGroup)
        checkMessagesButton = findViewById<Button>(R.id.recievedMessagesButton)
        messageTextField = findViewById<EditText>(R.id.messageFieldInput)
        sendMessageButton = findViewById<Button>(R.id.sendMessageButton)
    }
}