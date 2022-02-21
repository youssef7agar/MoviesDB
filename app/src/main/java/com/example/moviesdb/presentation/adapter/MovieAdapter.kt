package com.example.moviesdb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.AdapterMovieOverviewBinding
import com.example.moviesdb.databinding.AdapterYearBinding
import com.example.moviesdb.presentation.model.MovieUiModel
import com.example.moviesdb.presentation.model.MultiViewItem
import com.example.moviesdb.presentation.model.YearUiModel

class MovieAdapter constructor(
    private val onMovieClicked: (movieId: Long) -> Unit
) : ListAdapter<MultiViewItem, RecyclerView.ViewHolder>(MultiViewItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            YEAR_VIEW -> {
                YearViewHolder(
                    binding = AdapterYearBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            MOVIE_VIEW -> {
                MovieItemViewHolder(
                    binding = AdapterMovieOverviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid type of view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovieUiModel -> MOVIE_VIEW
            is YearUiModel -> YEAR_VIEW
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is MovieItemViewHolder && item is MovieUiModel) {
            holder.bind(item)
        } else if (holder is YearViewHolder && item is YearUiModel) {
            holder.bind(item)
        }
    }

    inner class MovieItemViewHolder(private val binding: AdapterMovieOverviewBinding) :
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

    }

    inner class YearViewHolder(private val binding: AdapterYearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(year: YearUiModel) {
            binding.yearTextView.text = year.year
        }
    }

    private companion object {
        private const val YEAR_VIEW = 0
        private const val MOVIE_VIEW = 1
    }
}