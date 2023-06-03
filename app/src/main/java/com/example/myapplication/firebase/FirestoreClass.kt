package com.example.myapplication.firebase

import Expense
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.acitivities.*
import com.example.myapplication.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    interface ProfilePictureSaveListener {
        fun onProfilePictureSaved(imageUri: Uri)
        fun onProfilePictureSaveFailed(error: Exception)
    }


    fun saveProfilePicture(imageUri: Uri, listener: ProfilePictureSaveListener) {
        val userId = getCurrentUserId()
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_pictures/$userId.jpg")
        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val userMap = hashMapOf<String, Any>(
                    "image" to downloadUri.toString()
                )
                val db = FirebaseFirestore.getInstance()
                val userRef = db.collection("users").document(userId)
                userRef.update(userMap)
                    .addOnSuccessListener {
                        // Image URL saved successfully in Firestore
                        listener.onProfilePictureSaved(imageUri)
                    }
                    .addOnFailureListener { e ->
                        // Failed to update user document
                        // You can handle the error or show an error message here
                        listener.onProfilePictureSaveFailed(e)
                    }
            } else {
                // Failed to upload image to Firebase Storage
                // You can handle the error or show an error message here
                task.exception?.let { listener.onProfilePictureSaveFailed(it) }
            }
        }
    }



    fun rewriteExpenses(context: Context, expenses: List<Expense>) {
        val db = FirebaseFirestore.getInstance()
        val userId = getCurrentUserId()

        for (expense in expenses) {
            db.collection("Expenses")
                .document(expense.id.toString())
                .set(expense)
                .addOnSuccessListener {
                    // Handle success
                }
                .addOnFailureListener { exception ->
                    // Handle failure
                }
        }
    }

    fun deleteExpense(activity: HomePage, expense: Expense) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Expenses")
            .document(expense.id!!)
            .delete()
            .addOnSuccessListener {
                Log.d(activity.javaClass.simpleName, "Expense deleted successfully")
                // Perform any additional actions or callbacks here, if needed
            }
            .addOnFailureListener { exception ->
                Log.e(activity.javaClass.simpleName, "Error deleting expense: $exception")
            }
    }



    fun getExpenses(activity: HomePage) {
        val db = FirebaseFirestore.getInstance()
        val userId = getCurrentUserId()

        db.collection("Expenses")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val expenses = querySnapshot.toObjects(Expense::class.java)
                activity.displayExpenses(expenses as ArrayList<Expense>)
            }
            .addOnFailureListener { exception ->
                Log.e(activity.javaClass.simpleName, "Error retrieving expenses: $exception")
            }
    }

    fun updateUserName(activity: ProfileActivity, username: String)
    {
        val userRef = mFireStore.collection("Users").document(getCurrentUserId())
        userRef
            .update("name", username)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    fun updateBudget(activity: Activity, budget: String)
    {
        val userRef = mFireStore.collection("Users").document(getCurrentUserId())
        userRef
            .update("budget", budget)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

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

    fun signInUser(activity: Activity){
        mFireStore.collection("Users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document->
                val loggedInUser = document.toObject(User::class.java)!!

                when(activity){
                    is LoginActivity ->{
                        activity.signInSuccess(loggedInUser)
                    }
                    is HomePage ->{
                        activity.setUserData(loggedInUser)
                    }
                    is ProfileActivity ->{
                        activity.setUserData(loggedInUser)
                    }
                }
            }.addOnFailureListener {
                    e->
                Log.e("Sign In", "Error signing in user", e)
            }
    }

    fun addExpense(activity: AddExpenseActivity, expense: Expense){
        mFireStore.collection("Expenses")
            .document()
            .set(expense, SetOptions.merge())
            .addOnSuccessListener{
                Log.e(activity.javaClass.simpleName, "Expense added successfully")
                Toast.makeText(activity,"Expense added successfully", Toast.LENGTH_LONG).show()
                activity.finish()
            }.addOnFailureListener {
                e ->
                Log.e(
                    activity.javaClass.simpleName, "Error while adding expense", e
                )
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