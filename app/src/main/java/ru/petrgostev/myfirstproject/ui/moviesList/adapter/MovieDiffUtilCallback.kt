package ru.petrgostev.myfirstproject.ui.moviesList.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem

class MovieDiffUtilCallback : DiffUtil.ItemCallback<MoviesViewItem>(){
    override fun areItemsTheSame(oldItem: MoviesViewItem, newItem: MoviesViewItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesViewItem, newItem: MoviesViewItem): Boolean = oldItem == newItem
}