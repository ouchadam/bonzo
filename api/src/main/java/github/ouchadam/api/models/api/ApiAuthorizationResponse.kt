package github.ouchadam.api.models.api

import com.squareup.moshi.Json

data class ApiAuthorizationResponse(
        @Json(name = "access_token")
        val accessToken: String,

        @Json(name = "client_id")
        val clientId: String,

        @Json(name = "expires_in")
        val expiresIn: Long,

        @Json(name = "refresh_token")
        val refreshToken: String,

        @Json(name = "token_type")
        val tokenType: String,

        @Json(name = "user_id")
        val userId: String
)