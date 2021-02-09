package ru.petrgostev.myfirstproject.ui.moviesList.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity

class MovieDiffUtilCallback : DiffUtil.ItemCallback<MoviesEntity>(){
    override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean = oldItem == newItem
}