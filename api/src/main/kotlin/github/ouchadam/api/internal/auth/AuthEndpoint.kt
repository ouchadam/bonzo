package github.ouchadam.api.internal.auth

import github.ouchadam.modules.api.models.api.ApiAuthorizationResponse
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthEndpoint {

    @FormUrlEncoded
    @POST("/oauth2/token")
    fun submitAuthorizationCode(@FieldMap request: Map<String, String>): Single<ApiAuthorizationResponse>

}