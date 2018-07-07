package github.ouchadam.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AuthRedirectActivity : AppCompatActivity(), Presenter.View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authModule = AuthModule.create()
        presenter = authModule.presenter(this)
    }

    override fun onStart() {
        super.onStart()
        val data = intent.data.toString()
        presenter.startPresenting(data)
    }

    override fun showLoading() {
        TODO("not implemented")
    }

    override fun showContent() {
        TODO("not implemented")
    }

    override fun showError() {
        TODO("not implemented")
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }
}
