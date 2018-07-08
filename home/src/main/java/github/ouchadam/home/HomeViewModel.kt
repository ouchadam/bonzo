package github.ouchadam.home

import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.ViewModel

data class HomeViewModel(
        val balanceLabel: String = "",
        override val status: LceStatus = LceStatus.IDLE_EMPTY
) : ViewModel