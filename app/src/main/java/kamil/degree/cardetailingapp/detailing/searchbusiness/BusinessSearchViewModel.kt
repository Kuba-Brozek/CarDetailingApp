package kamil.degree.cardetailingapp.detailing.searchbusiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.QueryDocumentSnapshot
import kamil.degree.cardetailingapp.extentions.Extentions.addNewItem
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.repo.FirebaseRepository
import kotlinx.coroutines.launch

class BusinessSearchViewModel: ViewModel() {

    private val firebaseRepo = FirebaseRepository

    fun getBusinesses(callback: (List<Pair<Business, String>>) -> Unit) {
        firebaseRepo.getImageUriOfAllBusinesses(callback)
    }

    fun getBusinessId(business: Business, callback: (String) -> Unit){
        return firebaseRepo.getBusinessId(business, callback)
    }

    fun filterBusinessList(businessList: List<Pair<Business, String>>, predicate: String): List<Pair<Business, String>> {
        return businessList.filter {
                pair -> pair.first.services
            .map { it.name }.any { string -> string?.contains(predicate) ?: false }
        }
    }


}