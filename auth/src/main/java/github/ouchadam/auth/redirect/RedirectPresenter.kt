package github.ouchadam.auth.redirect

import github.ouchadam.auth.AuthenticatorService
import github.ouchadam.common.SchedulerPair
import github.ouchadam.common.schedulers
import github.ouchadam.common.subscribeAsLce
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class RedirectPresenter(
        private val service: AuthenticatorService,
        private val view: View,
        private val schedulerPair: SchedulerPair
) {

    private val disposables = CompositeDisposable()

    fun startPresenting(redirectUrlResponse: String) {
        disposables += service.submitResponse(redirectUrlResponse)
                .schedulers(schedulerPair)
                .subscribeAsLce(
                        onLoading = {
                            view.showLoading()
                        },
                        onContent = {
                            view.showSignInSuccess()
                        },
                        onError = {
                            view.showError()
                        })
    }

    fun stopPresenting() = disposables.clear()

    interface View {

        fun showLoading()

        fun showSignInSuccess()

        fun showError()

    }

}