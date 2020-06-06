package com.open.baselibrary.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.open.baselibrary.R
import com.open.baselibrary.mvp.BaseActionPresenter
import com.open.baselibrary.mvp.Contract
import com.open.baselibrary.mvp.IPresenterFactory
import com.open.baselibrary.mvp.PresenterManager
import com.open.baselibrary.view.dialogs.ProgressDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActionPresenterFragment<M : Any, V : Contract.ViewWithModel<M>, P : BaseActionPresenter<M, V>> :
    BaseFragment(), Contract.ViewWithModel<M> {
    private val presenterManager = PresenterManager.getInstance()
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = presenterManager.getOrCreatePresenter<P>(savedInstanceState, presenterFactory)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedCompositeDisposable = CompositeDisposable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (onViewCreatedCompositeDisposable != null) {
            onViewCreatedCompositeDisposable!!.clear()
        }
    }

    protected abstract val presenterFactory: IPresenterFactory<P>

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenterManager.onSaveInstanceState(outState, presenter)
    }

    @JvmOverloads
    protected fun showProgressDialog(message: String? = null, isInCommunity: Boolean = false) {
        val progressDialogFragment =
            childFragmentManager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialogFragment
        if (progressDialogFragment == null) {
            val fragment = if (TextUtils.isEmpty(message))
                ProgressDialogFragment.newInstance()
            else
                ProgressDialogFragment.newInstance(message, isInCommunity)
            fragment.show(childFragmentManager, PROGRESS_DIALOG_TAG)
        }
    }

    private fun hideProgressDialog() {
        val progressDialogFragment =
            childFragmentManager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialogFragment
        progressDialogFragment?.dismissAllowingStateLoss()
    }

    fun hideRoutingLoadingDialog() {
        val progressDialogFragment =
            childFragmentManager.findFragmentByTag(ROUTING_ACTIVITY_DIALOG_TAG) as? ProgressDialogFragment
        progressDialogFragment?.dismissAllowingStateLoss()
    }

    override fun addOnViewCreatedSubscription(subscription: Disposable?) {
        if (onViewCreatedCompositeDisposable != null) {
            if (subscription != null) {
                onViewCreatedCompositeDisposable!!.add(subscription)
            }
        } else {
            throw IllegalStateException("this method is supposed to be called in or after onViewCreated()")
        }
    }

    override fun removeOnViewCreatedSubscription(subscription: Disposable?) {
        if (onViewCreatedCompositeDisposable != null) {
            if (subscription != null) {
                onViewCreatedCompositeDisposable!!.remove(subscription)
            }
        } else {
            throw IllegalStateException("onViewCreatedCompositeDisposable was not initialized")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this as V)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
        presenterManager.onPause(requireActivity(), presenter)
    }

    override fun close() {
        activity?.finish()
    }

    override fun showLoading() {
        showProgressDialog(getString(R.string.loading))
    }

    fun showLoadingInCommunity() {
        showProgressDialog(getString(R.string.loading), true)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showContent(model: M) {}

    companion object {
        private const val PROGRESS_DIALOG_TAG = "com.open.wanandroid.view.base.progress"
        private const val ROUTING_ACTIVITY_DIALOG_TAG = "progressDialog"
    }
}
