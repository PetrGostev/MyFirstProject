package ru.petrgostev.myfirstproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    val actorName: String,
    val actorImage: Int
) : Parcelable
