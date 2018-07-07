package github.ouchadam.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import github.ouchadam.api.internal.auth.AuthApi
import github.ouchadam.api.internal.balance.ExportedBalanceService
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.api.AuthService
import github.ouchadam.modules.api.BalanceService
import github.ouchadam.modules.api.models.ClientCredentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.monzo.com"

class ExportedApiModule(
        private val retrofit: Retrofit,
        private val moshi: Moshi,
        private val headersProvider: HeadersProvider
) : ApiModule {

    companion object {

        fun create(tokenProvider: TokenProvider): ExportedApiModule {
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
            return ExportedApiModule(retrofit, moshi, HeadersProvider(tokenProvider))
        }
    }

    override fun auth(credentials: ClientCredentials): AuthService = AuthApi.from(retrofit, credentials)

    override fun balance(): BalanceService = ExportedBalanceService.from(retrofit, headersProvider)


}