package com.open.baselibrary.service


import com.google.gson.GsonBuilder
import com.open.baselibrary.managers.config.Config
import com.open.baselibrary.managers.config.ConfigManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.*

open class BackendService(
    private val config: Config = ConfigManager.instance.config
    ) : BaseBackendService() {


    private val headerInterceptor = Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("client", "WanAndroid")
                .addHeader("content-type", "application/json")
                .build()
        chain.proceed(request)
    }

    override val defaultBaseUrl: String
        get() = config.backendHost

    override fun <S> createService(serviceClass: Class<S>, interceptor: Interceptor): S {
        return createService(serviceClass, listOf(interceptor))
    }


    override fun <S> createService(serviceClass: Class<S>, interceptors: List<Interceptor>): S {
        val resultInterceptors = ArrayList<Interceptor>()
        resultInterceptors.addAll(interceptors)
        return super.createService(serviceClass, resultInterceptors)
    }

    override fun setupHttpClient(httpClient: OkHttpClient.Builder) {
        super.setupHttpClient(httpClient)

        httpClient.addInterceptor(headerInterceptor)

        config.interceptors.forEach { httpClient.addInterceptor(it) }
    }

    companion object {

        const val PAGE_LIMIT: Int = 100
        const val REVIEW_TEXT_MAX_LENGTH: Int = 2500
        const val INCORRECT_PLACE_TEXT_MAX_LENGTH: Int = 2500

        @JvmStatic
        val instance: IBackendService by lazy { BackendService() }

        val platform = "Android"
        val appName = "tp"
        const val APPLICATION: String = "tpp"
        const val PLATFORM: String = "android"
    }

    override fun setupGsonBuilder(gsonBuilder: GsonBuilder) {
        super.setupGsonBuilder(gsonBuilder)
        config.gsonBuilderAction?.invoke(gsonBuilder)
    }
}
