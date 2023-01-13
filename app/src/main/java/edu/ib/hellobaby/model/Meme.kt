package edu.ib.hellobaby.model

class Meme {

    private var memeid: String = ""
    private var memeimage: String = ""
    private var memetag: String = ""

    constructor()

    constructor(memeid: String, memeimage: String, memetag: String) {
        this.memeid = memeid
        this.memeimage = memeimage
        this.memetag = memetag
    }

    fun getMemeid(): String {
        return memeid
    }

    fun getMemeimage(): String {
        return memeimage
    }

    fun getMemetag(): String {
        return memetag
    }

    fun setMemeid(memeid: String) {
        this.memeid = memeid
    }

    fun setMemeimage(memeimage: String) {
        this.memeimage = memeimage
    }

    fun setMemetag(memetag: String) {
        this.memetag = memetag
    }

}