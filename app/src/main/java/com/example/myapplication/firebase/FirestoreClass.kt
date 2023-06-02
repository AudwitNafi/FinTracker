package com.example.myapplication.firebase

import Expense
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.myapplication.acitivities.*
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

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