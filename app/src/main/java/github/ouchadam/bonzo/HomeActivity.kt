package github.ouchadam.bonzo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.common.SchedulerPair
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity(), Presenter.View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val modules = (application as BonzoBaseApplication).modules
        val authStatusService = modules.auth().authStatusService()
        val homeService = HomeService(authStatusService)

        presenter = Presenter(
                homeService,
                this,
                SchedulerPair()
        )

        authentication_error_button.setOnClickListener {
            val authActivity = Intent("${BuildConfig.APPLICATION_ID}.auth")
            startActivity(authActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting()
    }

    override fun showLoading() {
        authentication_error_button.visibility = View.GONE
    }

    override fun showContent(model: ViewModel) {
        authentication_error_button.visibility = View.GONE
    }

    override fun showError() {
        authentication_error_button.visibility = View.VISIBLE
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }
}
