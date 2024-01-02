package com.example.scribblenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scribblenotes.databinding.ActivityForgotpasswordBinding
import com.google.firebase.auth.FirebaseAuth

class Forgot_Password : AppCompatActivity() {
    private lateinit var binding: ActivityForgotpasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        binding.reset.setOnClickListener {
            val email = binding.email.text.toString()
            if(email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, " E-mail sent successfully to reset your password", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this,Login::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Please enter E-mail Address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}