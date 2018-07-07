package github.ouchadam.auth

import android.app.Application
import android.content.Context
import github.ouchadam.api.models.ClientCredentials
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.auth.AuthModule

class ExportedAuthModule(
        private val apiModule: ApiModule,
        private val clientCredentials: ClientCredentials,
        private val tokenPersistence: AccessTokenPersistence
) : AuthModule {

    companion object {

        fun create(apiModule: ApiModule, clientCredentials: ClientCredentials, context: Application): ExportedAuthModule {
            val tokenPersistence = AccessTokenPersistence(context.getSharedPreferences("token", Context.MODE_PRIVATE))
            return ExportedAuthModule(apiModule, clientCredentials, tokenPersistence)
        }

    }

    override fun authenticatorService(): AuthenticatorService {
        val auth = apiModule.auth(clientCredentials)
        return AuthenticatorService(auth, tokenPersistence)
    }

}