package com.example.pam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class TeacherActivity : AppCompatActivity() {

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
        val sendMessagesButton = findViewById<Button>(R.id.openSendMessageActivityButton)
        val addStudentsGroupButton = findViewById<Button>(R.id.addStudentsGroupButton)
        val showMessagesHistoryButton = findViewById<Button>(R.id.showMessagesHistoryButton)


        sendMessagesButton.setOnClickListener {
            val intent = Intent(context, TeacherMessages::class.java).apply {};
            startActivity(intent)
        }

        addStudentsGroupButton.setOnClickListener {
            val intent = Intent(context, AddStudentGroupActivity::class.java).apply {};
            startActivity(intent)
        }

        showMessagesHistoryButton.setOnClickListener {
            val intent = Intent(context, ShowMessagesHistoryActivity::class.java).apply {};
            startActivity(intent)
        }
    }
}