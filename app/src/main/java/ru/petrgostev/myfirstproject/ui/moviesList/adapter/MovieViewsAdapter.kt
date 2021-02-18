package ru.petrgostev.myfirstproject.ui.moviesList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity

class MovieViewsAdapter(private val clickListener: (view: View, moviesViewItem: MoviesEntity) -> Unit) :
    PagingDataAdapter<MoviesEntity, MovieViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { item -> clickListener(it, item) }
        }
    }
}