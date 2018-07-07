package github.ouchadam.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AuthRedirectActivity : AppCompatActivity() {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authModule = AuthModule.create()
        presenter = authModule.presenter()
    }

    override fun onStart() {
        super.onStart()
        val data = intent.data.toString()
        presenter.startPresenting(data)
    }

}
