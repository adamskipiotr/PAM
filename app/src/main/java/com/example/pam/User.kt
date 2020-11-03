package com.example.pam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.example.pam.api.TeacherApi
import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentsGroupDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class User : AppCompatActivity() {
    var notificationCounter = 0
    lateinit var notificationsText: TextView
    lateinit var notificationsBox: CardView
    lateinit var spinner: Spinner
    lateinit var checkMessagesButton: Button
    lateinit var messageTextField: EditText
    lateinit var titleTextField: EditText
    lateinit var sendMessageButton: Button
    val context: Context = this
    var studentsGroupsList: List<StudentsGroupDTO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mapViewsToReferences();

        val builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.0.213:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()
        val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
        val call: Call<List<StudentsGroupDTO>> = teacherApi.getAllStudentsGroups()
        call.enqueue(object : Callback<List<StudentsGroupDTO>> {

            override fun onFailure(call: Call<List<StudentsGroupDTO>>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd pobierania listy grup", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(  call: Call<List<StudentsGroupDTO>>,
                                      response: Response<List<StudentsGroupDTO>>) {
                val studentsGroupsNames: MutableList<String> = LinkedList<String>().toMutableList()
                studentsGroupsList = response.body()
                studentsGroupsList?.forEach {
                    studentsGroupsNames+= it.toString()
                }
                val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, studentsGroupsNames)
                spinner.adapter = adapter
            }
        })

        checkMessagesButton.setOnClickListener() {
            notificationCounter = 0
            notificationsText.setText(notificationCounter.toString())
            notificationsText.visibility = View.INVISIBLE
            notificationsBox.visibility = View.INVISIBLE
            val intent = Intent(this, UserMessages::class.java).apply {};
            startActivity(intent)
        }

        sendMessageButton.setOnClickListener() {
            var idGroupToInform:Long? = null
            val selectedGroupName = spinner.selectedItem.toString()
            studentsGroupsList?.forEach {
               if(it.groupName.equals(selectedGroupName)){
                   idGroupToInform = it.groupID!!
               }
            }
            val messageDTO = MessageDTO(idGroupToInform!!, messageTextField.text.toString(),titleTextField.text.toString(),"Autor testowy")
            val callSendMessage: Call<Void> = teacherApi.sendNewMessage(messageDTO)
            callSendMessage.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(  call:  Call<Void>,
                                          response:  Response<Void>) {
                    Toast.makeText(applicationContext, "Wiadomość została wysłana", Toast.LENGTH_LONG).show()
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
        notificationsText = findViewById(R.id.notificationsCounterText)
        notificationsBox = findViewById(R.id.notificationsCardView)
        spinner = findViewById(R.id.spinnerRecieverGroup)
        checkMessagesButton = findViewById(R.id.recievedMessagesButton)
        messageTextField = findViewById(R.id.messageFieldInput)
        sendMessageButton = findViewById(R.id.sendMessageButton)
        titleTextField = findViewById(R.id.messageTileTextView)
    }
}