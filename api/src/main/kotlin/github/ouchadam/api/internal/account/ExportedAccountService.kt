package github.ouchadam.api.internal.account

import github.ouchadam.api.internal.HeadersProvider
import github.ouchadam.modules.api.AccountService
import retrofit2.Retrofit

class ExportedAccountService(
        private val accountEndpoint: AccountEndpoint,
        private val headersProvider: HeadersProvider
) : AccountService {

    companion object {

        fun from(retrofit: Retrofit, headersProvider: HeadersProvider): AccountService {
            val endpoint = retrofit.newBuilder()
                    .build()
                    .create(AccountEndpoint::class.java)
            return ExportedAccountService(endpoint, headersProvider)
        }

    }

    override fun readAccounts() = accountEndpoint.getBalance(
            headersProvider.authenticatedHeaders())

}