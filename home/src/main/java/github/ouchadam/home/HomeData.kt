package github.ouchadam.home

import android.util.Log
import github.ouchadam.lce.Lce
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.Pipeline
import github.ouchadam.lce.SchedulerPair
import github.ouchadam.modules.api.AccountService
import github.ouchadam.modules.api.BalanceService
import github.ouchadam.modules.api.models.api.ApiAccountsResponse
import github.ouchadam.modules.api.models.api.ApiBalanceResponse
import github.ouchadam.modules.auth.AuthStatusService
import github.ouchadam.modules.auth.models.AuthStatus
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.text.NumberFormat
import java.util.*

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
        val source = createDataSource()
        return lce.execute(source, resultHandler)
    }

    private fun createDataSource(): Single<Holder> = authStatusService
            .readStatus()
            .map { toErrorIfSignedOut(it) }
            .flatMap { accountService.readAccounts() }
            .flatMap { allBalances(it) }
            .map { accounts ->
                accounts.sortBy {
                    it.balanceInMinor
                }
                Holder(accounts.last().balanceInMinor)
            }

    private fun allBalances(it: ApiAccountsResponse): Single<MutableList<ApiBalanceResponse>> = Observable.fromIterable(it.accounts)
            .flatMap { account ->
                balanceService.readBalance(account.id).toObservable()
            }.toList()


    private val resultHandler = { status: LceStatus, upstream: Lce<Holder, Throwable>, current: HomeViewModel ->
        when (upstream) {
            is Lce.Loading -> {
                toLoading(current, status)
            }
            is Lce.Content -> {
                toContent(current, status, upstream)
            }
            is Lce.Error -> {
                Log.e("!!!", upstream.error.message, upstream.error)

                toError(current, status)
            }
        }
    }

    private fun toLoading(current: HomeViewModel, status: LceStatus) = current.copy(
            status = status
    )

    private fun toContent(current: HomeViewModel, status: LceStatus, upstream: Lce.Content<Holder, Throwable>) = current.copy(
            status = status,
            balanceLabel = NumberFormat.getCurrencyInstance(Locale.UK)
                    .format(upstream.content.balance / 100f)
    )

    private fun toError(current: HomeViewModel, status: LceStatus) = current.copy(
            status = status
    )

    private fun toErrorIfSignedOut(it: AuthStatus): AuthStatus {
        return if (it == AuthStatus.SIGNED_OUT) {
            throw IllegalStateException("Signed out, uh oh")
        } else {
            it
        }
    }

    private data class Holder(val balance: Long)

}