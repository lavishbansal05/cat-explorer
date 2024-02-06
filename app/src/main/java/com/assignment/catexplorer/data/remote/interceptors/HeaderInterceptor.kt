package com.assignment.catexplorer.data.remote.interceptors

import com.assignment.catexplorer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("x-api-key", BuildConfig.CATS_API_KEY)
                .build()
        )
    }
}