package ru.petrgostev.myfirstproject.moviesDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Actor

class ActorViewsAdapter : ListAdapter<Actor, ActorViewHolder>(ActorDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}