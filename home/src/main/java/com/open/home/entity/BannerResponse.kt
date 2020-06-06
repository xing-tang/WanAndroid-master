package com.open.home.entity

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

/**
 * 轮播图
 */
@Keep
data class BannerResponse(
    @SerializedName("desc") val desc: String? = null, // 享学~
    @SerializedName("id") val id: Int? = null, // 29
    @SerializedName("imagePath") val imagePath: String? = null, // https://wanandroid.com/blogimgs/6723ca73-bbc2-4b2a-9538-4c36df6edf56.png
    @SerializedName("isVisible") val isVisible: Int? = null, // 1
    @SerializedName("order") val order: Int? = null, // 0
    @SerializedName("title") val title: String? = null, // 可能是目前超全的《Android面试题及解析》（379页）
    @SerializedName("type") val type: Int? = null, // 0
    @SerializedName("url") val url: String? = null // https://mp.weixin.qq.com/s/NOjBZuLlcfMd3FbvfK3S9w
)
