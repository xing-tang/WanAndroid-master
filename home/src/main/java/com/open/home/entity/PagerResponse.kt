package com.open.home.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class PagerResponse<T>(
    @SerializedName("curPage") val curPage: Int? = null,
    @SerializedName("datas") val datas: ArrayList<AriticleResponse>? = null,
    @SerializedName("offset") val offset: Int? = null,
    @SerializedName("over") val over: Boolean? = null,
    @SerializedName("pageCount") val pageCount: Int? = null,
    @SerializedName("size") val size: Int? = null,
    @SerializedName("total") val total: Int? = null
) : Serializable {
    fun isFirstPage(): Boolean {
        return curPage == 0
    }

    fun isLastPage(): Boolean {
        return (over == true) && (curPage == pageCount)
    }
}