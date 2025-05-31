package com.example.planter.data.api.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.UUID

class UUIDAdapter {
    @FromJson
    fun fromJson(value: String?): UUID? {
        return value?.let { UUID.fromString(it) }
    }

    @ToJson
    fun toJson(uuid: UUID?): String? {
        return uuid?.toString()
    }
} 