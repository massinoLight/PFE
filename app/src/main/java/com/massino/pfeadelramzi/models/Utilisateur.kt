package com.massino.pfeadelramzi.models

class Utilisateur (uidp : String,nomp:String,adressep:String,urlphotop:String) {
    private  var uid:String
        get() = field

    private  var nom:String = ""

        set(value) { field = value }

    private  var adresse:String = ""

        set(value) { adresse = value }

    private  var urlphoto:String = ""
        set(value) { field = value }

    init {
        this.uid = uidp
        this.nom = nomp
        this.adresse=adressep
        this.urlphoto=urlphotop
    }



}