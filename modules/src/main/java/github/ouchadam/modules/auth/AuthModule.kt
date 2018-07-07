package github.ouchadam.modules.auth

interface AuthModule {

    fun authenticatorService(): AuthenticatorService

    fun authStatusService(): AuthStatusService

}