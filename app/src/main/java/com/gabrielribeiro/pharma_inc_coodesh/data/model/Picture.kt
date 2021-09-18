package com.gabrielribeiro.pharma_inc_coodesh.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable