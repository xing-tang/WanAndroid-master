package com.open.baselibrary.mvp

import com.open.baselibrary.managers.platform.PlatformManager
import com.open.baselibrary.managers.platform.IPlatformManager


abstract class BaseActionPresenter<M : Any, T : Contract.ViewWithModel<M>>(
    protected val modelType: Class<M>,
    private val platformManager: IPlatformManager = PlatformManager.instance
) : BasePresenter<T>(), Contract.Presenter<T> {

    private var actionType1: ((T) -> Unit)? = null

    private var actionType2: ((T) -> Unit)? = null

    private var shouldClose: Boolean = false

    private var shouldShowContent: Boolean = false

    protected lateinit var model: M

    private var modelInitialized = false

    protected val RESPONSE_CODE_SUCCESS = 200
    protected val RESPONSE_CODE_500 = 500

    override final fun init(id: String, isRestoring: Boolean) {
        super.init(id, isRestoring)
        model = createModel()
        doInit(isRestoring)
    }

    open protected fun createModel(): M = modelType.newInstance()

    //该方法在创建Presenter对象时调用，以完成必要的初始化
    protected abstract fun doInit(isRestoring: Boolean)

    protected fun updateViewData(m: M) {
        model = m
        modelInitialized = true

        platformManager.runOnUiThread {
            if (view != null) {
                view!!.showContent(model)
            } else {
                shouldShowContent = true
            }
        }
    }

    protected fun updateViewActionType1(action: (view: T) -> Unit) =
        platformManager.runOnUiThread {
            if (view != null) {
                action(view!!)
            } else {
                actionType1 = action
            }
        }

    protected fun updateViewActionType2(action: (view: T) -> Unit) =
        platformManager.runOnUiThread {
            if (view != null) {
                action(view!!)
            } else {
                actionType2 = action
            }
        }

    protected fun closeView() =
        platformManager.runOnUiThread {
            if (view != null) {
                view!!.close()
            } else {
                shouldClose = true
            }
        }

    override fun attachView(view: T) {
        if (isViewAttached) {
            return
        }
        super.attachView(view)
        onSyncViewState()
    }

    private fun onSyncViewState() {
        if (shouldClose) {
            view!!.close()
        } else {
            if (shouldShowContent) {
                view!!.showContent(model)
                shouldShowContent = false
            }

            if (actionType1 != null) {
                actionType1!!(view!!)
                actionType1 = null
            }

            if (actionType2 != null) {
                actionType2!!(view!!)
                actionType2 = null
            }
        }
    }

    override fun syncViewOnAttach() {
        if (modelInitialized) {
            shouldShowContent = true
        }
    }
}
