package kamil.degree.cardetailingapp.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.ActivityLoginBinding
import kamil.degree.cardetailingapp.detailing.DrawerActivity
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText
import kamil.degree.cardetailingapp.utils.FirebaseUtils
import kamil.degree.cardetailingapp.utils.Start

class LoginActivity : AppCompatActivity() {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    private val viewModel: SignViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signInBTN.setOnClickListener {
            signIn(
                binding.emailET.useText(),
                binding.passwordET.useText()

            )
        }
        binding.createAccountBTN.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
        }



    }

    private fun signIn(emailText: String, passwordText: String){
        Log.d("asd", emailText)
        Log.d("asd", passwordText)
        if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
            FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        shortToast("You successfully signed in!")
                        startActivity(Intent(this, DrawerActivity::class.java))
                        finish()
                    } else {
                        shortToast("Account with that credentials does not exist.")
                    }
                }
        } else {
            shortToast("Enter proper credentials")
        }
    }





}