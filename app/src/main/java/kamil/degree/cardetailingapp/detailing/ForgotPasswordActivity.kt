package kamil.degree.cardetailingapp.detailing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.ActivityLoginBinding
import kamil.degree.cardetailingapp.extentions.Extentions.longToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class ForgotPasswordActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)



        findViewById<Button>(R.id.resetPassword_BTN).setOnClickListener{
            val emailForGotPasswordET = findViewById<EditText>(R.id.email_forgot_password_ET).useText()
            if (emailForGotPasswordET != "") {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Email to reset password").setMessage("Are you sure you want to reset password?")
                    .setNegativeButton("No"){ _, _ -> }
                    .setPositiveButton("Yes"){ _, _ ->
                        FirebaseRepository.sendEmail(emailForGotPasswordET)
                    }.show()
            } else {
                longToast("Enter your email in email field before sending email verification")
            }
        }
    }
}