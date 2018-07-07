package github.ouchadam.auth

import github.ouchadam.api.Auth
import github.ouchadam.api.models.api.ApiAuthorizationResponse
import io.reactivex.Single


private const val REDIRECT_URL = "bonzo://auth/callback"

class AuthService(private val auth: Auth) {

    fun createUrl(uniqueToken: String) = Single.just(auth.acquireAccessTokenUrl(REDIRECT_URL, uniqueToken))

    fun submitResponse(url: String): Single<ApiAuthorizationResponse> {
        val redirectResponse = auth.convertRedirectResponse(REDIRECT_URL, url)
        return auth.submitAuthorizationCode(redirectResponse)
    }

}