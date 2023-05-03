package kamil.degree.cardetailingapp.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.utils.Const

class FirebaseRepository {
    //auth
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //firestore
    private val cloud = Firebase.firestore

    fun postUserData(email: String) {

        val userHashMap  = hashMapOf(
            "id" to firebaseAuth.currentUser!!.uid,
            "email" to email,
            "username" to email.split("@").first(),
            "birthDate" to "01.01.1980",
            "hasBusiness" to false
        )

        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).set(userHashMap)
            .addOnSuccessListener {
                Log.d(Const.USER_TAG, "User data added.")
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error adding user data", exception)
            }
    }

    fun getUserData(callback: (User) -> Unit) {
        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val user = task.result!!.toObject<User>()!!
                    callback(user)
                }
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
        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).set(userHashMap)
            .addOnSuccessListener {
                Log.d(Const.USER_TAG, "User data modified.")
                firebaseAuth.currentUser!!.updateEmail(user.email!!)
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error modifying user data", exception)
            }
    }

    fun changeUserBusinessFlag () {
        getUserData {
        val user = it
            user.hasBusiness = true
            cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).set(user)
                .addOnSuccessListener {
                    Log.d(Const.USER_TAG, "User data modified.")
                }
                .addOnFailureListener { exception ->
                    Log.w(Const.USER_TAG, "Error modifying user data", exception)
                }
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
            cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.BUSINESS_INFO)
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