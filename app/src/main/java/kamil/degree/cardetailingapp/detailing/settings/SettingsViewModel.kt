package kamil.degree.cardetailingapp.detailing.settings

import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class SettingsViewModel : ViewModel() {

    private val firebaseRepository = FirebaseRepository

    fun logOut() {
        return firebaseRepository.logOut()
    }

    fun deleteAccount(callback: (Boolean) -> Unit) {
        return firebaseRepository.deleteAccount(callback)
    }

    fun setPhoneNumber(phoneNumber: String) {
        return firebaseRepository.setPhoneNumber(phoneNumber)
    }

    fun deleteBusiness() {
        return firebaseRepository.deleteBusiness()
    }

    fun changeEmail(email: String) {
        return firebaseRepository.changeEmail(email)
    }

    fun changePassword(password: String) {
        return firebaseRepository.changePassword(password)
    }

    fun getUserData(callback: (User) -> Unit) {
        return firebaseRepository.getUserData(callback)
    }

}