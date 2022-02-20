package com.example.moviesdb.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesdb.presentation.model.YearMovieUiModel

object YearMoviesDiffUtil : DiffUtil.ItemCallback<YearMovieUiModel>() {

    override fun areItemsTheSame(oldItem: YearMovieUiModel, newItem: YearMovieUiModel): Boolean {
        return oldItem.year == newItem.year
    }

    override fun areContentsTheSame(oldItem: YearMovieUiModel, newItem: YearMovieUiModel): Boolean {
        return oldItem == newItem
    }
}