package com.example.pam.dto

class StudentsGroupDTO {
    var groupID: Long? = null
    lateinit var groupName:String

    constructor(groupID:Long,groupName: String) {
        this.groupID = groupID
        this.groupName = groupName
    }

    constructor()

    override fun toString(): String {
        return groupName;
    }
}