package com.example.pam.dto


class StudentDTO {
    private var studentID: Long? = null
    private var username: String? = null
    private var password: String? = null

    constructor(studentID: Long, username: String, password: String) {
        this.studentID = studentID
        this.username = username
        this.password = password
    }

    fun getUsername(): String {
        return username!!
    }

    fun getPassword(): String {
        return password!!
    }

    fun getstudentID(): Long {
        return studentID!!
    }

    constructor()
}
