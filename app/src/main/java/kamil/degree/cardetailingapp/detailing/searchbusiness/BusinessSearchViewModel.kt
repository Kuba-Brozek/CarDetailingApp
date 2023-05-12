package kamil.degree.cardetailingapp.detailing.searchbusiness

import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class BusinessSearchViewModel: ViewModel() {
    private val firebaseRepo = FirebaseRepository
    fun getBusinesses(returnedList: (List<Pair<Business, String>>) -> Unit){
        return firebaseRepo.getImageUriOfAllBusinesses(returnedList)
    }

}