package github.ouchadam.auth

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class Presenter(private val service: AuthService, private val view: View) {

    private val disposables = CompositeDisposable()

    fun startPresenting(redirectUrlResponse: String) {
        disposables += service.submitResponse(redirectUrlResponse)
                .subscribeAsLce(
                        onLoading = {
                            view.showLoading()
                        },
                        onContent = {
                            view.showContent()
                        },
                        onError = {
                            view.showError()
                        })
    }

    fun stopPresenting() = disposables.clear()

    interface View {

        fun showLoading()

        fun showContent()

        fun showError()

    }

}