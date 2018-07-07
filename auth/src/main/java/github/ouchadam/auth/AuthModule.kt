package github.ouchadam.auth

import github.ouchadam.api.ApiModule
import github.ouchadam.api.models.ClientCredentials

class AuthModule(private val authService: AuthService) {

    companion object {

        fun create(): AuthModule {
            val clientCredentials = ClientCredentials("", "")
            val apiModule = ApiModule.create()
            val auth = apiModule.auth(clientCredentials)
            val authService = AuthService(auth)
            return AuthModule(authService)
        }

    }

    fun presenter(view: Presenter.View) = Presenter(authService, view)

}