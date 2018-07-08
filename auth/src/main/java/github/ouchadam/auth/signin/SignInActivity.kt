package github.ouchadam.auth.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.ouchadam.auth.R
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.SchedulerPair
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.net.URL

class SignInActivity : AppCompatActivity(), SignInPresenter.View {

    private lateinit var signInPresenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val modules = (application as BonzoBaseApplication).modules
        val authModule = modules.auth()

        signInPresenter = SignInPresenter(
                authModule.authenticatorService(),
                this,
                SchedulerPair()
        )

        authentication_error.setOnClickListener {
            signInPresenter.startSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
        signInPresenter.startPresenting()
    }

    override fun showLoading() {
    }

    override fun showContent(url: URL) {
        Intent(Intent.ACTION_VIEW).run {
            data = Uri.parse(url.toString())
            startActivity(this)
            finish()
        }
    }

    override fun showError() {
    }

    override fun onStop() {
        signInPresenter.stopPresenting()
        super.onStop()
    }

}
