package github.ouchadam.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import github.ouchadam.api.internal.auth.AuthApi
import github.ouchadam.api.models.ClientCredentials
import retrofit2.Retrofit

class ApiModule(
        private val retrofit: Retrofit,
        private val moshi: Moshi
) {

    companion object {

        fun create(): ApiModule {
            val retrofit = Retrofit.Builder()
                    .build()
            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            return ApiModule(retrofit, moshi)
        }
    }

    fun auth(credentials: ClientCredentials): Auth = AuthApi.from(retrofit, credentials)

}