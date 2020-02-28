package com.open.baselibrary.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import kotlin.Unit;


public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected CompositeDisposable onViewCreatedCompositeDisposable;
    protected CompositeDisposable onResumeCompositeDisposable;
    protected CompositeDisposable onCreateCompositeDisposable;
    private BehaviorSubject<Unit> onViewCreatedSubject = BehaviorSubject.create();
    // 标识当前Fragment是否可见
    protected Boolean mCurrentFragmentIsShow = false;

    protected void showLoading(String message) {
        if (isAdded()) {
            ((BaseActivity) getActivity()).showProgressDialog(message);
        }
    }

    protected void showLoading() {
        if (isAdded()) {
            ((BaseActivity) getActivity()).showProgressDialog();
        }
    }

    protected void hideLoading() {
        if (isAdded()) {
            ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    protected void showToast(@StringRes int resId) {
        if (isAdded()) {
            Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        if (onCreateCompositeDisposable != null) {
            onCreateCompositeDisposable.clear();
            onCreateCompositeDisposable = null;
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedCompositeDisposable = new CompositeDisposable();
        onViewCreatedSubject.onNext(Unit.INSTANCE);
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeCompositeDisposable = new CompositeDisposable();
        if (getUserVisibleHint() && !isHidden()) {
            mCurrentFragmentIsShow = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (onResumeCompositeDisposable != null) {
            onResumeCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (onViewCreatedCompositeDisposable != null) {
            onViewCreatedCompositeDisposable.clear();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mCurrentFragmentIsShow = isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mCurrentFragmentIsShow = !hidden;
    }

    protected void addOnViewCreatedSubscription(Disposable subscription) {
        if (onViewCreatedCompositeDisposable != null) {
            onViewCreatedCompositeDisposable.add(subscription);
        } else {
            throw new IllegalStateException("this method is supposed to be called in or after onViewCreated()");
        }
    }

    protected void removeOnViewCreatedSubscription(Disposable subscription) {
        if (onViewCreatedCompositeDisposable != null) {
            onViewCreatedCompositeDisposable.remove(subscription);
        } else {
            throw new IllegalStateException("onViewCreatedCompositeDisposable was not initialized");
        }
    }

    protected void addOnResumeViewSubscription(Disposable subscription) {
        onResumeCompositeDisposable.add(subscription);
    }

    protected void removeOnResumeViewSubscription(Disposable subscription) {
        onResumeCompositeDisposable.remove(subscription);
    }

    protected void addOnCreateSubscription(Disposable subscription) {
        if (onCreateCompositeDisposable != null) {
            onCreateCompositeDisposable.add(subscription);
        } else {
            throw new IllegalStateException("this method is supposed to be called in or after onCreate()");
        }
    }

    protected void removeOnCreateSubscription(Disposable subscription) {
        if (onCreateCompositeDisposable != null) {
            onCreateCompositeDisposable.remove(subscription);
        } else {
            throw new IllegalStateException("onCreateCompositeDisposable was not initialized");
        }
    }

    protected Observable<Unit> onViewCreatedObservable() {
        return onViewCreatedSubject.hide();
    }
}
