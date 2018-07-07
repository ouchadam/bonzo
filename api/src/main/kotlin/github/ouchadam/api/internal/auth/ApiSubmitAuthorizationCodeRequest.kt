package github.ouchadam.api.internal.auth

import com.squareup.moshi.Json

data class ApiSubmitAuthorizationCodeRequest(
        @Json(name = "grant_type")
        val grantType: String = "authorization_code",

        @Json(name = "client_id")
        val clientId: String,

        @Json(name = "client_secret")
        val clientSecret: String,

        @Json(name = "redirect_uri")
        val redirectUri: String,

        @Json(name = "code")
        val authorizationCode: String
)