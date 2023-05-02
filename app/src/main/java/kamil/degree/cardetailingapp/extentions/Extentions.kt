package kamil.degree.cardetailingapp.extentions

import android.app.Activity
import android.content.Intent
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kamil.degree.cardetailingapp.R

object Extentions {

    fun Activity.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    fun Activity.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    fun Fragment.shortToast(message: String) = Toast.makeText(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    fun Fragment.longToast(message: String) = Toast.makeText(activity!!.baseContext, message, Toast.LENGTH_LONG).show()
    fun EditText.useText() = text.toString().trim()
    fun TextView.useText() = text.toString().trim()



}