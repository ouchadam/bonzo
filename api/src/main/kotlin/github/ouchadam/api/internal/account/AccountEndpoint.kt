package github.ouchadam.api.internal.account

import github.ouchadam.modules.api.models.api.ApiAccountsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface AccountEndpoint {

    @GET("/accounts")
    fun getAccounts(
            @HeaderMap headers: Map<String, String>
    ): Single<ApiAccountsResponse>

}