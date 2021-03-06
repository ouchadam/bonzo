package github.ouchadam.auth

import android.app.Application
import android.content.Context
import github.ouchadam.modules.api.models.ClientCredentials
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.auth.AuthModule
import github.ouchadam.modules.auth.AuthStatusService

class ExportedAuthModule(
        private val apiModule: ApiModule,
        private val clientCredentials: ClientCredentials,
        private val tokenPersistence: AccessTokenPersistence
) : AuthModule {

    companion object {

        fun create(apiModule: ApiModule, clientCredentials: ClientCredentials, tokenPersistence: AccessTokenPersistence): ExportedAuthModule {
            return ExportedAuthModule(apiModule, clientCredentials, tokenPersistence)
        }

    }

    override fun authenticatorService(): AuthenticatorService {
        val auth = apiModule.auth(clientCredentials)
        return AuthenticatorService(auth, tokenPersistence)
    }

    override fun authStatusService(): AuthStatusService {
        return AuthStatusService(tokenPersistence)
    }

}