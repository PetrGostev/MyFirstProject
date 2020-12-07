package ru.petrgostev.myfirstproject.moviesDetails.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Actor
import ru.petrgostev.myfirstproject.databinding.ViewHolderActorBinding

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.poster_none)
            .fallback(R.drawable.poster_none)
    }

    private val viewBinding = ViewHolderActorBinding.bind(itemView)

    fun onBind(actor: Actor) {
        viewBinding.actorName.text = actor.actorName

        Glide.with(itemView.context)
            .load(actor.actorImage)
            .apply(imageOption)
            .into(viewBinding.actorImage)
    }
}