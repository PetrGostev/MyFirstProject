package ru.petrgostev.myfirstproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    var actorName: String,
    var actorImage: Int
) : Parcelable
