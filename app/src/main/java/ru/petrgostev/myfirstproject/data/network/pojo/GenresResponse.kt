package ru.petrgostev.myfirstproject.data.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(

	@SerialName("genres")
	val genres: List<GenresItem>
)

@Serializable
class GenresItem(

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int
)
