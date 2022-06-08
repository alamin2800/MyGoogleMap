package com.example.mygooglemap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygooglemap.models.ProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationViewModel : ViewModel() {

    val fireAuth = FirebaseAuth.getInstance()
    var user = fireAuth.currentUser
    val AuthLiveData: MutableLiveData<Auth> = MutableLiveData()


    private val collectionProfile = "Profile"
    private val db = FirebaseFirestore.getInstance()
    val erroeMsgLiveData: MutableLiveData<String> = MutableLiveData()


    init {
        if (user != null) {
            AuthLiveData.value = Auth.AUTHENTICATED
        } else {
            AuthLiveData.value = Auth.UNAUTHENTICATED

        }
    }

    fun insertProfile(profileModel: ProfileModel) {
        val docRef = db.collection(collectionProfile).document()
        profileModel.id = docRef.id
        docRef.set(profileModel)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                erroeMsgLiveData.value = "could not add"
            }
    }


    fun registration(email: String, password: String, callback: () -> Unit) {
        fireAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    AuthLiveData.value = Auth.AUTHENTICATED
                    user = fireAuth.currentUser
                    callback()
                }
            }
            .addOnFailureListener {
                erroeMsgLiveData.value = it.localizedMessage
            }
    }

}