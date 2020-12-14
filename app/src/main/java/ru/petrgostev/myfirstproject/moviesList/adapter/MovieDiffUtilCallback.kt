package ru.petrgostev.myfirstproject.MoviesList.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.data.Movie

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}