package github.ouchadam.auth.redirect

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import github.ouchadam.auth.AuthModule
import github.ouchadam.auth.R
import kotlinx.android.synthetic.main.activity_redirect.*

class AuthRedirectActivity : AppCompatActivity(), RedirectPresenter.View {

    private lateinit var presenter: RedirectPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redirect)

        val authModule = AuthModule.create()
        presenter = authModule.redirectPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        val data = intent.data.toString()
        presenter.startPresenting(data)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun showSignInSuccess() {
        loading.visibility = View.GONE
    }

    override fun showError() {
        loading.visibility = View.GONE
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }
}
