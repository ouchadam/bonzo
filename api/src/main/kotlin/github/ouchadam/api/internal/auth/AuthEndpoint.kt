package github.ouchadam.api.internal.auth

import github.ouchadam.modules.api.models.api.ApiAuthorizationResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthEndpoint {

    @POST("https://api.monzo.com/oauth2/token")
    fun submitAuthorizationCode(@Body request: ApiSubmitAuthorizationCodeRequest): Single<ApiAuthorizationResponse>

}