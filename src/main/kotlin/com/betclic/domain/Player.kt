package com.betclic.domain

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class Player(
    @BsonId
    val id: String? = null,
    val pseudo: String,
    var points: Int,
    var ranking: Int? = 0
)
