package com.example.moviesdb.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesdb.presentation.model.MovieUiModel
import com.example.moviesdb.presentation.model.MultiViewItem
import com.example.moviesdb.presentation.model.YearUiModel

object MultiViewItemDiffUtil : DiffUtil.ItemCallback<MultiViewItem>() {

    override fun areItemsTheSame(oldItem: MultiViewItem, newItem: MultiViewItem): Boolean {
        return if (oldItem is MovieUiModel && newItem is MovieUiModel) {
            oldItem.id == newItem.id
        } else if (oldItem is YearUiModel && newItem is YearUiModel) {
            oldItem == newItem
        } else false
    }

    override fun areContentsTheSame(oldItem: MultiViewItem, newItem: MultiViewItem): Boolean {
        return if (oldItem is MovieUiModel && newItem is MovieUiModel) {
            oldItem == newItem
        } else if (oldItem is YearUiModel && newItem is YearUiModel) {
            oldItem == newItem
        } else false
    }
}