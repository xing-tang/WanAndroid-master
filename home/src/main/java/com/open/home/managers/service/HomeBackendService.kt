package com.open.home.managers.service

import com.open.baselibrary.service.BackendService
import com.open.baselibrary.service.IBackendService
import com.open.baselibrary.utils.HostUtils
import okhttp3.Interceptor
import java.util.*

class HomeBackendService : BackendService() {

    override val defaultBaseUrl: String
        get() = HostUtils.instance.APP_DOMAIN

    override fun <S> createService(serviceClass: Class<S>, interceptors: List<Interceptor>): S {
        val resultInterceptors = ArrayList<Interceptor>()
        resultInterceptors.addAll(interceptors)
        return super.createService(serviceClass, resultInterceptors)
    }

    companion object {
        @JvmStatic
        val instance: IBackendService by lazy { HomeBackendService() }
    }
}

