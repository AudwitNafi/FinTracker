package com.example.myapplication.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.acitivities.ReportGenerateActivity
import com.example.myapplication.firebase.FirestoreClass
import com.example.myapplication.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class ProfileActivity : AppCompatActivity() {
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        FirestoreClass().signInUser(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.nav_profile

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_expenses -> {
                    startActivity(Intent(applicationContext, HomePage::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> return@OnNavigationItemSelectedListener true
                R.id.nav_reports -> {
                    startActivity(Intent(applicationContext, ReportGenerateActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        var signOutBtn : Button = findViewById(R.id.temp_sign_out_btn)
        signOutBtn.setOnClickListener {
            signOut()
        }

        var saveChangesBtn : Button = findViewById(R.id.save_changes_btn)
        saveChangesBtn.setOnClickListener {
            updateUserData()
        }
    }

    fun updateUserData()
    {
        val etName: EditText = findViewById(R.id.et_change_name)
        val etBudget: EditText = findViewById(R.id.et_set_budget)

        if(etName.text.toString() != mUser.name && etName.text.toString()!="")
            FirestoreClass().updateUserName(this, etName.text.toString())
        FirestoreClass().updateBudget(this, etBudget.text.toString())
        finish()
    }
    fun setUserData(user: User){
        mUser = user
        val dp : ImageView = findViewById(R.id.profile_picture)
        val username : TextView = findViewById(R.id.tv_profile_username)

        username.text = user.name
    }

    private fun signOut()
    {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
        startActivity(intent)
        finish()
    }
}

