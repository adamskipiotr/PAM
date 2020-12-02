package com.example.pam.api

import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentDTO
import com.example.pam.dto.StudentsGroupDTO
import com.example.pam.responses.StudentLoginResponse
import retrofit2.Call
import retrofit2.http.*

 interface StudentApi {

    @POST("student/add")
    fun createStudent(@Body studentDTO: StudentDTO): Call<Void>

    @POST("student/login")
    fun loginStudent(@Body studentDTO: StudentDTO): Call<StudentLoginResponse>

    @POST("student/getAllMessages")
    fun getAllMessagesForStudent(@Body studentDTO: StudentDTO):Call<List<MessageDTO>>

    @POST("student/markAsSeen/{id}")
    fun markMessageAsSeen(@Path("id") id:Long,@Body messageDTO: MessageDTO):Call<Void>

    @GET("group/getAll")
    fun getAllStudentsGroups():Call<List<StudentsGroupDTO>>

    @POST("student/getUnreadMessagesCounter")
    fun getUnreadMessagesCounter(@Body studentDTO: StudentDTO): Call<Int>

    @POST("group/joinGroup/{IDGroupToJoin}")
     fun joinGroup(@Path("IDGroupToJoin") idGroupToJoin: Long?,@Body idStudent: Long): Call<Void>
 }