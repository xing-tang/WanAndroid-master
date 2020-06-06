package com.open.home.managers

import com.open.baselibrary.base.BaseResponse
import com.open.baselibrary.managers.BaseModelManager
import com.open.home.entity.AriticleResponse
import com.open.home.entity.BannerResponse
import com.open.home.entity.PagerResponse
import com.open.home.managers.api.IHomeApi
import com.open.home.managers.service.HomeBackendService
import io.reactivex.Observable

class HomeManager internal constructor() :
    BaseModelManager<HomeModel>(HomeModel::class.java), IHomeManager {

    val api: IHomeApi by lazy {
        HomeBackendService.instance.createService(IHomeApi::class.java)
    }

    override fun getBannerList(): Observable<BaseResponse<ArrayList<BannerResponse>>> {
        return api.getBanner();
    }

    override fun getAriticleList(page: Int): Observable<BaseResponse<PagerResponse<ArrayList<AriticleResponse>>>> {
        return api.getAritrilList(page)
    }


    companion object {
        @JvmStatic
        val INSTANCE: HomeManager by lazy { HomeManager() }
    }
}