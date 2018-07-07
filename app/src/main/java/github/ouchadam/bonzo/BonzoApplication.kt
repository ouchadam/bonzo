package github.ouchadam.bonzo

import github.ouchadam.api.ExportedApiModule
import github.ouchadam.api.models.ClientCredentials
import github.ouchadam.auth.ExportedAuthModule
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.common.SchedulerPair
import github.ouchadam.common.schedulers
import github.ouchadam.modules.Modules
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.auth.AuthModule
import github.ouchadam.modules.auth.models.AuthStatus

@SuppressWarnings("unused")
class BonzoApplication : BonzoBaseApplication() {

    override lateinit var modules: Modules

    override fun onCreate() {
        super.onCreate()

        val clientCredentials = ClientCredentials(
                id = "oauth2client_00009YP4w3uEHpEjk1eyqf",
                secret = "mnzpub.n+m4dfG13eBk6K0MBH0T5/TKar16eutCTrKf+nljx9EK3EK87F0elRU/MbwCaLOk+gahjBxcEpq1JxU0xH/S"
        )

        val apiModule = ExportedApiModule.create()
        val authModule = ExportedAuthModule.create(apiModule, clientCredentials, this)

        this.modules = object : Modules {

            override fun api(): ApiModule = apiModule

            override fun auth(): AuthModule = authModule

        }

        val authStatusService = authModule.authStatusService()
        authStatusService
                .observeAuthStatus()
                .startWith {
                    authStatusService.forceAuthStatusUpdate()
                }
                .schedulers(SchedulerPair())
                .subscribe {
                    when (it) {
                        AuthStatus.SIGNED_OUT -> showSignedOutNotification()
                    }
                }
    }

    private fun showSignedOutNotification() {
        // TODO
    }
}
