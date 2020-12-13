package com.example.pam

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.pam.api.StudentApi
import com.example.pam.api.TeacherApi
import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentsGroupDTO
import com.example.pam.services.RetrofitBuilderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class TeacherMessages : AppCompatActivity() {
    lateinit var spinner: Spinner
    private lateinit var messageTextField: EditText
    private lateinit var titleTextField: EditText
    private lateinit var sendMessageButton: Button
    val context: Context = this
    var studentsGroupsList: List<StudentsGroupDTO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_send_message)
        mapViewsToReferences()
        val sp: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)

        val teacherApi: TeacherApi = RetrofitBuilderService.buildRetrofitService(TeacherApi::class.java)
        val username = sp.getString("username", "EMPTY")
        val call: Call<List<StudentsGroupDTO>> = teacherApi.getAllStudentsGroups()
        call.enqueue(object : Callback<List<StudentsGroupDTO>> {

            override fun onFailure(call: Call<List<StudentsGroupDTO>>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd pobierania listy grup", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(
                call: Call<List<StudentsGroupDTO>>,
                response: Response<List<StudentsGroupDTO>>
            ) {
                val studentsGroupsNames: MutableList<String> = LinkedList<String>().toMutableList()
                studentsGroupsList = response.body()
                studentsGroupsList?.forEach {
                    studentsGroupsNames += it.toString()
                }
                val adapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, studentsGroupsNames)
                spinner.adapter = adapter
            }
        })

        sendMessageButton.setOnClickListener {
            var idGroupToInform: Long? = null
            val selectedGroupName = spinner.selectedItem.toString()
            studentsGroupsList?.forEach {
                if (it.groupName == selectedGroupName) {
                    idGroupToInform = it.groupID!!
                }
            }
            val messageDTO = MessageDTO(
                idGroupToInform!!,
                messageTextField.text.toString(),
                titleTextField.text.toString(),
                username!!
            )
            val callSendMessage: Call<Void> = teacherApi.sendNewMessage(messageDTO)
            callSendMessage.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Wiadomość została wysłana",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }


    private fun mapViewsToReferences() {
        spinner = findViewById(R.id.spinnerRecieverGroup)
        messageTextField = findViewById(R.id.messageFieldInput)
        sendMessageButton = findViewById(R.id.sendMessageButton)
        titleTextField = findViewById(R.id.messageTileTextView)
    }
}