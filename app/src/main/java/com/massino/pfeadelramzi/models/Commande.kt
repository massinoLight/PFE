package com.massino.pfeadelramzi.models

import java.util.*

data class Commande( val article:String="" ,val mailUtilisateur: String="",val utlisateur: String="", val quantite: Int=-1, val date: String="",val adresse:String="",val prix:Int=-1) {
}
