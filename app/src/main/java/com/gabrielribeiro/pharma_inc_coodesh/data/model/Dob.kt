package com.gabrielribeiro.pharma_inc_coodesh.data.model

import android.os.Parcelable
import com.gabrielribeiro.pharma_inc_coodesh.utils.BackendUtils
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Dob(
    val age: Int,
    val date: String
) : Parcelable {

}