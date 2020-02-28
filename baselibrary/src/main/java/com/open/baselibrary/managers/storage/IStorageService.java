package com.open.baselibrary.managers.storage;

public interface IStorageService {
	<T extends BaseModel> boolean exists(Class<T> modelClass);

	<T extends BaseModel> void save(T model);

	<T extends BaseModel> T load(Class<T> modelClass);

	<T extends BaseModel> void load(T model);

	<T extends BaseModel> void clear(Class<T> model);
}
