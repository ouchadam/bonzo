package github.ouchadam.modules.api

import github.ouchadam.api.models.ClientCredentials

interface ApiModule {

    fun auth(credentials: ClientCredentials): AuthService

}