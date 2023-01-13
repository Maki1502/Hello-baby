package edu.ib.hellobaby.model

class Info {

    private var infoname: String = ""
    private var infotxt: String = ""
    private var infotag: String = ""

    constructor()

    constructor(infoname: String, infotxt: String, infotag: String) {
        this.infoname = infoname
        this.infotxt = infotxt
        this.infotag = infotag
    }

    fun getInfoname(): String {
        return infoname
    }

    fun getInfotxt(): String {
        return infotxt
    }

    fun getInfotag(): String {
        return infotag
    }

    fun setInfoname(name: String) {
        this.infoname = name
    }

    fun setInfotxt(txt: String) {
        this.infotxt = txt
    }

    fun setInfotag(tag: String) {
        this.infotag = tag
    }

}