package github.ouchadam.bonzo

import android.content.Context
import github.ouchadam.api.ExportedApiModule
import github.ouchadam.api.TokenProvider
import github.ouchadam.auth.AccessTokenPersistence
import github.ouchadam.auth.ExportedAuthModule
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.SchedulerPair
import github.ouchadam.lce.schedulers
import github.ouchadam.modules.Modules
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.api.models.ClientCredentials
import github.ouchadam.modules.auth.AuthModule
import github.ouchadam.modules.auth.models.AuthStatus

@SuppressWarnings("unused")
class BonzoApplication : BonzoBaseApplication() {

    override lateinit var modules: Modules

    override fun onCreate() {
        super.onCreate()

        val clientCredentials = ClientCredentials(
                id = BuildConfig.CLIENT_ID,
                secret = BuildConfig.CLIENT_SECRET
        )

        val tokenPersistence = AccessTokenPersistence(getSharedPreferences("token", Context.MODE_PRIVATE))

        val apiModule = ExportedApiModule.create(object : TokenProvider {

            override fun readToken() = tokenPersistence.read()?.value

            override fun invalidateToken() = tokenPersistence.invalidate()

        })
        val authModule = ExportedAuthModule.create(apiModule, clientCredentials, tokenPersistence)

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
