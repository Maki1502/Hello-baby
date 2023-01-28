package edu.ib.hellobaby

class User {
    private var uid: String = ""
    private var email: String = ""

    constructor()

    constructor(uid: String, email: String) {
        this.uid = uid
        this.email = email
    }

    fun getUid(): String {
        return uid
    }

    fun getEmail(): String {
        return email
    }

    fun setUid(uid: String) {
        this.uid = uid
    }

    fun setEmail(email: String) {
        this.email = email
    }

}