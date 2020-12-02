package ru.petrgostev.myfirstproject.moviesDetails.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Actor

class ActorViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.poster_none)
            .fallback(R.drawable.poster_none)
    }

    private val name: TextView = itemView.findViewById(R.id.actor_name)
    private val imageView: ImageView = itemView.findViewById(R.id.actor_image)

    fun onBind(actor: Actor) {
        name.text = actor.actorName

        Glide.with(itemView.context)
            .load(actor.actorImage)
            .apply(imageOption)
            .into(imageView)
    }
}