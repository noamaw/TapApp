package com.noam.tapapp.data.api

import android.util.Log
import com.noam.tapapp.utils.getYoutubeUrls
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.Charset


internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("TAG", "intercept: intercepting the request")
        val originalRequest: Request = chain.request()
        val response: Response = chain.proceed(originalRequest)

        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer
        val responseBodyString = buffer?.clone()?.readString(Charset.forName("UTF-8"))
//        val responseBodyString = response.peekBody(Long.MAX_VALUE).string()
        if (response.body != null) {
            Log.d("TAG", "intercept: response body is non null")
//            val byteResponse = response.body?.bytes()
            Log.d("TAG", "intercept: converted the response to bytes $responseBodyString")
//            val string = byteResponse?.toString(Charsets.UTF_8)
//            Log.d("TAG", "intercept: converted the response to string $string")
            var resultString = "Error"
//            if (string != null) {
//               resultString = getYoutubeUrls(responseBodyString)
//            }

            resultString.toByteArray().toResponseBody()
        }
        return response
    }
}
