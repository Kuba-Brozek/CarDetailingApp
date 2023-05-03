package kamil.degree.cardetailingapp.detailing.one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class OneViewModel : ViewModel() {

    private val firebaseRepo = FirebaseRepository()

    fun addBusiness(business: Business) {
        return firebaseRepo.addBusiness(business)
    }

    fun changeBusinessFlag(){
        return firebaseRepo.changeUserBusinessFlag()
    }

}