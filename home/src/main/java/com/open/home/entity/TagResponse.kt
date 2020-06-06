package com.open.home.entity

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TagResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)