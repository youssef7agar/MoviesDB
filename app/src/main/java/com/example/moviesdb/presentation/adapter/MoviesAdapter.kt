package com.example.moviesdb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.AdapterMovieBinding
import com.example.moviesdb.presentation.model.MovieUiModel

class MoviesAdapter(
    private val onMovieClicked: (moviedId: Long) -> Unit
) : ListAdapter<MovieUiModel, MoviesAdapter.MovieViewHolder>(MoviesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            AdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MovieViewHolder(private val binding: AdapterMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieUiModel) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .fallback(R.drawable.default_image)
                .into(binding.posterImageView)
            binding.posterNameTextView.text = movie.title

            binding.root.setOnClickListener { onMovieClicked(movie.id) }
        }
    }
}