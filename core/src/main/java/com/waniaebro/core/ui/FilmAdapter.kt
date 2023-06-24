package com.waniaebro.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waniaebro.core.databinding.FilmAdapterBinding
import com.waniaebro.core.domain.model.Film

class FilmAdapter(private val onClick: (View, Int) -> Unit) :
    ListAdapter<Film, FilmAdapter.ViewHolder>(FilmDiffCallback()) {
    class FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: FilmAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FilmAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = getItem(position)

        holder.binding.titleTv.text = film.title
        holder.binding.filmContainer.setOnClickListener {
            onClick(it, film.id)
        }
        Glide.with(holder.itemView).load(film.photoUrl).into(holder.binding.posterImage)
        if (film.isFavorite) {
            holder.binding.favorite.visibility = View.VISIBLE
        } else {
            holder.binding.favorite.visibility = View.GONE
        }
    }
}