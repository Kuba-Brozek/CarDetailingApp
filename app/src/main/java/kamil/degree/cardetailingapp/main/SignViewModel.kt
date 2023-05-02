package kamil.degree.cardetailingapp.main

import android.app.Activity
import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.extentions.Extentions.shortToast
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class SignViewModel: ViewModel() {

    private val firebaseRepo = FirebaseRepository()


    fun passwordIsValid(passwordText: String, confirmPasswordText: String): Boolean {
        return if (passwordText.isEmpty()) false
        else if (passwordText.length < 8) {
            false
        } else passwordText == confirmPasswordText
    }

}