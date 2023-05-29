package com.example.myapplication.firebase

import android.util.Log
import com.example.myapplication.acitivities.LoginActivity
import com.example.myapplication.acitivities.SignUpActivity
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()
    fun registerUser(activity: SignUpActivity, userInfo: User)
    {
        mFireStore.collection("Users")
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                e->
                Log.e(activity.javaClass.simpleName, "Error registering user", e)
            }
    }

    fun signInUser(activity: LoginActivity){
        mFireStore.collection("Users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document->
                val loggedInUser = document.toObject(User::class.java)
                if (loggedInUser != null) {
                    activity.signInSuccess(loggedInUser)
                }
            }.addOnFailureListener {
                    e->
                Log.e("Sign In", "Error signing in user", e)
            }
    }
    fun getCurrentUserId(): String {
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null){
            currentUserId = currentUser.uid
        }
        return  currentUserId
    }
}