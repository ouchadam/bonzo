package github.ouchadam.auth

import github.ouchadam.api.Auth
import github.ouchadam.api.models.api.ApiAuthorizationResponse
import io.reactivex.Single
import java.net.URL

private const val REDIRECT_URL = "https://ouchadam.github/auth-callback"

class AuthenticatorService(private val auth: Auth) {

    fun createUrl(uniqueToken: String): Single<URL> = Single.just(auth.acquireAccessTokenUrl(REDIRECT_URL, uniqueToken))

    fun submitResponse(url: String): Single<ApiAuthorizationResponse> {
        val redirectResponse = auth.convertRedirectResponse(REDIRECT_URL, url)
        return auth.submitAuthorizationCode(redirectResponse)
    }

}