package com.open.home.managers.api

import com.open.baselibrary.base.BaseResponse
import com.open.home.entity.AriticleResponse
import com.open.home.entity.BannerResponse
import com.open.home.entity.PagerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface IHomeApi {

    /**
     * 获取banner数据
     */
    @GET("/banner/json")
    fun getBanner(): Observable<BaseResponse<ArrayList<BannerResponse>>>

    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    fun getAritrilList(@Path("page") page: Int): Observable<BaseResponse<PagerResponse<ArrayList<AriticleResponse>>>>


}