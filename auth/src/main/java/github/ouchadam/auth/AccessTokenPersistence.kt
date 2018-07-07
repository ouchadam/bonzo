package github.ouchadam.auth

import android.content.SharedPreferences

class AccessTokenPersistence(private val preferences: SharedPreferences) {

    var listener: Listener? = null

    fun persist(accessToken: AccessToken) {
        preferences.edit()
                .putString("value", accessToken.value)
                .putLong("expiresIn", accessToken.expiresInSeconds)
                .apply()
        listener?.let { it(true) }
    }

    fun read(): AccessToken? {
        return if (preferences.contains("value") && preferences.contains("expiresIn")) {
            AccessToken(preferences.getString("value", null), preferences.getLong("expiresIn", -1))
        } else {
            null
        }
    }

    fun invalidate() {
        preferences.edit().clear().commit()
        listener?.let { it(false) }
    }

    fun setTokenChangeListener(listener: Listener) {
        this.listener = listener
    }

}

typealias Listener = (hasToken: Boolean) -> Unit
