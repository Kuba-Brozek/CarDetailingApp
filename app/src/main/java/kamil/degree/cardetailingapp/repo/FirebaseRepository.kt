package kamil.degree.cardetailingapp.repo

import android.util.Log
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.utils.Const
import kamil.degree.cardetailingapp.utils.FirebaseUtils.cloud
import kamil.degree.cardetailingapp.utils.FirebaseUtils.currentUser

class FirebaseRepository {


    fun postUserData(email: String) {

        val userHashMap  = hashMapOf(
            "id" to currentUser!!.uid,
            "email" to email,
            "username" to email.split("@").first(),
            "birthDate" to "01.01.1980",
            "hasBusiness" to false
        )

        cloud.collection(currentUser.uid).document(Const.USER_DETAILS).set(userHashMap)
            .addOnSuccessListener {
                Log.d(Const.USER_TAG, "User data added.")
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error adding user data", exception)
            }
    }

    fun getUserData(callback: (User) -> Unit) {
        cloud.collection(currentUser!!.uid).document(Const.USER_DETAILS).get()
            .addOnSuccessListener { result ->
                val user = result.toObject(User::class.java)!!
                callback(user)
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error getting documents.", exception)
            }
    }

    fun updateUserData (user: User) {
        val userHashMap  = hashMapOf(
            "id" to user.id,
            "email" to user.email,
            "username" to user.username,
            "birthDate" to user.birthDate,
            "hasBusiness" to user.hasBusiness
        )
        cloud.collection(currentUser.toString()).document(Const.USER_DETAILS).set(userHashMap)
            .addOnSuccessListener {
                Log.d(Const.USER_TAG, "User data modified.")
                currentUser!!.updateEmail(user.email)
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error modifying user data", exception)
            }
    }


    fun addBusiness(business: Business) {
        val businessHashMap = hashMapOf(
            "name" to business.name,
            "services" to business.services,
            "description" to business.description
        )
        getUserData {
            val user = it
            user.hasBusiness = true
            updateUserData(user)
            cloud.collection(currentUser!!.uid).document(Const.BUSINESS_INFO)
                .set(businessHashMap)
                .addOnSuccessListener {
                    Log.d(Const.BUSINESS_TAG, "Business added succesfully.")
                }
                .addOnFailureListener { exception ->
                    Log.w(Const.BUSINESS_TAG, "Error adding new business", exception)
                }
        }
    }


}