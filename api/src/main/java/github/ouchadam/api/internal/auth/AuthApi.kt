package github.ouchadam.api.internal.auth

import github.ouchadam.api.Auth
import github.ouchadam.api.models.ClientCredentials
import github.ouchadam.api.models.api.ApiRedirectResponse
import retrofit2.Retrofit
import java.net.URL

private const val BASE_AUTH_URL = "https://auth.monzo.com"

class AuthApi(private val endpoint: AuthEndpoint,
              private val clientCredentials: ClientCredentials) : Auth {

    companion object {

        fun from(retrofit: Retrofit, clientCredentials: ClientCredentials): AuthApi {
            val endpoint = retrofit.newBuilder()
                    .baseUrl(BASE_AUTH_URL)
                    .build()
                    .create(AuthEndpoint::class.java)
            return AuthApi(endpoint, clientCredentials)
        }

    }

    override fun acquireAccessTokenUrl(redirectUri: String, uniqueRequestToken: String) =
            URL(
                    "$BASE_AUTH_URL?" +
                            "client_id=${clientCredentials.id}" +
                            "&" +
                            "redirect_uri=$redirectUri" +
                            "&" +
                            "response_type=code" +
                            "&" +
                            "state=$uniqueRequestToken"
            )

    override fun convertRedirectResponse(sourceRedirectUri: String, redirectResponse: String): ApiRedirectResponse {
        // assuming all parameters exist, are clean and unique...
        val queryValues = URL(redirectResponse).query.split("&").map {
            val keyValue = it.split("=")
            Pair(keyValue[0], keyValue[1])
        }.fold(HashMap<String, String>(), { accumulator, (key, value) ->
            accumulator[key] = value
            accumulator
        })
        return ApiRedirectResponse(
                sourceRedirectUri,
                queryValues["authorization_code"]!!,
                queryValues["state"]!!
        )
    }

    override fun submitAuthorizationCode(redirectResponse: ApiRedirectResponse) =
            endpoint.submitAuthorizationCode(
                    ApiSubmitAuthorizationCodeRequest(
                            clientId = clientCredentials.id,
                            clientSecret = clientCredentials.secret,
                            redirectUri = redirectResponse.sourceRedirectUri,
                            authorizationCode = redirectResponse.authorizationCode
                    )
            )

}