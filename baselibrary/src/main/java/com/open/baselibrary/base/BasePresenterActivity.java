package com.open.baselibrary.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.open.baselibrary.R;
import com.open.baselibrary.mvp.BasePresenter;
import com.open.baselibrary.mvp.Contract;
import com.open.baselibrary.mvp.IPresenterFactory;
import com.open.baselibrary.mvp.IPresenterManager;
import com.open.baselibrary.mvp.PresenterManager;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

@SuppressLint("Registered")
public abstract class BasePresenterActivity<P extends BasePresenter<V>, V extends Contract.View> extends BaseActivity implements Contract.View, AnimatedEnterExitAvailable {

    private IPresenterManager presenterManager = PresenterManager.getInstance();
    @NonNull
    private P presenter;

    protected IPresenterManager getPresenterManager() {
        return presenterManager;
    }

    @NonNull
    protected P getPresenter() {
        return presenter;
    }

    @NotNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = getPresenterManager().getOrCreatePresenter(savedInstanceState, getPresenterFactory());
        super.onCreate(savedInstanceState);
    }

    protected abstract IPresenterFactory<P> getPresenterFactory();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterManager.onSaveInstanceState(outState, presenter);
    }

    protected void addOnCreateSubscription(Disposable subscription) {
        onCreateCompositeDisposable.add(subscription);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();
        getPresenter().attachView((V) this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().detachView();
        presenterManager.onPause(this, presenter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionDefault();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog(getString(R.string.loading));
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

}
