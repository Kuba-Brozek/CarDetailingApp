package kamil.degree.cardetailingapp.repo

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kamil.degree.cardetailingapp.model.Business
import kamil.degree.cardetailingapp.model.PhoneNumber
import kamil.degree.cardetailingapp.model.Service
import kamil.degree.cardetailingapp.model.User
import kamil.degree.cardetailingapp.utils.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

object FirebaseRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    private val cloud = Firebase.firestore
    val imageRef = Firebase.storage.reference

    fun postUserData(email: String) {

        val userHashMap = hashMapOf(
            "id" to firebaseAuth.currentUser!!.uid,
            "email" to email,
            "username" to email.split("@").first(),
            "birthDate" to "01.01.1980",
            "hasBusiness" to false
        )

        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS)
            .set(userHashMap)
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
                if (task.isSuccessful) {
                    val user = task.result!!.toObject<User>()!!
                    callback(user)
                }
            }
    }

    fun getBusinessInfo(callback: (Business) -> Unit) {
        cloud.collection(Const.BUSINESS_INFO).document(firebaseAuth.currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val business = task.result!!.toObject<Business>()!!
                    Log.d(Const.BUSINESS_TAG, business.toString())
                    callback(business)
                }
            }
    }

    fun updateUserData(user: User) {
        val userHashMap = hashMapOf(
            "id" to user.id,
            "email" to user.email,
            "username" to user.username,
            "birthDate" to user.birthDate,
            "hasBusiness" to user.hasBusiness
        )
        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS)
            .set(userHashMap)
            .addOnSuccessListener {
                Log.d(Const.USER_TAG, "User data modified.")
                firebaseAuth.currentUser!!.updateEmail(user.email!!)
            }
            .addOnFailureListener { exception ->
                Log.w(Const.USER_TAG, "Error modifying user data", exception)
            }
    }

    fun changeUserBusinessFlag(callback: (Business) -> Unit) {
        getUserData {
            val user = it
            user.hasBusiness = true
            cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).set(user)
                .addOnSuccessListener {
                    Log.d(Const.USER_TAG, "User data modified.")
                    addBusiness(callback)
                }
                .addOnFailureListener { exception ->
                    Log.w(Const.USER_TAG, "Error modifying user data", exception)
                }
        }
    }


    fun addBusiness(callback: (Business) -> Unit) {
        val business = Business()
        val service = Service("Empty service", 999)
        business.services = mutableListOf(
            service
        )
        val businessHashMap = hashMapOf(
            "name" to business.name,
            "services" to business.services,
            "description" to business.description
        )
        getUserData {
            val user = it
            user.hasBusiness = true
            updateUserData(user)
            cloud.collection(Const.BUSINESS_INFO).document(firebaseAuth.currentUser!!.uid)
                .set(businessHashMap)
                .addOnSuccessListener {
                    callback(business)
                    Log.d(Const.BUSINESS_TAG, "Business added successfully.")
                }
                .addOnFailureListener { exception ->
                    Log.w(Const.BUSINESS_TAG, "Error adding new business", exception)
                }
        }
    }

    fun modifyBusiness(business: Business) {
        val businessHashMap = hashMapOf(
            "name" to business.name,
            "services" to business.services,
            "description" to business.description,
            "address" to business.address
        )
        getUserData { userInfo ->
            val user = userInfo
            user.hasBusiness = true
            updateUserData(user)
            cloud.collection(Const.BUSINESS_INFO).document(firebaseAuth.currentUser!!.uid)
                .set(businessHashMap)
                .addOnSuccessListener {
                    Log.d(Const.BUSINESS_TAG, "Business added successfully.")
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    Log.w(Const.BUSINESS_TAG, "Error adding new business", exception)
                }
        }

    }

    fun saveService(business: Business) {
        cloud.collection(Const.BUSINESS_INFO).document(firebaseAuth.currentUser!!.uid)
            .set(business).addOnSuccessListener {
                Log.d(Const.BUSINESS_TAG, "Business services updated correctly")
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
                Log.w(Const.BUSINESS_TAG, "Unexpected Error", exception)
            }
    }

    fun getAllBusinesses(returnedList: (List<Pair<QueryDocumentSnapshot, Business>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            cloud.collection(Const.BUSINESS_INFO).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val businessList = mutableListOf<Pair<QueryDocumentSnapshot, Business>>()
                        CoroutineScope(Dispatchers.IO).launch {
                            for (doc in it.result) {
                                withContext(Dispatchers.IO) {
                                    val business = doc.toObject<Business>()
                                    withContext(Dispatchers.IO) {
                                    }
                                    val pair = Pair(doc, business)
                                    businessList.add(pair)
                                }
                            }
                            returnedList(businessList)

                        }

                    }
                }
        }
    }

    fun getImageUriOfAllBusinesses(callback: (List<Pair<Business, String>>) -> Unit) {
        val callbackList = mutableListOf<Pair<Business, String>>()
        getAllBusinesses {
            CoroutineScope(Dispatchers.IO).launch {
                it.forEach {
                    val image = imageRef.child(it.first.id).listAll()
                        .await().items.firstOrNull()
                    val imageUri= image?.downloadUrl?.await()?.toString() ?: ""
                    callbackList.add(Pair(it.second, imageUri))
                }
                callback(callbackList)
            }
        }
    }


    fun getBusinessId(business: Business, callback: (String) -> Unit) {
        var filteredList =
            listOf<Pair<QueryDocumentSnapshot, Business>>()
        getAllBusinesses {
            filteredList = it.filter { b ->
                b.second == business
            }
            callback(filteredList[0].first.id)
        }
    }

    fun getUserById(uid: String, callback: (User) -> Unit) {
        cloud.collection(uid).document(Const.USER_DETAILS).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result!!.toObject<User>()!!
                    callback(user)
                }
            }
    }

    fun deleteAccount(callback: (Boolean) -> Unit) {
        firebaseAuth.currentUser!!.delete().addOnCompleteListener {
            if (it.isSuccessful) {
                deleteBusiness()
                deleteUserDetails()
                Log.i("Account", "Account deleted succesfully")
                callback(true)
            } else {
                Log.w("Account", "Account not deleted ${it.exception?.message}")
                callback(false)
            }
        }
    }

    fun deleteBusiness() {
        cloud.collection(Const.BUSINESS_INFO).document(firebaseAuth.currentUser!!.uid).delete()
    }

    fun deleteUserDetails() {
        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.USER_DETAILS).delete()
    }

    fun logOut() {
        firebaseAuth.signOut()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun setPhoneNumber(phoneNumber: String) {
        cloud.collection(firebaseAuth.currentUser!!.uid).document(Const.PHONE_NUMBER).set(
            hashMapOf(
                "phoneNumber" to phoneNumber
            )
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(Const.PHONE_INFO,"Number added succesfully")
            } else {
                Log.w(Const.PHONE_INFO, "Error adding number, ${it.exception?.message}")
            }
        }
    }

    fun getPhoneNumber(uid: String, callback: (PhoneNumber) -> Unit) {
        cloud.collection(uid).document("PhoneNumber").get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    callback(it.result.toObject<PhoneNumber>()!!)
                } else {
                    Log.w("PhoneNumber", it.exception?.message?: "Exception with no message")
                }
            }
    }

    fun changeEmail(email: String) {
        firebaseAuth.currentUser!!.updateEmail(email)
        getUserData {
            val user = it
            user.email = email
            updateUserData(user)
        }
    }

    fun changePassword(password: String) {
        firebaseAuth.currentUser!!.updatePassword(password)
    }

    fun sendEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Log.i("RESET" ,"Password reset email sent to the user.")
            }.addOnFailureListener {
                Log.i("RESET", "Password reset email not delivered to the user.")
            }
    }




}

