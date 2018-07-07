package github.ouchadam.bonzo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.ouchadam.api.ApiModule
import github.ouchadam.api.models.ClientCredentials
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ApiModule.create()

        val clientCredentials = ClientCredentials(
                "oauth2client_00009YP4w3uEHpEjk1eyqf",
                "mnzpub.n+m4dfG13eBk6K0MBH0T5/TKar16eutCTrKf+nljx9EK3EK87F0elRU/MbwCaLOk+gahjBxcEpq1JxU0xH/S"
        )

        val redirectUrl = "bonzo://auth/callback"
        val uniqueValue = UUID.randomUUID().toString()

        val auth = api.auth(clientCredentials)
        val acquireAccessTokenUrl = auth.acquireAccessTokenUrl(redirectUrl, uniqueValue)

        val redirectResponse = auth.convertRedirectResponse(redirectUrl, "")
        val response = auth.submitAuthorizationCode(redirectResponse)
    }


    class AuthenticatorService {

        fun start() {

        }




    }

}
