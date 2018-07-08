package github.ouchadam.api.internal.account

import github.ouchadam.modules.api.models.api.ApiAccountResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface AccountEndpoint {

    @GET("/accounts")
    fun getBalance(
            @HeaderMap headers: Map<String, String>
    ): Single<List<ApiAccountResponse>>

}