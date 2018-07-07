package github.ouchadam.api.internal.balance

import github.ouchadam.modules.api.models.api.ApiBalanceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface BalanceEndpoint {

    @GET("/balance")
    fun getBalance(
            @HeaderMap headers: Map<String, String>,
            @Query("account_id") accountId: String
    ): Single<ApiBalanceResponse>

}