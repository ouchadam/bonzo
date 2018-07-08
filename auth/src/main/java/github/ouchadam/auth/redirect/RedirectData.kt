package github.ouchadam.auth.redirect

import github.ouchadam.lce.*
import github.ouchadam.modules.auth.AuthenticatorService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class RedirectData(
        private val authenticatorService: AuthenticatorService,
        initialValue: RedirectViewModel,
        schedulerPair: SchedulerPair

) {

    private val lce = Pipeline<Boolean, RedirectViewModel>(schedulerPair, initialValue)

    fun observe(): Observable<RedirectViewModel> = lce.observe()

    fun submitResponse(redirectUrlResponse: String): Disposable {
        val source = authenticatorService.submitResponse(redirectUrlResponse)
                .toSingle { true }
        return lce.execute(source, resultHandler)
    }

    private val resultHandler = { status: LceStatus, _: Lce<Boolean, Throwable>, _: RedirectViewModel ->
        RedirectViewModel(status = status)
    }

}