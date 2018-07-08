package github.ouchadam.auth.redirect

import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.ViewModel

data class RedirectViewModel(
        override val status: LceStatus = LceStatus.IDLE_EMPTY
) : ViewModel