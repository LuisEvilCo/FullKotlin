package Client

import gson
import Dto.BarbellUser
import Util.Exception.InternalErrorException
import Util.Exception.NotFoundException
import Util.Exception.UnauthorizedException
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object BarbellClient {

    private val Barbell_Client = OkHttpClient.Builder()
            .addInterceptor{
                val original = it.request()
                val request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build()
                it.proceed(request)
            }
            .build()!!

    fun fetchUser(endpoint: String): BarbellUser{
        return Request.Builder()
                .url(
                        HttpUrl.parse(endpoint).newBuilder()
                                .addPathSegment("fetchUser")
                                .build()
                )
                .build()
                .execute()
    }

    private inline fun <reified T> Request.execute(): T {
        val response = Barbell_Client.newCall(this).execute()
        val url = url().toString()
        val code = response.code()
        val stream = when (code) {
            200 -> response.body().charStream()
            401 -> {
                response.close()
                throw UnauthorizedException(url)
            }
            404 -> throw NotFoundException(url)
            else -> throw InternalErrorException("Unexpected response code $code for $url")
        }
        return gson.fromJson(stream, T::class.java).apply { stream.close() }
    }

}

