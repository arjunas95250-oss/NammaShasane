package com.mindmatrix.nammashasane.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mindmatrix.nammashasane.databinding.ItemShasanaBinding
import com.mindmatrix.nammashasane.model.Shasana

class ShasanaAdapter(private val onItemClick: (Shasana) -> Unit) :
    ListAdapter<Shasana, ShasanaAdapter.ShasanaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShasanaViewHolder {
        val binding = ItemShasanaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShasanaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShasanaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShasanaViewHolder(private val binding: ItemShasanaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shasana: Shasana) {
            binding.apply {
                tvTitle.text = shasana.title
                tvDynasty.text = shasana.dynasty
                tvPeriod.text = shasana.period
                tvLocation.text = "📍 ${shasana.location}"
                tvDamagedTag.visibility = if (shasana.isDamaged)
                    android.view.View.VISIBLE else android.view.View.GONE
                tvUserTag.visibility = if (shasana.isUserAdded)
                    android.view.View.VISIBLE else android.view.View.GONE
                root.setOnClickListener { onItemClick(shasana) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Shasana>() {
        override fun areItemsTheSame(oldItem: Shasana, newItem: Shasana) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Shasana, newItem: Shasana) = oldItem == newItem
    }
}
