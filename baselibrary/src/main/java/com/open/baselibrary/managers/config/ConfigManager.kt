package com.open.baselibrary.managers.config

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.Interceptor

class ConfigManager {

    lateinit var config: Config
    lateinit var app: Application

    fun init(config: Config, app: Application) {
        this.config = config
        this.app = app
    }

    companion object {
        @JvmStatic
        val instance: ConfigManager by lazy { ConfigManager() }
    }
}

open class Config(
        val salt: String,
        val versionName: String,
        val backendHost: String,
        val apiToWrapperMap: Map<Class<*>, Lazy<*>>?,
        val gsonBuilderAction: ((GsonBuilder) -> Unit)?,
        val migrationInstallationId: String?,
        val interceptors: List<Interceptor>) {

    private constructor(builder: Builder) : this(
            builder.salt,
            builder.versionName,
            builder.backendHost,
            builder.apiToWrapperMap,
            builder.gsonBuilderAction,
            builder.migrationInstallationId,
            builder.interceptors)

    class Builder(val salt: String, val versionName: String, val backendHost: String) {

        internal var apiToWrapperMap: Map<Class<*>, Lazy<*>>? = null
            private set

        internal var gsonBuilderAction: ((GsonBuilder) -> Unit)? = null
            private set

        internal var migrationInstallationId: String? = null
            private set

        internal val interceptors = mutableListOf<Interceptor>()

        fun apiToWrapperMap(apiToWrapperMap: Map<Class<*>, Lazy<*>>) = apply { this.apiToWrapperMap = apiToWrapperMap }
        fun gsonBuilderAction(gsonBuilderAction: ((GsonBuilder) -> Unit)) = apply { this.gsonBuilderAction = gsonBuilderAction }
        fun migrationInstallationId(migrationInstallationId: String) = apply { this.migrationInstallationId = migrationInstallationId }
        fun addOkHttpInterceptor(interceptor: Interceptor) = apply { this.interceptors.add(interceptor) }
        fun build() = Config(this)
    }

}