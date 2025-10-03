package com.marziehnourmohamadi.tornapos.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*

object FormatUtils {
    fun formatAmountTo12(amount: Long): String {
        return String.format(Locale.US, "%012d", amount)
    }

    fun getTimeNow(): String {
        val sdf = SimpleDateFormat("MMddHHmmss", Locale.US)
        return sdf.format(Date())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parsTime(input: String): String? {
        if (input.length != 10) return null
        return try {
            val month = input.substring(0, 2).toInt()
            val day   = input.substring(2, 4).toInt()
            val hour  = input.substring(4, 6).toInt()
            val minute= input.substring(6, 8).toInt()
            val second= input.substring(8, 10).toInt()

            val year = Year.now().value
            val ldt = LocalDateTime.of(year, month, day, hour, minute, second)

            val outFmt = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss")
            ldt.format(outFmt)
        } catch (e: NumberFormatException) {
            null
        } catch (e: DateTimeException) {
            null
        }
    }
}
