package com.example.moviesdb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.AdapterMovieOverviewBinding
import com.example.moviesdb.presentation.model.MovieUiModel

class MovieOverviewAdapter(
    private val onMovieClicked: (movieId: Long) -> Unit
) : ListAdapter<MovieUiModel, MovieOverviewAdapter.MovieOverviewViewHolder>(MoviesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieOverviewViewHolder {
        val binding =
            AdapterMovieOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieOverviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieOverviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MovieOverviewViewHolder(private val binding: AdapterMovieOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieUiModel) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .error(R.drawable.default_image)
                .into(binding.moviePosterImageView)
            binding.movieTitleTextView.text = movie.title
            binding.movieOverviewTextView.text = movie.overview
            binding.inWatchlistTextView.isVisible = movie.inWatchlist

            binding.root.setOnClickListener { onMovieClicked(movie.id) }
        }
    }}