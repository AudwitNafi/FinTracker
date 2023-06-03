package com.example.myapplication.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.firebase.FirestoreClass
import com.example.myapplication.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val textViewSignIn: TextView = findViewById(R.id.textViewSignIn)
        textViewSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val signUpBtn = findViewById<Button>(R.id.btn_sign_up)
        signUpBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun showErrorSnackBar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        snackBar.show()
    }


    fun userRegisteredSuccess() {
        Toast.makeText(
            this, "You have successfully registered", Toast.LENGTH_LONG
        ).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun registerUser() {
        val etName = findViewById<EditText>(R.id.et_username)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirm = findViewById<EditText>(R.id.et_confirm)

        val name: String = etName.text.toString()
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()
        val confirm: String = etConfirm.text.toString()

        if (validateForm(name, email, password, confirm)) {
            // Check if password is strong
            if (!isPasswordStrong(password)) {
                showErrorSnackBar("Password should contain at least 8 characters, including uppercase and lowercase letters, numbers, and special characters.")
                return
            }

            // Check if email is valid
            if (!isValidEmail(email)) {
                showErrorSnackBar("Invalid email address.")
                return
            }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!

                        val user = User(firebaseUser.uid, name, registeredEmail)

                        FirestoreClass().registerUser(this, user)
                    } else {
                        showErrorSnackBar("Please fill out all the fields")
                    }
                }
        } else {
            showErrorSnackBar("Please fill out all the fields")
        }
    }

    private fun validateForm(name: String, email: String, password: String, confirm: String): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) return false
        return true
    }

    private fun isPasswordStrong(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
        return password.matches(passwordRegex.toRegex())
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}
