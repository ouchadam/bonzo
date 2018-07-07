package github.ouchadam.modules

import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.auth.AuthModule

interface Modules {

    fun api(): ApiModule

    fun auth(): AuthModule

}