package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to button
        val btn_click_acc = findViewById(R.id.button) as Button
// set on-click listener
        btn_click_acc.setOnClickListener {
            val intent = Intent(this, CreateAcc::class.java)
            startActivity(intent)
        }
        val btn_click_login = findViewById(R.id.button2) as Button
// set on-click listener
        btn_click_login.setOnClickListener {
            login()
        }
    }

    //login
    fun login() {
        auth.signInWithEmailAndPassword(email.getText().toString(), passwd.getText().toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                } else {
                    Log.w("aaa", "signInWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed: "+task.exception,
                        Toast.LENGTH_SHORT).show()
                }
                // ...
            }
    }

}
