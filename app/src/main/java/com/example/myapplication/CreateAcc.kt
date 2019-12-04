package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_acc.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CreateAcc :  BaseActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        val btn_click_login = findViewById(R.id.button2) as Button
// set on-click listener
        btn_click_login.setOnClickListener {
            createUser()
        }
    }

    fun createUser() {

        val email = registerEmail.text.toString()
        val password = registerPasswd.text.toString()
        val testPasswd = registerPasswd2.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(baseContext, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
        if(password != testPasswd){
            Toast.makeText(baseContext, "The passwords entered are different", Toast.LENGTH_SHORT).show()
            return
        }
/*if (selectedPhotoUri == null){
Toast.makeText(context, "Please select your profile image", Toast.LENGTH_SHORT).show()
return
}*/
        Log.d("SignUp"
            , "Attempting to create user with email: $email")
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (!it.isSuccessful)  return@addOnCompleteListener
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(
                        "SignUp"
                        , "Successfully created user with uid: ${it.result!!.user!!.uid}"
                    )
                saveUserToFirebaseDatabase()

            }
            .addOnFailureListener{
                        Log.d("SignUp"
                            , "Failed to create user: ${it.message}")
                        Toast.makeText(baseContext, "Failed to create user: ${it.message}"
                            , Toast.LENGTH_SHORT).show()
                    }
    }
    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = UserData(uid, userName.text.toString(), registerEmail.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("SignUp"
                    , "saved the user to Firebase Database")
// launch the Main activity, clear back stack,
// not going back to login activity with back press button
                val intent = Intent(baseContext, Homepage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("SignUp"
                    , "Failed to set value to database: ${it.message}")
            }
    }
}









