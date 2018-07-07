package github.ouchadam.modules.auth

import io.reactivex.Completable
import io.reactivex.Single
import java.net.URL

interface AuthenticatorService {

    fun createUrl(uniqueToken: String): Single<URL>

    fun submitResponse(url: String): Completable

}