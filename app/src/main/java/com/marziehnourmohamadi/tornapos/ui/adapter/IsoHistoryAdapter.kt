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
                messageNumberText.text = "پیام ${message.stan}"
                cardNumberText.text = "کارت: ${message.cardNumber}"
                amountText.text = "مبلغ: ${message.amount} ریال"
                stanText.text = "STAN: ${message.stan}"
                timestampText.text = "زمان: ${message.timestamp}"
                completeIsoText.text = message.completeMessage


                val fieldsText = message.fields.joinToString("\n") { field ->
                    "${field.fieldNumber}: ${field.value}"
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