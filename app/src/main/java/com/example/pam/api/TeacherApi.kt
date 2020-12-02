package com.example.pam.api

import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentsGroupDTO
import com.example.pam.dto.TeacherDTO
import com.example.pam.responses.TeacherLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TeacherApi {

    @POST("teacher/sendMessage")
    fun sendNewMessage(@Body messageDTO: MessageDTO): Call<Void>

    @GET("group/getAll")
    fun getAllStudentsGroups():Call<List<StudentsGroupDTO>>

    @POST("teacher/login")
    fun loginTeacher(@Body teacherDTO: TeacherDTO):Call<TeacherLoginResponse>

    @POST("teacher/add")
    fun createTeacher(@Body teacherDTO:  TeacherDTO): Call<Void>

    @POST("group/addNewGroup")
    fun createNewGroup(@Body newStudentGroupName: String): Call<Void>

    @POST("teacher/getAllMessages")
    fun getAllTeacherMessages(@Body teacherDTO: TeacherDTO):Call<List<MessageDTO>>

    @POST("teacher/getMessageDetails")
    fun getMessageDetails(messageToCheck: MessageDTO): Call<Void>
}