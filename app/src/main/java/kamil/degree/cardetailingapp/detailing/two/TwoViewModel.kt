package kamil.degree.cardetailingapp.detailing.two

import android.net.Uri
import androidx.lifecycle.ViewModel
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.repo.FirebaseRepository

class TwoViewModel: ViewModel() {
    private val firebaseRepo = FirebaseRepository()
    fun getBusinesses(returnedList: (List<Pair<Business, String>>) -> Unit){
        return firebaseRepo.getImageUriOfAllBusinesses(returnedList)
    }

}