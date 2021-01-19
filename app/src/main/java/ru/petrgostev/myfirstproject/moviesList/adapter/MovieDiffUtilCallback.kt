package ru.petrgostev.myfirstproject.moviesList.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem

class MovieDiffUtilCallback : DiffUtil.ItemCallback<MoviesItem>(){
    override fun areItemsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean = oldItem == newItem
}