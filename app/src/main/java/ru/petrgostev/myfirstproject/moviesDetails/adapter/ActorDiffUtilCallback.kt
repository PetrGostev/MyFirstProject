package ru.petrgostev.myfirstproject.moviesDetails.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.myfirstproject.data.Actor

class ActorDiffUtilCallback: DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}