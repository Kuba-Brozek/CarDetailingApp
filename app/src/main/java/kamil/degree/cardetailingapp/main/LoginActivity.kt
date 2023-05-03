package kamil.degree.cardetailingapp.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kamil.degree.cardetailingapp.databinding.ActivityLoginBinding
import kamil.degree.cardetailingapp.detailing.DrawerActivity
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.extentions.Extentions.useText

class LoginActivity : AppCompatActivity() {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    private val viewModel: SignViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    //firestore
    val cloud = Firebase.firestore

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
            firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
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