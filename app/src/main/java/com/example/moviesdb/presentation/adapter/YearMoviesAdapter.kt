package com.example.moviesdb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.moviesdb.databinding.AdapterYearMovieBinding
import com.example.moviesdb.presentation.model.YearMovieUiModel

class YearMoviesAdapter(
    private val onMovieClicked: (movieId: Long) -> Unit
) : ListAdapter<YearMovieUiModel, YearMoviesAdapter.YearMovieViewHolder>(YearMoviesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearMovieViewHolder {
        val binding =
            AdapterYearMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YearMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class YearMovieViewHolder(private val binding: AdapterYearMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val moviesAdapter = MovieOverviewAdapter { movieId ->
            onMovieClicked(movieId)
        }

        fun bind(yearMovieUiModel: YearMovieUiModel) {
            binding.yearTextView.text = yearMovieUiModel.year
            binding.moviesRecyclerView.adapter = moviesAdapter
            moviesAdapter.submitList(yearMovieUiModel.movies)

            (binding.moviesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
        }
    }
}