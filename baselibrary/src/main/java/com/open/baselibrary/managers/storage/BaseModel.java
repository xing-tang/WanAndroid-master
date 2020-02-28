package com.open.baselibrary.managers.storage;

public abstract class BaseModel {
    private transient IStorageService service;
    private int persistentModelVersion = 1;

    private boolean hasData = false;

    public BaseModel() {
        this(PreferencesStorageService.getInstance());
    }

    public BaseModel(IStorageService service) {
        this.service = service;
    }

    protected IStorageService getService() {
        return service;
    }

    protected void setService(IStorageService service) {
        this.service = service;
    }

    public boolean hasData() {
        return hasData;
    }

    public int getPersistentModelVersion() {
        return persistentModelVersion;
    }

    public int getModelClassVersion() {
        return 1;
    }

    public final void save() {
        hasData = true;
        persistentModelVersion = getModelClassVersion();
        service.save(this);
    }

    synchronized public final void clean() {
        onClean();
        hasData = false;
        service.save(this);
    }

    protected void onClean() {

    }

    public void load() {
        service.load(this);
    }

    public void onUpgrade() {

    }

    public void onModelUpgrade(int persistentModelVersion, int modelClassVersion) {

    }
}
