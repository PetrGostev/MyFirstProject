package ru.petrgostev.myfirstproject.MoviesList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Movie

class MovieViewsAdapter(context: Context, private val clickListener: OnMovieClickListener) :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffUtilCallback()) {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(layoutInflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }

    interface OnMovieClickListener {
        fun onClick(movie: Movie)
    }
}