package ru.petrgostev.myfirstproject.MoviesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Movie

class MovieViewsAdapter(private val clickListener: (movie: Movie) -> Unit) :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener(getItem(position))
        }
    }
}