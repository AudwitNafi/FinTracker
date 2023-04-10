package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInEmail : EditText = findViewById(R.id.signInEmail)
        val signInPassword : EditText = findViewById(R.id.signInPassword)
        val signInBtn : Button = findViewById(R.id.signInBtn)


        val signUpText : TextView = findViewById(R.id.signUpText)
        signUpText.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if( email.isEmpty() || password.isEmpty() ) {
                if (email.isEmpty()) {
                    signInEmail.error = "Enter your email"
                }
                if (password.isEmpty()) {
                    signInPassword.error = "Enter your password"
                }
            }else if (!email.matches(emailRegex.toRegex())) {
                signInEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

    }

}