package github.ouchadam.auth

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class Presenter(private val service: AuthService) {

    private val disposables = CompositeDisposable()

    fun startPresenting(redirectUrlResponse: String) {
        disposables += service.submitResponse(redirectUrlResponse)
                .subscribeAsLce(
                        onLoading = {
                            // show loading
                        },
                        onContent = {
                            // show content
                        },
                        onError = {
                            // handle error?
                        })
    }

    fun stopPresenting() = disposables.clear()

}