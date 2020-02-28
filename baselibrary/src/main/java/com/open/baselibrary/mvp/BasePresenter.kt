package com.open.baselibrary.mvp

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BasePresenter<T : Contract.View> : Contract.Presenter<T> {

    protected var view: T? = null
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var viewAttachedCompositeDisposable: CompositeDisposable? = null
    final override lateinit var id: String
        private set

    var initialized = false
        private set

    @CallSuper
    override fun init(id: String, isRestoring: Boolean) {
        this.id = id
        this.initialized = true
    }

    override fun attachView(view: T) {
        viewAttachedCompositeDisposable = CompositeDisposable()
        this.view = view
    }

    override fun detachView() {
        this.view = null
        viewAttachedCompositeDisposable?.clear()
        viewAttachedCompositeDisposable = null
    }


    abstract fun syncViewOnAttach()

    override fun onDestroy() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }

    protected fun addSubscription(subscription: Disposable) = compositeDisposable?.add(subscription)

    protected fun addOnViewAttachedCompositeDisposable(subscription: Disposable) {
        // todo dedicated method in base class not to forget to add sub
        if (view == null) {
            throw IllegalStateException("view should be not null")
        }
        viewAttachedCompositeDisposable?.add(subscription)
    }

    val isViewAttached: Boolean
        get() = view != null
}
