package com.open.baselibrary.managers.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.google.gson.JsonSyntaxException
import com.open.baselibrary.managers.platform.PlatformManager
import java.util.*

open class PreferencesStorageService protected constructor(
    sharedPreferencesName: String,
    protected val applicationContext: Context = PlatformManager.instance.applicationContext
) : IStorageService {

    open protected val preferences: SharedPreferences =
        applicationContext.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    private fun createGson() = createGsonBuilder().create()

    override fun <T : BaseModel> exists(modelClass: Class<T>) =
        preferences.getString(modelClass.name, "")!!.isNotEmpty()

    override fun <T : BaseModel> save(model: T) {
        val gson = createGson()
        val modelStr = gson.toJson(model)
        preferences.edit().putString(model.javaClass.name, modelStr).apply()
    }

    fun commit() {
        preferences.edit().commit()
    }

    override fun <T : BaseModel> load(modelClass: Class<T>): T? {
        val modelStr = preferences.getString(modelClass.name, "")
        var loadedModel: T?
        if (modelStr!!.isNotEmpty()) {
            val gson = createGson()
            try {
                loadedModel = gson.fromJson(modelStr, modelClass)
                if (loadedModel.persistentModelVersion != loadedModel.modelClassVersion) {
                    loadedModel.onModelUpgrade(
                        loadedModel.persistentModelVersion,
                        loadedModel.modelClassVersion
                    )
                }
            } catch (e: JsonSyntaxException) {
                loadedModel = makeNewInstance(modelClass)
                e.printStackTrace()
            } catch (e: IncompatibleClassChangeError) {
                // workaround for a known samsung bug in android-L
                // links to track issue:
                // https://issuetracker.google.com/issues/37045084
                // https://github.com/google/gson/issues/726
                loadedModel = makeNewInstance(modelClass)
                e.printStackTrace()
            } catch (e: Throwable) {
                loadedModel = makeNewInstance(modelClass)
                e.printStackTrace()
            }

        } else {
            loadedModel = makeNewInstance(modelClass)
        }

        if (loadedModel != null) {
            loadedModel.service = this
        }

        return loadedModel
    }

    private fun <T : BaseModel> makeNewInstance(modelClass: Class<T>): T? {
        try {
            val loadedModel = modelClass.newInstance()
            loadedModel.onUpgrade()
            return loadedModel
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return null
        }

    }

    override fun <T : BaseModel> load(model: T) {
        val modelStr = preferences.getString(model.javaClass.name, "")
        if (!modelStr!!.isEmpty()) {
            createGsonBuilder().registerTypeAdapter(model.javaClass, InstanceCreator { model })
                .create().fromJson<BaseModel>(modelStr, model.javaClass)

            if (model.persistentModelVersion != model.modelClassVersion) {
                model.onModelUpgrade(model.persistentModelVersion, model.modelClassVersion)
            }
        } else {
            model.onUpgrade()
        }
    }

    override fun <T : BaseModel> clear(modelClass: Class<T>) =
        preferences.edit().putString(modelClass.name, "").apply()

    private fun createGsonBuilder() = GsonBuilder()
        .enableComplexMapKeySerialization()
        .registerTypeAdapter(Date::class.java, DateTypeAdapter())

    companion object {
        @JvmStatic
        val instance: IStorageService by lazy { PreferencesStorageService(PreferencesStorageService::class.java.name) }

        @JvmStatic
        fun getInstance(preferenceName: String): PreferencesStorageService {
            return PreferencesStorageService(preferenceName)
        }
    }
}
