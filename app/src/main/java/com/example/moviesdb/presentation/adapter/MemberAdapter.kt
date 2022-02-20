package com.example.moviesdb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.AdapterMemberBinding
import com.example.moviesdb.presentation.model.MemberUiModel

class MemberAdapter : ListAdapter<MemberUiModel, MemberAdapter.MemberViewHolder>(MemberDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding =
            AdapterMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MemberViewHolder(private val binding: AdapterMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: MemberUiModel) {

            Glide.with(binding.root.context)
                .load(member.photo)
                .error(R.drawable.default_person)
                .into(binding.memberPhotoImageView)

            binding.memberNameTextView.text = member.name
        }
    }
}