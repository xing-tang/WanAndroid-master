package com.open.baselibrary.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PresenterManager implements IPresenterManager {
    private static final String PRESENTER_ID_KEY = "com.sixdays.truckerpath.managers.mvp.presenter_key";
    private static PresenterManager ourInstance = new PresenterManager();

    private Map<String, BasePresenter> presenters = new HashMap<>();

    private PresenterManager() {
    }

    public static IPresenterManager getInstance() {
        return ourInstance;
    }

    @Override
    public <T extends Contract.Presenter> T getOrCreatePresenter(IPresenterFactory factory) {
        return getOrCreatePresenter(null, factory);
    }

    @Override
    public <T extends Contract.Presenter> T getOrCreatePresenter(Bundle savedInstanceState, IPresenterFactory factory) {
        String id = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(PRESENTER_ID_KEY)) {
            id = savedInstanceState.getString(PRESENTER_ID_KEY);
        }

        if (id == null && savedInstanceState != null) {
            throw new IllegalStateException("no presenter id in savedInstanceState");
        }

        return getOrCreatePresenter(id, factory, savedInstanceState != null);
    }

    @Override
    public <T extends Contract.Presenter> T getOrCreatePresenter(String id, IPresenterFactory factory, Boolean isRestoring) {

        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        BasePresenter presenter = presenters.get(id);
        if (presenter == null) {
            presenter = factory.createPresenter();
            presenter.init(id, isRestoring);
            presenters.put(id, presenter);
        } else {
            presenter.syncViewOnAttach();
        }

        //noinspection unchecked
        return (T) presenter;
    }

    @Override
    public void destroyPresenter(@NonNull Contract.Presenter presenter) {
        presenters.remove(presenter.getId());
        presenter.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, @NonNull Contract.Presenter presenter) {
        outState.putString(PRESENTER_ID_KEY, presenter.getId());
    }

    @Override
    public void onPause(FragmentActivity activity, @NonNull Contract.Presenter presenter) {
        if (activity.isFinishing() && !activity.isChangingConfigurations()) {
            destroyPresenter(presenter);
        }
    }
}
