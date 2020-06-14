package com.massino.pfeadelramzi.models

import java.util.*

data class Commande(val utlisateur: Utilisateur, val quantite: Int, val date: Date,val adresse:String,val prix:Float) {
}