package kamil.degree.cardetailingapp.detailing

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.core.os.postDelayed
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.main.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.max = 1000
        progressBar.progress = 0
        val currentProgress = 1000
        ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
            .setDuration(3000)
            .start()

        Handler()
            .postDelayed({
                startActivity(Intent(this, SignActivity()::class.java))
        }, 3000)
    }

}

