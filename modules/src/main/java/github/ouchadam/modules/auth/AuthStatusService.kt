package github.ouchadam.modules.auth

import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable
import io.reactivex.Single

interface AuthStatusService {

    fun observeAuthStatus(): Observable<AuthStatus>

    fun forceAuthStatusUpdate()

    fun readStatus(): Single<AuthStatus>

}