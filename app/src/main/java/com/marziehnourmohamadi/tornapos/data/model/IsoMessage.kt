package com.marziehnourmohamadi.tornapos.data.model

data class IsoMessage(
    val id: Long = System.currentTimeMillis(),
    val cardNumber: String,
    val amount: String,
    val stan: String,
    val timestamp: String,
    val fields: List<IsoField>,
    val completeMessage: String
)
