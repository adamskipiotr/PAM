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

    constructor()
}
