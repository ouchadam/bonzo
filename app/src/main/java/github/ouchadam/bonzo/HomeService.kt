package github.ouchadam.bonzo

import github.ouchadam.modules.auth.AuthStatusService
import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable

class HomeService(private val authStatusService: AuthStatusService) {

    fun fetch(): Observable<ViewModel> {
        return authStatusService
                .readStatus()
                .map { toErrorIfSignedOut(it) }
                .map {
                    ViewModel(0.00)
                }
    }

    private fun toErrorIfSignedOut(it: AuthStatus): AuthStatus {
        return if (it == AuthStatus.SIGNED_OUT) {
            throw IllegalStateException("")
        } else {
            it
        }
    }


}