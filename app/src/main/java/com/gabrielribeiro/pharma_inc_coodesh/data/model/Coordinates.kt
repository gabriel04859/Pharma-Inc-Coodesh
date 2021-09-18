package com.gabrielribeiro.pharma_inc_coodesh.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    val latitude: String,
    val longitude: String,
) : Parcelable
