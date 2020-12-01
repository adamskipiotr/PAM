package com.example.pam

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.pam.adapters.MessageAdapter
import com.example.pam.api.TeacherApi
import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentDTO
import com.example.pam.dto.StudentsGroupDTO
import com.example.pam.dto.TeacherDTO
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class TeacherMessages : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var messageTextField: EditText
    lateinit var titleTextField: EditText
    lateinit var sendMessageButton: Button
    val context: Context = this
    var studentsGroupsList: List<StudentsGroupDTO>? = null
    var arrayListMessage: ArrayList<MessageDTO>? = null
    var messagesToRead: List<MessageDTO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_send_message)
        val  sp: SharedPreferences = getSharedPreferences("login",MODE_PRIVATE)
        mapViewsToReferences();

        val builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.0.213:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()
        val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
        val ID = sp.getLong("ID",-1)
        val username =  sp.getString("username","EMPTY")
        val password = sp.getString("password","EMPTY")
        val activeTeacher = TeacherDTO(ID, username!!, password!!)
        val call: Call<List<MessageDTO>> = teacherApi.getAllTeacherMessages(activeTeacher)
        call.enqueue(object : Callback<List<MessageDTO>> {

            override fun onFailure(call: Call<List<MessageDTO>>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<MessageDTO>>,
                response: Response<List<MessageDTO>>
            ) {
                Toast.makeText(applicationContext, "Sukces", Toast.LENGTH_LONG).show()
                val messagesToShow: MutableList<String> = LinkedList<String>().toMutableList()
                arrayListMessage = ArrayList()
                messagesToRead = response.body()
                messagesToRead?.forEach {
                    messagesToShow += it.toString()
                    arrayListMessage!!.add(it)
                }

                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    messagesToShow
                )
                val listViewItem = findViewById<ListView>(R.id.listView)
                val adapterCustom = MessageAdapter(context, arrayListMessage!!)
                listViewItem.adapter = adapterCustom

                listViewItem.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        val clickedItem = parent.getItemAtPosition(position).toString()

                        MaterialAlertDialogBuilder(context).apply {
                            setTitle("Item: $clickedItem")
                            setMessage("Oznaczyć jako przeczytaną?")
                            setPositiveButton("Potwierdź") { _, _ ->
                                val messageToCheck = messagesToRead!!.elementAt(position)
                                val call: Call<Void> =
                                    teacherApi.getMessageDetails(messageToCheck)
                                call.enqueue(object : Callback<Void> {

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Błąd serwera",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    override fun onResponse(
                                        call: Call<Void>,
                                        response: Response<Void>
                                    ) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Oznaczono jako przeczytana",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                })
                                messagesToShow.removeAt(position)
                                //TODO tu powinno sie usuwac
                                adapter.notifyDataSetChanged()
                            }
                            setNeutralButton("Anuluj") { _, _ -> }
                        }.create().show()
                    }
            }
        })
    }

    private fun mapViewsToReferences() {
        spinner = findViewById(R.id.spinnerRecieverGroup)
        messageTextField = findViewById(R.id.messageFieldInput)
        sendMessageButton = findViewById(R.id.sendMessageButton)
        titleTextField = findViewById(R.id.messageTileTextView)
    }
}