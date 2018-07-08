package github.ouchadam.api.internal.balance

import github.ouchadam.api.internal.HeadersProvider
import github.ouchadam.modules.api.BalanceService
import retrofit2.Retrofit

class ExportedBalanceService(
        private val balanceEndpoint: BalanceEndpoint,
        private val headersProvider: HeadersProvider
) : BalanceService {

    companion object {

        fun from(retrofit: Retrofit, headersProvider: HeadersProvider): BalanceService {
            val endpoint = retrofit.newBuilder()
                    .build()
                    .create(BalanceEndpoint::class.java)
            return ExportedBalanceService(endpoint, headersProvider)
        }

    }

    override fun readBalance(accountId: String) = balanceEndpoint.getBalance(
            headersProvider.authenticatedHeaders(),
            accountId
    )

}