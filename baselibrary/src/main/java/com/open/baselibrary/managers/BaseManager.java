package com.open.baselibrary.managers;

import android.app.Application;
import android.content.Context;

import com.open.baselibrary.managers.config.ConfigManager;


public abstract class BaseManager implements IManager {

    protected Context getContext() {
        return ConfigManager.getInstance().app;
    }

    protected Application getApplication() {
        return ConfigManager.getInstance().app;
    }

    @Override
    public void initialize() {

    }
}
