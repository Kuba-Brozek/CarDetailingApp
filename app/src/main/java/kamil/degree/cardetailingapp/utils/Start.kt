package kamil.degree.cardetailingapp.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object Start {

    fun <T: AppCompatActivity> startActivity(startingActivity: Activity, endActivity: Class<T>) {
        val intent = Intent(startingActivity.baseContext, endActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(
            startingActivity.baseContext,
            intent,
            null
        )
        startingActivity.finish()
    }

    fun <T: AppCompatActivity> startFragment(fragment: Fragment, activity: T, container: Int){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(container,fragment)
        transaction.commit()
    }


}