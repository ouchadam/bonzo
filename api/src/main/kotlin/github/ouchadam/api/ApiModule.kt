package github.ouchadam.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import github.ouchadam.api.internal.auth.AuthApi
import github.ouchadam.api.models.ClientCredentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.monzo.com"

class ApiModule(
        private val retrofit: Retrofit,
        private val moshi: Moshi
) {

    companion object {

        fun create(): ApiModule {
            val okHttpClient = OkHttpClient()
            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return ApiModule(retrofit, moshi)
        }
    }

    fun auth(credentials: ClientCredentials): Auth = AuthApi.from(retrofit, credentials)

}