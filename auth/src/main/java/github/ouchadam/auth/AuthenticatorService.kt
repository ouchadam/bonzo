package github.ouchadam.auth

import github.ouchadam.modules.api.AuthService
import github.ouchadam.modules.auth.AuthenticatorService
import io.reactivex.Completable
import io.reactivex.Single
import java.net.URL

private const val REDIRECT_URL = "https://ouchadam.github/auth-callback"

class AuthenticatorService(
        private val auth: AuthService,
        private val accessTokenPersistence: AccessTokenPersistence
) : AuthenticatorService {

    override fun createUrl(uniqueToken: String): Single<URL> = Single.just(auth.acquireAccessTokenUrl(REDIRECT_URL, uniqueToken))

    override fun submitResponse(url: String): Completable {
        val redirectResponse = auth.convertRedirectResponse(REDIRECT_URL, url)
        return auth.submitAuthorizationCode(redirectResponse)
                .doOnSuccess {
                    val accessToken = AccessToken(it.accessToken, it.expiresIn)
                    accessTokenPersistence.persist(accessToken)
                }.toCompletable()
    }

}
