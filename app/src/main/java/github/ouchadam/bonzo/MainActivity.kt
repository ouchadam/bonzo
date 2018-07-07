package github.ouchadam.bonzo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_in_button.setOnClickListener {
            val authActivity = Intent("${BuildConfig.APPLICATION_ID}.auth")
            startActivity(authActivity)
        }
    }

}
