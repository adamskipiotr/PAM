package com.example.pam.responses

import com.example.pam.dto.StudentDTO
import com.example.pam.dto.TeacherDTO

class TeacherLoginResponse {
    private var activeTeacher: TeacherDTO? = null
    private var result: Boolean? = null


    constructor(activeTeacher: TeacherDTO, result: Boolean) {
        this.activeTeacher = activeTeacher
        this.result = result
    }

    constructor()

    fun  getResult():Boolean{
        return this.result!!
    }

    fun getActiveTeacher(): TeacherDTO {
        return this.activeTeacher!!
    }
}