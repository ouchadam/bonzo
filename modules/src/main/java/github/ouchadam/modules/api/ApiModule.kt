package github.ouchadam.modules.api

import github.ouchadam.modules.api.models.ClientCredentials

interface ApiModule {

    fun auth(credentials: ClientCredentials): AuthService

    fun balance(): BalanceService

}