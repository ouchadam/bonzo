package github.ouchadam.auth.redirect

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class RedirectPresenter(
        private val redirectData: RedirectData,
        private val view: View
) {

    private val disposables = CompositeDisposable()

    fun startPresenting(redirectUrlResponse: String) {
        disposables += redirectData.observe().subscribe {
            view.show(it)
        }

        disposables += redirectData.submitResponse(redirectUrlResponse)
    }

    fun stopPresenting() = disposables.clear()

    interface View {

        fun show(model: RedirectViewModel)

    }

}