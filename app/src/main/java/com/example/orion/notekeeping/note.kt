package com.example.orion.notekeeping

class note {

    var notid:Int?=null
    var notname:String?=null
    var notdes:String?=null

    constructor(notid:Int,notname:String,notdes:String){
        this.notdes=notdes
        this.notid=notid
        this.notname=notname
    }
}