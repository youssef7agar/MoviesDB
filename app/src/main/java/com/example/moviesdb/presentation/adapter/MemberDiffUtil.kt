package com.example.moviesdb.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesdb.presentation.model.MemberUiModel

object MemberDiffUtil : DiffUtil.ItemCallback<MemberUiModel>() {

    override fun areItemsTheSame(oldItem: MemberUiModel, newItem: MemberUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MemberUiModel, newItem: MemberUiModel): Boolean {
        return oldItem == newItem
    }
}