package kamil.degree.cardetailingapp.detailing.searchbusiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.extentions.Extentions.addNewItem
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class BusinessSearchViewModel: ViewModel() {

    private val firebaseRepo = FirebaseRepository

    fun getBusinesses2(callback: (List<Pair<Business, String>>) -> Unit) {
        firebaseRepo.getImageUriOfAllBusinesses(callback)
    }

}