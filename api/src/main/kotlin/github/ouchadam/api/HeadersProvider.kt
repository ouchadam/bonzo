package github.ouchadam.api

class HeadersProvider(private val tokenProvider: TokenProvider) {

    fun authenticatedHeaders(): Map<String, String> {
        return mapOf(createAuthorizationHeader())
    }

    private fun createAuthorizationHeader() =
            Pair("Authorization", "Bearer ${tokenProvider.readToken() ?: "missing"}")

}