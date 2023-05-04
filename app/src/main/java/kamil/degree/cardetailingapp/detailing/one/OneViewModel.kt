package kamil.degree.cardetailingapp.detailing.one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class OneViewModel : ViewModel() {

    private val firebaseRepo = FirebaseRepository()


    fun modifyBusiness(business: Business) {
        return firebaseRepo.modifyBusiness(business)
    }

    fun changeBusinessFlag(callback: (Business) -> Unit){
        return firebaseRepo.changeUserBusinessFlag(callback)
    }

    fun getUserData(callback: (User) -> Unit) {
        return firebaseRepo.getUserData(callback)
    }

    fun getBusinessData(callback: (Business) -> Unit) {
        return firebaseRepo.getBusinessInfo(callback)
    }

}