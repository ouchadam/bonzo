package github.ouchadam.auth

import github.ouchadam.modules.auth.AuthStatusService
import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class AuthStatusService(private val tokenPersistence: AccessTokenPersistence) : AuthStatusService {

    private val subject: BehaviorSubject<AuthStatus> = BehaviorSubject.create()

    init {
        tokenPersistence.setTokenChangeListener {
            updateSubject(it)
        }
    }

    override fun observeAuthStatus(): Observable<AuthStatus> = subject

    override fun forceAuthStatusUpdate() {
        val accessToken: AccessToken? = tokenPersistence.read()
        val hasAccount = accessToken != null
        updateSubject(hasAccount)
    }

    private fun updateSubject(hasAccount: Boolean) {
        subject.onNext(toStatus(hasAccount))
    }

    override fun readStatus(): Single<AuthStatus> {
        return Single.just(toStatus(tokenPersistence.read() != null))
    }

    private fun toStatus(hasAccount: Boolean): AuthStatus {
        return when {
            hasAccount -> AuthStatus.SIGNED_IN
            else -> AuthStatus.SIGNED_OUT
        }
    }

}