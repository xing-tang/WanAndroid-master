package com.open.home.mvp.contract

import com.open.baselibrary.mvp.Contract
import com.open.home.entity.AriticleResponse
import com.open.home.entity.BannerResponse
import com.open.home.entity.PagerResponse
import com.open.home.mvp.model.Model

class Contract {
    interface View : Contract.ViewWithModel<Model> {
        fun requestBannerSucces(banners: ArrayList<BannerResponse>)
        fun requestAritilSucces(ariticles: PagerResponse<ArrayList<AriticleResponse>>)
        fun requestAritilFaild(errorMsg: String)
    }

    interface Presenter : Contract.Presenter<View> {
        fun resetPage();
        fun getBannerList();
        fun getArticleList();
    }
}