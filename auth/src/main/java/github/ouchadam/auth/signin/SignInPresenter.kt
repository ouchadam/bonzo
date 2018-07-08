package github.ouchadam.auth.signin

import github.ouchadam.lce.SchedulerPair
import github.ouchadam.lce.schedulers
import github.ouchadam.lce.subscribeAsLce
import github.ouchadam.modules.auth.AuthenticatorService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.net.URL
import java.util.*

class SignInPresenter(private val service: AuthenticatorService,
                      private val view: View,
                      private val schedulerPair: SchedulerPair
) {

    private val disposables = CompositeDisposable()

    fun startPresenting() {
        // do nothing
    }

    fun startSignIn() {
        val uniqueToken = UUID.randomUUID().toString()
        disposables += service.createUrl(uniqueToken)
                .schedulers(schedulerPair)
                .subscribeAsLce(
                        onLoading = {
                            view.showLoading()
                        },
                        onContent = {
                            view.showContent(it)
                        },
                        onError = {
                            view.showError()
                        })
    }


    fun stopPresenting() = disposables.clear()

    interface View {

        fun showLoading()

        fun showContent(url: URL)

        fun showError()

    }

}