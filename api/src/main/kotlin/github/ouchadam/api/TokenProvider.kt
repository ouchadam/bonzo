package github.ouchadam.api

interface TokenProvider {

    fun readToken(): String?

    fun invalidateToken()

}