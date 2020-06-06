package com.open.baselibrary.service

import android.util.Log
import androidx.annotation.CallSuper
import com.google.gson.GsonBuilder
import com.open.baselibrary.BuildConfig
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseBackendService :
    IBackendService {

    @Volatile
    var apiBaseUrl: String? = null
        private set(value) {
            field = value
        }
        get() = if (field.isNullOrBlank()) defaultBaseUrl else field

    private val urlChangedSubject = PublishSubject.create<String>()

    protected abstract val defaultBaseUrl: String

    override fun <S> createService(serviceClass: Class<S>): S =
        createService(serviceClass, emptyList())

    override fun <S> createService(serviceClass: Class<S>, interceptor: Interceptor): S =
        createService(serviceClass, listOf(interceptor))

    override fun <S> createService(serviceClass: Class<S>, interceptors: List<Interceptor>): S {
        val httpClient = OkHttpClient.Builder()

        interceptors.forEach { httpClient.addInterceptor(it) }
        setupHttpClient(httpClient)

        val client = httpClient.build()
        val retrofit = createBuilder().client(client).build()
        return retrofit.create(serviceClass)
    }

    @CallSuper
    protected open fun setupHttpClient(httpClient: OkHttpClient.Builder) {

    }

    override fun changeBaseUrl(url: String?) {
        apiBaseUrl = url
        urlChangedSubject.onNext(apiBaseUrl!!)
    }

    override val urlChangedObservable: Observable<String>
        get() = urlChangedSubject.hide()

    private fun createBuilder(): Retrofit.Builder {

        val gsonBuilder = GsonBuilder()
        setupGsonBuilder(gsonBuilder)
        Log.d("createBuilder", "createBuilder: ${apiBaseUrl}")
        val builder = Retrofit.Builder()
            .validateEagerly(BuildConfig.DEBUG)
            .baseUrl(apiBaseUrl!!)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))

        setupRetrofitBuilder(builder)

        return builder
    }

    protected open fun setupRetrofitBuilder(builder: Retrofit.Builder) {

    }

    protected open fun setupGsonBuilder(gsonBuilder: GsonBuilder) {

    }
}
