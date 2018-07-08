package github.ouchadam.home

import android.util.Log
import github.ouchadam.lce.Lce
import github.ouchadam.lce.Pipeline
import github.ouchadam.lce.SchedulerPair
import github.ouchadam.modules.api.AccountService
import github.ouchadam.modules.api.BalanceService
import github.ouchadam.modules.auth.AuthStatusService
import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class HomeData(
        private val authStatusService: AuthStatusService,
        private val accountService: AccountService,
        private val balanceService: BalanceService,
        schedulerPair: SchedulerPair,
        initialValue: HomeViewModel
) {

    private val lce = Pipeline<Holder, HomeViewModel>(schedulerPair, initialValue)

    fun observe(): Observable<HomeViewModel> = lce.observe()

    fun fetch(): Disposable {
        val source = authStatusService
                .readStatus()
                .map { toErrorIfSignedOut(it) }
                .flatMap { accountService.readAccounts() }
                .flatMap {
                    Observable.fromIterable(it.accounts)
                            .flatMap { account ->
                                balanceService.readBalance(account.id).toObservable()
                            }.toList()
                }
                .map { accounts ->
                    accounts.sortBy {
                        it.balanceInMinor
                    }
                    Holder(accounts.last().balanceInMinor)
                }

        return lce.execute(source, { status, upstream, current ->
            when (upstream) {
                is Lce.Loading -> {
                    current.copy(
                            status = status
                    )
                }
                is Lce.Content -> {
                    current.copy(
                            status = status,
                            balanceLabel = "£${upstream.content.balance / 100}"
                    )
                }

                is Lce.Error -> {
                    Log.e("!!!", upstream.error.message, upstream.error)

                    current.copy(
                            status = status
                    )
                }
            }
        })
    }

    private fun toErrorIfSignedOut(it: AuthStatus): AuthStatus {
        return if (it == AuthStatus.SIGNED_OUT) {
            throw IllegalStateException("Signed out, uh oh")
        } else {
            it
        }
    }

    private data class Holder(val balance: Long)

}