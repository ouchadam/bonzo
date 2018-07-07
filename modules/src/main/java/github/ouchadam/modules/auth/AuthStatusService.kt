package github.ouchadam.modules.auth

import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable

interface AuthStatusService {

    fun observeAuthStatus(): Observable<AuthStatus>

    fun forceAuthStatusUpdate()

    fun readStatus(): Observable<AuthStatus>

}