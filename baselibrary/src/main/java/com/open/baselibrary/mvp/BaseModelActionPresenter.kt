package com.open.baselibrary.mvp

import com.open.baselibrary.managers.storage.BaseModel
import com.open.baselibrary.managers.storage.IStorageService
import com.open.baselibrary.managers.storage.PreferencesStorageService

abstract class BaseModelActionPresenter<M : BaseModel, V : Contract.ViewWithPersistentModel<M>>(
    modelType: Class<M>,
    private val storageService: IStorageService = PreferencesStorageService.instance,
    private val cleanModelOnDestroy: Boolean = true
) : BaseActionPresenter<M, V>(modelType), Contract.Presenter<V> {

    override final fun createModel(): M = storageService.load(modelType)

    protected fun syncAndSaveModel() {
        view?.updateModelFromView(model)
        model.save()
    }

    override fun detachView() {
        syncAndSaveModel()
        super.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cleanModelOnDestroy) {
            storageService.clear(modelType)
        }
    }
}