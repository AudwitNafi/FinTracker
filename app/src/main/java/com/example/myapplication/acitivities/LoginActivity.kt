package com.example.myapplication.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = FirebaseAuth.getInstance()

        val signUpBtn : TextView = findViewById(R.id.btn_sign_up)
        val signInBtn : Button = findViewById(R.id.btn_sign_in)

        signInBtn.setOnClickListener {
            signInUser()
        }

        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun signInSuccess(user: User){
        startActivity(Intent(this, HomePage::class.java))
        finish()
    }
    private fun signInUser()
    {
        val etEmail = findViewById<EditText>(R.id.et_email_sign_in)
        val etPassword = findViewById<EditText>(R.id.et_password_sign_in)

        val email : String = etEmail.text.toString()
        val password : String = etPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign in", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, HomePage::class.java)
                    if (user != null) {
                        intent.putExtra("username", user.displayName)
                    }
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign in", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }


    }
}