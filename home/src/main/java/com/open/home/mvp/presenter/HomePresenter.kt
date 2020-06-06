package com.open.home.mvp.presenter

import com.open.baselibrary.mvp.BaseActionPresenter
import com.open.home.managers.HomeManager
import com.open.home.managers.IHomeManager
import com.open.home.mvp.contract.Contract
import com.open.home.mvp.model.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomePresenter(private val homeManager: IHomeManager = HomeManager.INSTANCE) :
    BaseActionPresenter<Model, Contract.View>(Model::class.java),
    Contract.Presenter {

    override fun doInit(isRestoring: Boolean) = Unit

    override fun resetPage() {
        model.curPage = 0;
    }

    override fun getBannerList() {
        addSubscription(homeManager.getBannerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { updateViewActionType1(Contract.View::hideLoading) }
            .doOnError { updateViewActionType1(Contract.View::hideLoading) }
            .subscribe({ response ->
                if (response.isSuccessful()) {
                    updateViewActionType1 { it.requestBannerSucces(response.data) }
                }
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun getArticleList() {
        addSubscription(homeManager.getAriticleList(model.curPage++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { updateViewActionType1(Contract.View::hideLoading) }
            .doOnError { updateViewActionType1(Contract.View::hideLoading) }
            .subscribe({ response ->
                if (response.isSuccessful()) {
                    updateViewActionType1 { it.requestAritilSucces(response.data) }
                } else {
                    updateViewActionType1 { it.requestAritilFaild(response.errorMsg) }
                }
            }, { throwable ->
                throwable.printStackTrace()
                updateViewActionType1 { it.requestAritilFaild(throwable.message ?: "") }
            })
        )
    }


}
