package com.massino.pfeadelramzi.models

class Utilisateur (uidp : String,nomp:String,adressep:String,urlphotop:String) {
    // RAMZI---- admin: Boolean dans le constructeur
    private  var uid:String
        get() = field

    // RAMZI--    private var admin:Boolean= false
    // AJOUTER la variable admin pour savoir si c'est un client ou admin

    private  var nom:String = ""

        set(value) { field = value }

    private  var adresse:String = ""
        // pour le setter c'est pas field= value ??
        set(value) { adresse = value }

    private  var urlphoto:String = ""
        set(value) { field = value }

    init {
        this.uid = uidp
        this.nom = nomp
        this.adresse=adressep
        this.urlphoto=urlphotop
        // RAMZI----  this.admin=admin
    }



}