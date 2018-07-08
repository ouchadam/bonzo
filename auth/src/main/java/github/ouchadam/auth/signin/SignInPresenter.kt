package github.ouchadam.auth.signin

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class SignInPresenter(private val signInData: SignInData,
                      private val view: View
) {

    private val disposables = CompositeDisposable()

    fun startPresenting() {
        disposables += signInData.observe().subscribe {
            view.show(it)
        }
    }

    fun startSignIn() {
        disposables += signInData.createSignInPayload()
    }

    fun stopPresenting() = disposables.clear()

    interface View {

        fun show(model: SignInViewModel)

    }

}