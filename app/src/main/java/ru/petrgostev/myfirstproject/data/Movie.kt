package ru.petrgostev.myfirstproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val duration: Int,
    val reviewsQuantity: Int,
    val rating: Float,
    val category: String,
    val like: Boolean,
    val ageLimit: Int,
    val imagePoster: Int,
    val actors: List<Actor>
) : Parcelable
