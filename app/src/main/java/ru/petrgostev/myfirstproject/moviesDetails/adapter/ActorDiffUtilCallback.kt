package ru.petrgostev.myfirstproject.moviesDetails.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.data.Actor

class ActorDiffUtilCallback: DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.actorName == newItem.actorName
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}