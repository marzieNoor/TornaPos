package com.marziehnourmohamadi.tornapos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marziehnourmohamadi.tornapos.data.model.IsoMessage
import com.marziehnourmohamadi.tornapos.databinding.ItemMessageBinding

class IsoHistoryAdapter :ListAdapter<IsoMessage, IsoHistoryAdapter.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: IsoMessage) {
            binding.apply {
                mitText.text = " MIT: ${message.mit}"
                bitmapText.text = "BitMap: ${message.bitmap}"
                dataText.text = "data: ${message.data}"
                completeIsoText.text = message.completeMessage


                val fieldsText = message.fields.joinToString("\n") { field ->
                    "${field.fieldNumber} (${field.fieldName}): ${field.value}"
                }
                fieldsListText.text = fieldsText
            }
        }
    }
    class MessageDiffCallback : DiffUtil.ItemCallback<IsoMessage>() {
        override fun areItemsTheSame(oldItem: IsoMessage, newItem: IsoMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IsoMessage, newItem: IsoMessage): Boolean {
            return oldItem == newItem
        }
    }
}