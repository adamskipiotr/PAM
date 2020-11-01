package com.example.pam.api

import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentsGroupDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TeacherApi {


    @POST("teacher/sendMessage")
    fun sendNewMessage(@Body messageDTO: MessageDTO): Call<MessageDTO>

    @GET("group/getAll")
    fun getAllStudentsGroups():Call<List<StudentsGroupDTO>>
}