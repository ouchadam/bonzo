package github.ouchadam.auth

import github.ouchadam.api.ApiModule
import github.ouchadam.api.models.ClientCredentials
import github.ouchadam.auth.redirect.RedirectPresenter
import github.ouchadam.common.SchedulerPair
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthModule(private val authService: AuthenticatorService, private val schedulerPair: SchedulerPair) {

    companion object {

        fun create(): AuthModule {
            val clientCredentials = ClientCredentials(
                    "oauth2client_00009YP4w3uEHpEjk1eyqf",
                    "mnzpub.n+m4dfG13eBk6K0MBH0T5/TKar16eutCTrKf+nljx9EK3EK87F0elRU/MbwCaLOk+gahjBxcEpq1JxU0xH/S"
            )
            val apiModule = ApiModule.create()
            val auth = apiModule.auth(clientCredentials)
            val authService = AuthenticatorService(auth)

            val subscribeOn: Scheduler = AndroidSchedulers.mainThread()
            val observeOn: Scheduler = Schedulers.io()
            val schedulerPair = SchedulerPair(subscribeOn, observeOn)
            return AuthModule(authService, schedulerPair)
        }

    }

    fun redirectPresenter(view: RedirectPresenter.View) = RedirectPresenter(authService, view, schedulerPair)

    fun authPresenter(view: Presenter.View) = Presenter(authService, view, schedulerPair)

}