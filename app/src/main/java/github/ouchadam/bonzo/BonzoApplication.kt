package github.ouchadam.bonzo

import github.ouchadam.api.ExportedApiModule
import github.ouchadam.api.models.ClientCredentials
import github.ouchadam.auth.ExportedAuthModule
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.modules.Modules
import github.ouchadam.modules.api.ApiModule
import github.ouchadam.modules.auth.AuthModule

class BonzoApplication : BonzoBaseApplication() {

    override lateinit var modules: Modules

    override fun onCreate() {
        super.onCreate()

        val clientCredentials = ClientCredentials(
                id = "",
                secret = ""
        )

        val apiModule = ExportedApiModule.create()
        val authModule = ExportedAuthModule.create(apiModule, clientCredentials, this)

        this.modules = object : Modules {

            override fun api(): ApiModule = apiModule

            override fun auth(): AuthModule = authModule

        }
    }

}
