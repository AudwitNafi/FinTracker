package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val signUpName : EditText  = findViewById(R.id.signUpName)
        val signUpEmail : EditText  = findViewById(R.id.signUpEmail)
        val signUpPassword : EditText  = findViewById(R.id.signUpPassword)
        val signUpConfirmPassword : EditText  = findViewById(R.id.signUpConfirmPassword)
        val signUpBtn : Button = findViewById(R.id.signUpBtn)

        val signInText : TextView = findViewById(R.id.sign_in_textview)


        signInText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val password = signUpPassword.text.toString()
            val cPassword = signUpConfirmPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()){
                if(name.isEmpty()){
                    signUpName.error = "Enter your name"
                }
                if(email.isEmpty()){
                    signUpEmail.error = "Enter your email"
                }
                if(password.isEmpty()){
                    signUpPassword.error = "Enter your password"
                }
                if(cPassword.isEmpty()){
                    signUpConfirmPassword.error = "Confirm your password"
                }
                Toast.makeText(this , "Enter valid details" , Toast.LENGTH_SHORT).show()

            }else if (!email.matches(emailRegex.toRegex())){
                signUpEmail.error = "Enter valid email address"
                Toast.makeText(this , "Enter valid email address" , Toast.LENGTH_SHORT).show()
            }else if(password.length<6){
                signUpPassword.error = "Password should be more than 6 characters"
                Toast.makeText(this , "Enter valid password" , Toast.LENGTH_SHORT).show()
            }else if(password != cPassword){
                signUpConfirmPassword.error = "Passwords don't match, try again"
                Toast.makeText(this , "Passwords don't match, try again" , Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                        val users : Users = Users(name, email, auth.currentUser!!.uid)

                        databaseRef.setValue(users).addOnCompleteListener {
                            if(it.isSuccessful){
                                val intent = Intent(this, LoginActivity::class.java)
                            }else{
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}