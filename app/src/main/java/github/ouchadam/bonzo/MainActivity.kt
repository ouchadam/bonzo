package github.ouchadam.bonzo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authentication_error_button.setOnClickListener {
            val authActivity = Intent("${BuildConfig.APPLICATION_ID}.auth")
            startActivity(authActivity)
        }
        showAuthenticationError()
    }

    private fun showAuthenticationError() {
        authentication_error_button.visibility = View.VISIBLE
    }

}
