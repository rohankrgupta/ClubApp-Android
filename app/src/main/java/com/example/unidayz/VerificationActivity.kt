package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class VerificationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        auth = FirebaseAuth.getInstance()

        val re : Button = findViewById(R.id.res_email)
        val user: FirebaseUser? = auth.currentUser
        val done: Button = findViewById(R.id.done)

        re.setOnClickListener{
            user?.sendEmailVerification()
            Toast.makeText(baseContext, "Verification email Re-sent",
                Toast.LENGTH_SHORT).show()
        }
        done.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
