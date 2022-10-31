package com.samfonsec.fuzecs.data.api

import android.content.Context
import com.samfonsec.fuzecs.R
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTH_KEY, AUTH_TYPE + context.getString(R.string.auth_key))
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val AUTH_KEY = "Authorization"
        private const val AUTH_TYPE = "Bearer "
    }
}