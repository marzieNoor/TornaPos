package com.marziehnourmohamadi.tornapos.data.model

data class IsoMessage(
    val id: Long = System.currentTimeMillis(),
    val mit: String,
    val bitmap: String,
    val data: String,
    val fields: List<IsoField>,
    val completeMessage: String
)
