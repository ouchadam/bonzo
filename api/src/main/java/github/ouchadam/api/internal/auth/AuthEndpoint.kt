package github.ouchadam.api.internal.auth

import github.ouchadam.api.models.api.ApiAuthorizationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthEndpoint {

    @POST("https://api.monzo.com/oauth2/")
    fun submitAuthorizationCode(@Body request: ApiSubmitAuthorizationCodeRequest): ApiAuthorizationResponse

}