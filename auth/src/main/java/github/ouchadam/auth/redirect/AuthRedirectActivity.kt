package github.ouchadam.auth.redirect

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.ouchadam.auth.R
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.SchedulerPair
import kotlinx.android.synthetic.main.activity_redirect.*

class AuthRedirectActivity : AppCompatActivity(), RedirectPresenter.View {

    private lateinit var presenter: RedirectPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redirect)

        val modules = (application as BonzoBaseApplication).modules
        val authModule = modules.auth()

        val redirectData = RedirectData(
                authModule.authenticatorService(),
                RedirectViewModel(),
                SchedulerPair()
        )

        presenter = RedirectPresenter(
                redirectData,
                this
        )
    }

    override fun onStart() {
        super.onStart()
        val data = intent.data.toString()
        presenter.startPresenting(data)
    }

    override fun show(model: RedirectViewModel) {
        when (model.status) {
            LceStatus.LOADING_EMPTY -> {
                loading.visibility = View.VISIBLE
                content.visibility = View.GONE
                error.visibility = View.GONE

            }

            LceStatus.LOADING_ON_CONTENT -> TODO()

            LceStatus.ERROR_EMPTY -> {
                loading.visibility = View.GONE
                content.visibility = View.GONE
                error.visibility = View.VISIBLE
            }

            LceStatus.ERROR_ON_CONTENT -> TODO()
            LceStatus.IDLE_EMPTY -> {
                // do nothing
            }
            LceStatus.IDLE_WITH_CONTENT -> {
                loading.visibility = View.GONE
                content.visibility = View.VISIBLE
                error.visibility = View.GONE

                content.text = "Success"
            }
        }
        loading.visibility = View.VISIBLE
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }
}
