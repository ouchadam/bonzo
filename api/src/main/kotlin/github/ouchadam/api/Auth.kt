package github.ouchadam.api

import github.ouchadam.api.models.api.ApiAuthorizationResponse
import github.ouchadam.api.models.api.ApiRedirectResponse
import io.reactivex.Single
import java.net.URL

interface Auth {

    fun acquireAccessTokenUrl(redirectUri: String, uniqueRequestToken: String): URL

    fun convertRedirectResponse(sourceRedirectUri: String, redirectResponse: String): ApiRedirectResponse

    fun submitAuthorizationCode(redirectResponse: ApiRedirectResponse): Single<ApiAuthorizationResponse>

}