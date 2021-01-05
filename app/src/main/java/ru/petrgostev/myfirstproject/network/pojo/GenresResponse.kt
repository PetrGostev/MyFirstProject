package ru.petrgostev.myfirstproject.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(

	@SerialName("genres")
	val genres: List<GenresItem>
)

@Serializable
data class GenresItem(

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int
)
