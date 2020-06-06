package com.open.baselibrary.managers;

import com.open.baselibrary.managers.storage.BaseModel;
import com.open.baselibrary.managers.storage.IStorageService;
import com.open.baselibrary.managers.storage.PreferencesStorageService;

public abstract class BaseModelManager<T extends BaseModel> extends BaseManager {
    private T model;
    private Class<T> modelClass;
    private IStorageService storageService;

    public BaseModelManager(Class<T> modelClass, IStorageService storageService) {
        this.modelClass = modelClass;
        this.storageService = storageService;
    }

    public BaseModelManager(Class<T> modelClass) {
        this(modelClass, PreferencesStorageService.getInstance());
    }

    protected T getModel() {
        if (model == null) {
            model = storageService.load(modelClass);
        }
        return model;
    }
}
