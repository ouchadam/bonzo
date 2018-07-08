package github.ouchadam.auth.signin

import android.util.Log
import github.ouchadam.lce.Lce
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.Pipeline
import github.ouchadam.lce.SchedulerPair
import github.ouchadam.modules.auth.AuthenticatorService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.net.URL
import java.util.*

class SignInData(
        private val service: AuthenticatorService,
        initialValue: SignInViewModel,
        schedulerPair: SchedulerPair
) {

    private val lce = Pipeline<Holder, SignInViewModel>(schedulerPair, initialValue)

    fun observe(): Observable<SignInViewModel> = lce.observe()

    fun createSignInPayload(): Disposable {
        val uniqueToken = UUID.randomUUID().toString()
        val source = service.createUrl(uniqueToken)
                .map { Holder(it) }
        return lce.execute(source, resultHandler)
    }

    private val resultHandler = { status: LceStatus, upstream: Lce<Holder, Throwable>, current: SignInViewModel ->
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

    private fun toLoading(current: SignInViewModel, status: LceStatus) = current.copy(
            status = status
    )

    private fun toContent(current: SignInViewModel, status: LceStatus, upstream: Lce.Content<Holder, Throwable>) = current.copy(
            status = status,
            signInPayload = upstream.content.signInPayload
    )

    private fun toError(current: SignInViewModel, status: LceStatus) = current.copy(
            status = status
    )

    data class Holder(
            val signInPayload: URL
    )

}