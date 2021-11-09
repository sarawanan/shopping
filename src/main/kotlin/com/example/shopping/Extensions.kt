package com.example.shopping

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder

fun LocalDateTime.format(): String = this.format(
    DateTimeFormatterBuilder()
        .appendPattern("dd-MMM-yyyy")
        .toFormatter()
)