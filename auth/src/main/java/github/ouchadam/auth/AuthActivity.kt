package github.ouchadam.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.common.SchedulerPair
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class AuthActivity : AppCompatActivity(), Presenter.View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val modules = (application as BonzoBaseApplication).modules
        val authModule = modules.auth()

        presenter = Presenter(
                authModule.authenticatorService(),
                this,
                SchedulerPair()
        )

        authentication_error_button.setOnClickListener {
            presenter.startSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting()
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
        presenter.stopPresenting()
        super.onStop()
    }

}
