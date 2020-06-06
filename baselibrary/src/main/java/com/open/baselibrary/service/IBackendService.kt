package com.open.baselibrary.service

import io.reactivex.Observable
import okhttp3.Interceptor

interface IBackendService {
    fun <S> createService(serviceClass: Class<S>): S
    fun <S> createService(serviceClass: Class<S>, interceptor: Interceptor): S
    fun <S> createService(serviceClass: Class<S>, interceptors: List<Interceptor> = emptyList()): S

    /**
     * Change base api url

     * @param url, if url is null base api url will be reset to default one
     */
    fun changeBaseUrl(url: String?)

    val urlChangedObservable: Observable<String>
}
