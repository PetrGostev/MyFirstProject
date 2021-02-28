package ru.petrgostev.myfirstproject.data.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity

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
) {
    fun toGenresEntity() = GenresEntity(id = id.toLong(), name = name)
}
