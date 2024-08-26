package com.example.studentlist.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Students(
    val studentId: String = "",
    val name: String = "",
    val std: String = "",
    val date_joining: String = "",
    val gender: String = "",
    val stream: String = "",
    val phNo: String = "",
    val city: String = "",
    val state: String = ""
) : Parcelable
