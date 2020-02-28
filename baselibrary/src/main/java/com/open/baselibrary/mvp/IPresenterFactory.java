package com.open.baselibrary.mvp;

public interface IPresenterFactory<T extends BasePresenter> {
    T createPresenter();
}
