package com.open.home.managers

import com.open.baselibrary.base.BaseResponse
import com.open.home.entity.AriticleResponse
import com.open.home.entity.BannerResponse
import com.open.home.entity.PagerResponse
import io.reactivex.Observable

interface IHomeManager {

    fun getBannerList(): Observable<BaseResponse<ArrayList<BannerResponse>>>


    fun getAriticleList(page:Int): Observable<BaseResponse<PagerResponse<ArrayList<AriticleResponse>>>>
}