package github.ouchadam.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.SchedulerPair
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomePresenter.View {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val modules = (application as BonzoBaseApplication).modules
        val authStatusService = modules.auth().authStatusService()
        val apiModule = modules.api()
        val homeService = HomeData(
                authStatusService,
                apiModule.account(),
                apiModule.balance(),
                SchedulerPair(),
                HomeViewModel()
        )

        presenter = HomePresenter(
                homeService,
                this
        )

        error.setOnClickListener {
            val authActivity = Intent("${BuildConfig.APPLICATION_ID}.auth")
            startActivity(authActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting()
    }

    override fun show(model: HomeViewModel) {
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
                Log.e("!!!", "idle empty")
//                loading.visibility = View.VISIBLE
            }

            LceStatus.IDLE_WITH_CONTENT -> {
                loading.visibility = View.GONE
                content.visibility = View.VISIBLE
                error.visibility = View.GONE

                content.text = model.balanceLabel
            }
        }
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }
}