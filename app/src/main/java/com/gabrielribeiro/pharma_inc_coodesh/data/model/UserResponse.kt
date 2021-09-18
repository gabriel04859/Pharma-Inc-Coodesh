package com.gabrielribeiro.pharma_inc_coodesh.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
) : Parcelable{
    fun formattedGender() : String {
        return if (gender == "female") "Feminino" else "Masculino"
    }


}