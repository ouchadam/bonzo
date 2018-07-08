package github.ouchadam.auth.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.ouchadam.auth.R
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.SchedulerPair
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), SignInPresenter.View {

    private lateinit var signInPresenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val modules = (application as BonzoBaseApplication).modules
        val authModule = modules.auth()

        val signInData = SignInData(
                authModule.authenticatorService(),
                SignInViewModel(),
                SchedulerPair()
        )

        signInPresenter = SignInPresenter(
                signInData,
                this
        )

        start_sign_in_button.setOnClickListener {
            signInPresenter.startSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
        signInPresenter.startPresenting()
    }

    override fun show(model: SignInViewModel) {
        when (model.status) {
            LceStatus.IDLE_WITH_CONTENT -> {
                model.signInPayload?.let {
                    Intent(Intent.ACTION_VIEW).run {
                        data = Uri.parse(it.toString())
                        startActivity(this)
                        finish()
                    }
                }
            }
            else -> {
                // do nothing
            }
        }
    }

    override fun onStop() {
        signInPresenter.stopPresenting()
        super.onStop()
    }

}
