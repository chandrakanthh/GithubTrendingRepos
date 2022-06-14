package com.example.githubtrendingrepos.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuiltBy(
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("href")
    val href: String? = null,
    @SerialName("username")
    val username: String? = null
)