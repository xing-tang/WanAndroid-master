package com.open.home.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AriticleResponse(
    @SerializedName("apkLink") val apkLink: String? = null,
    @SerializedName("audit") val audit: Int? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("canEdit") val canEdit: Boolean? = null,
    @SerializedName("chapterId") val chapterId: Int? = null,
    @SerializedName("chapterName") val chapterName: String? = null,
    @SerializedName("collect") val collect: Boolean? = null,
    @SerializedName("courseId") val courseId: Int? = null,
    @SerializedName("desc") val desc: String? = null,
    @SerializedName("descMd") val descMd: String? = null,
    @SerializedName("envelopePic") val envelopePic: String? = null,
    @SerializedName("fresh") val fresh: Boolean? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("link") val link: String? = null,
    @SerializedName("niceDate") val niceDate: String? = null,
    @SerializedName("niceShareDate") val niceShareDate: String? = null,
    @SerializedName("origin") val origin: String? = null,
    @SerializedName("prefix") val prefix: String? = null,
    @SerializedName("projectLink") val projectLink: String? = null,
    @SerializedName("publishTime") val publishTime: Long? = null,
    @SerializedName("selfVisible") val selfVisible: Int? = null,
    @SerializedName("shareDate") val shareDate: Long? = null,
    @SerializedName("shareUser") val shareUser: String? = null,
    @SerializedName("superChapterId") val superChapterId: Int? = null,
    @SerializedName("superChapterName") val superChapterName: String? = null,
    @SerializedName("tags") val tags: List<TagResponse>? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("userId") val userId: Int? = null,
    @SerializedName("visible") val visible: Int? = null,
    @SerializedName("zan") val zan: Int? = null
)