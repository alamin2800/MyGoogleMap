package com.example.mygooglemap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel():ViewModel() {
    val fireAuth = FirebaseAuth.getInstance()
    var user = fireAuth.currentUser
    val AuthLiveData: MutableLiveData<Auth> = MutableLiveData()
    val erroeMsgLiveData: MutableLiveData<String> = MutableLiveData()


    init {
        if (user != null) {
            AuthLiveData.value = Auth.AUTHENTICATED
        } else {
            AuthLiveData.value = Auth.UNAUTHENTICATED

        }
    }

    fun login(email: String, password: String) {
        fireAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    AuthLiveData.value = Auth.AUTHENTICATED
                    user = fireAuth.currentUser
                }
            }
            .addOnFailureListener {
                erroeMsgLiveData.value = it.localizedMessage
            }
    }

    fun logout() {
        if (user != null) {
            fireAuth.signOut()
            AuthLiveData.value = Auth.UNAUTHENTICATED
        }
    }
}

enum class Auth {
    AUTHENTICATED, UNAUTHENTICATED
}