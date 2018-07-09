package github.ouchadam.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import github.ouchadam.common.BonzoBaseApplication
import github.ouchadam.lce.LceStatus
import github.ouchadam.lce.SchedulerPair
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomePresenter.View {

    private lateinit var presenter: HomePresenter
    private lateinit var cache: ViewModelCache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val modules = (application as BonzoBaseApplication).modules
        val authStatusService = modules.auth().authStatusService()
        val apiModule = modules.api()

        cache = ViewModelProviders.of(this).get(ViewModelCache::class.java)
        val initialModel = cache.value ?: HomeViewModel()

        val homeService = HomeData(
                authStatusService,
                apiModule.account(),
                apiModule.balance(),
                SchedulerPair(),
                initialModel
        )

        presenter = HomePresenter(
                homeService,
                this
        )

        error.setOnClickListener {
            val authActivity = Intent("${BuildConfig.NAVIGATION_URI}.auth")
            startActivity(authActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        val cacheIsEmpty = cache.value == null

        presenter.startPresenting()

        if (cacheIsEmpty) {
            presenter.fetch()
        }
    }

    override fun show(model: HomeViewModel) {
        cache.value = model

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

                content.text = model.balanceLabel
            }
        }
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }

    class ViewModelCache(var value: HomeViewModel? = null) : ViewModel()
}
