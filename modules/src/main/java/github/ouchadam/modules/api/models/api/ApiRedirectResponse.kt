package github.ouchadam.modules.api.models.api

data class ApiRedirectResponse(
        val sourceRedirectUri: String,
        val authorizationCode: String,
        val uniqueRequestToken: String
)