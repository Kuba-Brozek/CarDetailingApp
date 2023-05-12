package kamil.degree.cardetailingapp.detailing.managebusiness

import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class BusinessViewModel : ViewModel() {


    private val firebaseRepo = FirebaseRepository

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

    fun saveService(business: Business) {
        return firebaseRepo.saveService(business)
    }

}