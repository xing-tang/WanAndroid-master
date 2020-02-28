package com.open.baselibrary.mvp;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public interface IPresenterManager {
    /**
     * Presenter will be created with random id
     */
    <T extends Contract.Presenter> T getOrCreatePresenter(IPresenterFactory factory);

    <T extends Contract.Presenter> T getOrCreatePresenter(Bundle savedInstanceState, IPresenterFactory factory);

    <T extends Contract.Presenter> T getOrCreatePresenter(String id, IPresenterFactory factory, Boolean isRestoring);

    void destroyPresenter(@NonNull Contract.Presenter presenter);

    void onPause(FragmentActivity activity, @NonNull Contract.Presenter presenter);

    void onSaveInstanceState(Bundle outState, @NonNull Contract.Presenter presenter);
}
