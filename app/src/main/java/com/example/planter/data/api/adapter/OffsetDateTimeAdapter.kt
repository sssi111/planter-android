package com.example.planter.data.api.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Moshi adapter for converting between OffsetDateTime and JSON String
 */
class OffsetDateTimeAdapter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun fromJson(value: String?): OffsetDateTime? {
        return value?.let {
            return OffsetDateTime.parse(it, formatter)
        }
    }

    @ToJson
    fun toJson(value: OffsetDateTime?): String? {
        return value?.format(formatter)
    }
}