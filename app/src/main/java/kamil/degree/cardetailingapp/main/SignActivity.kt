package kamil.degree.cardetailingapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kamil.degree.cardetailingapp.databinding.ActivitySignBinding
import kamil.degree.cardetailingapp.detailing.DrawerActivity
import kamil.degree.cardetailingapp.extentions.Extentions.longToast
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText

class SignActivity : AppCompatActivity() {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    private val viewModel: SignViewModel by viewModels()
    private lateinit var binding: ActivitySignBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    //firestore
    val cloud = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.createAccountBTN.setOnClickListener {
            createAccount(
                passwordText = binding.passwordET.useText(),
                confirmPasswordText = binding.confirmPasswordET.useText(),
                emailText = binding.emailET.useText()
            )
        }
        binding.signInBTN.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }

    private fun createAccount(passwordText: String, confirmPasswordText: String, emailText: String) {
        if (viewModel.passwordIsValid(passwordText, confirmPasswordText)) {

            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        shortToast("Account created successfully!")
                        viewModel.postUserData(emailText)
                        startActivity(Intent(this, DrawerActivity::class.java))
                        finish()
                    } else {
                        shortToast("Failed to Authenticate.")
                    }
                }
        } else {
            longToast("Password doesn't meet our requirements.")
        }
    }







}