package github.ouchadam.auth.signin

import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.ViewModel
import java.net.URL

data class SignInViewModel(
        val signInPayload: URL? = null,
        override val status: LceStatus = LceStatus.IDLE_EMPTY
) : ViewModel