package com.example.moviesdb.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesdb.presentation.model.MovieUiModel

object MoviesDiffUtil : DiffUtil.ItemCallback<MovieUiModel>() {

    override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem == newItem
    }
}