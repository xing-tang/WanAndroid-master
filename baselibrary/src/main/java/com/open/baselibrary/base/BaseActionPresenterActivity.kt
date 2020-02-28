package com.open.baselibrary.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.open.baselibrary.BuildConfig
import com.open.baselibrary.R
import com.open.baselibrary.mvp.BaseActionPresenter
import com.open.baselibrary.mvp.Contract
import com.open.baselibrary.mvp.IPresenterFactory
import com.open.baselibrary.mvp.PresenterManager
import com.open.baselibrary.view.dialogs.OnCancelListener

@SuppressLint("Registered")
abstract class BaseActionPresenterActivity<M : Any, V : Contract.ViewWithModel<M>, P : BaseActionPresenter<M, V>> :
    BaseActivity(), Contract.ViewWithModel<M>, AnimatedEnterExitAvailable {

    private val presenterManager = PresenterManager.getInstance()
    protected lateinit var presenter: P
    override fun getContext(): Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.DEBUG) {
            Log.i("onCreate", this.toString())
        }
        presenter = presenterManager.getOrCreatePresenter(savedInstanceState, presenterFactory)
        super.onCreate(savedInstanceState)
    }

    protected abstract val presenterFactory: IPresenterFactory<P>

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenterManager.onSaveInstanceState(outState, presenter)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    public override fun onResume() {
        super.onResume()
        presenter.attachView(this as V)
    }

    public override fun onPause() {
        super.onPause()
        presenter.detachView()
        presenterManager.onPause(this, presenter)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun close() {
        finish()
    }

    override fun showLoading() {
        showProgressDialog(getString(R.string.loading))
    }

    fun showLoadingInCommunity() {
        showProgressDialog(getString(R.string.loading), false)
    }

    fun showLoadingInCommunityInPostDetail(onCancelListener: OnCancelListener) {
        showProgressDialog(getString(R.string.loading), true, onCancelListener)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showContent(model: M) {}

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object {

        const val IN_APP_BILLING_REQUEST_CODE = 1003

        private const val PROGRESS_DIALOG_TAG = "progressDialog"

    }
}
