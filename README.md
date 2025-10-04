A simple Android app for building and parsing ISO8583 financial messages, built with Kotlin, MVVM, and StateFlow.
apk file is put in repository. download it and enjoy! 

🚀 Features

🏗️ Build ISO8583 messages (MTI 0200)

🔍 Parse existing ISO8583 messages

🧾 View message history in ListAdapter

⚙️ Clean MVVM architecture with Repository pattern

🔄 Reactive Programing using StateFlow

✅ Input validation & error handling

🧠 Tech Stack

Language: Kotlin

Architecture: MVVM + Repository

UI Layer: ViewBinding, RecyclerView

Reactive State: StateFlow / MutableStateFlow

ISO Handling: jPOS Packager

🧮 STAN Logic

Each transaction gets a unique incremental STAN (field 11).
It’s managed locally using DataStore:

The StanManager class stores the last used STAN.For each new ISO message:

        val next = if (current >= 999_999) 1 else current + 1

After 999999, it automatically resets to 000001.

🧮 iso building example
input: for card number= 6274123456789012  and price= 500
output: 020052200000000000001662741234567890120000000005001004233444000031

🧮 iso parsing example
input: 020052200000000000001662741234567890120000000005001004233444000031
output: 
MTI: Message Type Indicator
0200

Field 2: شماره کارت
6274123456789012

Field 4: مبلغ تراکنش
000000000500

Field 7: تاریخ/زمان
10/04 23:34:44

Field 11: شماره پیگیری
000031

