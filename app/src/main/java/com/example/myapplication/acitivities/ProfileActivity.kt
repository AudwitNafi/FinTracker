package com.example.myapplication.acitivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.firebase.FirestoreClass
import com.example.myapplication.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class ProfileActivity : AppCompatActivity(), FirestoreClass.ProfilePictureSaveListener {
    // ...
    private lateinit var mUser: User
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null


    override fun onProfilePictureSaved(imageUri: Uri) {
        // Update the profile picture view with the new image
        val profilePictureImageView: ImageView = findViewById(R.id.profile_picture)
        profilePictureImageView.setImageURI(imageUri)
    }

    override fun onProfilePictureSaveFailed(error: Exception) {
        // Handle the failure to save the profile picture
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

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

        val signOutBtn: Button = findViewById(R.id.temp_sign_out_btn)
        signOutBtn.setOnClickListener {
            signOut()
        }

        val saveChangesBtn: Button = findViewById(R.id.save_changes_btn)
        saveChangesBtn.setOnClickListener {
            updateUserData()
        }

        val editProfilePictureButton: ImageView = findViewById(R.id.edit_profile_picture_button)
        editProfilePictureButton.setOnClickListener {
            chooseProfilePicture()
        }
    }

    private fun updateUserData() {
        val etName: EditText = findViewById(R.id.et_change_name)
        val etBudget: EditText = findViewById(R.id.et_set_budget)

        if (etName.text.toString() != mUser.name && etName.text.toString() != "") {
            FirestoreClass().updateUserName(this, etName.text.toString())
        }

        FirestoreClass().updateBudget(this, etBudget.text.toString())

        // Check if an image is selected
        if (selectedImageUri != null) {
            FirestoreClass().saveProfilePicture(selectedImageUri!!, this)
        }

        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        finish()
    }


    private fun chooseProfilePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!

            // Set the selected image to the profile picture ImageView
            val profilePictureImageView: ImageView = findViewById(R.id.profile_picture)
            profilePictureImageView.setImageURI(imageUri)

            // Save the image URI to Firestore
            FirestoreClass().saveProfilePicture(imageUri, this)
        }
    }



    fun setUserData(user: User) {
        mUser = user
        val dp: ImageView = findViewById(R.id.profile_picture)
        val username: TextView = findViewById(R.id.tv_profile_username)

        username.text = user.name

        // Set the profile picture
        if (user.image.isNotEmpty()) {
            val profilePictureUri = Uri.parse(user.image)
            dp.setImageURI(profilePictureUri)
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
        startActivity(intent)
        finish()
    }
}
