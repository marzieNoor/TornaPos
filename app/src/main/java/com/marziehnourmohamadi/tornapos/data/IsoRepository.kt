package com.marziehnourmohamadi.tornapos.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.marziehnourmohamadi.tornapos.R
import com.marziehnourmohamadi.tornapos.data.dataStore.StanManager
import com.marziehnourmohamadi.tornapos.data.model.IsoField
import com.marziehnourmohamadi.tornapos.data.model.IsoMessage
import com.marziehnourmohamadi.tornapos.utils.FormatUtils.formatAmountTo12
import com.marziehnourmohamadi.tornapos.utils.FormatUtils.getTimeNow
import com.marziehnourmohamadi.tornapos.utils.FormatUtils.parsTime
import org.jpos.iso.ISOMsg
import org.jpos.iso.packager.GenericPackager

class IsoRepository(private val context: Context) {
    suspend fun buildMessage(cardNumber: String, amount: String): Result<IsoMessage> {
        return try {

            val stanManager = StanManager(context = context)
            val stan = stanManager.getNextStan()

            System.setProperty(
                "org.xml.sax.driver",
                "org.xmlpull.v1.sax2.Driver"
            )
            val packager = GenericPackager(context.resources.openRawResource(R.raw.iso8583))

            val isoMsg = ISOMsg()
            isoMsg.packager = packager
            isoMsg.mti = "0200"
            isoMsg.set(2, cardNumber)

            val formattedAmount = formatAmountTo12(amount.toLong())
            isoMsg.set(4, formattedAmount)

            val transmissionDateTime = getTimeNow()
            isoMsg.set(7, transmissionDateTime)
            isoMsg.set(11, stan)


            val fields = listOf(
                IsoField("Field 2", "شماره کارت", cardNumber),
                IsoField("Field 4", "مبلغ: ${amount} ریال", formattedAmount),
                IsoField("Field 7", "تاریخ/زمان ارسال", transmissionDateTime),
                IsoField("Field 11", "شماره پیگیری", stan)
            )

            val packedMessage = String(isoMsg.pack())


            val message = IsoMessage(
                cardNumber = cardNumber,
                amount = amount,
                stan = stan,
                timestamp = fields.find { it.fieldNumber == "Field 7" }?.value ?: "",
                fields = fields,
                completeMessage = packedMessage
            )

            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}