package github.ouchadam.modules.api

import github.ouchadam.modules.api.models.api.ApiAuthorizationResponse
import github.ouchadam.api.models.api.ApiRedirectResponse
import io.reactivex.Single
import java.net.URL

interface AuthService {

    fun acquireAccessTokenUrl(redirectUri: String, uniqueRequestToken: String): URL

    fun convertRedirectResponse(sourceRedirectUri: String, redirectResponse: String): ApiRedirectResponse

    fun submitAuthorizationCode(redirectResponse: ApiRedirectResponse): Single<ApiAuthorizationResponse>

}