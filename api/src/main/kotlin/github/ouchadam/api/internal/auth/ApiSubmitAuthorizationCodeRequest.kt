package github.ouchadam.api.internal.auth

import retrofit2.http.Field

data class ApiSubmitAuthorizationCodeRequest(
        @Field("grant_type")
        val grantType: String = "authorization_code",

        @Field("client_id")
        val clientId: String,

        @Field("client_secret")
        val clientSecret: String,

        @Field("redirect_uri")
        val redirectUri: String,

        @Field("code")
        val authorizationCode: String
)