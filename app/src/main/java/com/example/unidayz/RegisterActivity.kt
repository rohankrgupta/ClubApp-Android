package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.editText01
import kotlinx.android.synthetic.main.activity_register.editText02

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        val signIn: TextView = findViewById(R.id.sign_inpg)
        val regUser: Button = findViewById(R.id.register)


        regUser.setOnClickListener{
           signUpUser()
        }
        signIn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun signUpUser(){



        if(editText03.text.toString().isEmpty()){
            editText03.error = "Please enter Name"
            editText03.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editText01.text.toString()).matches()){
            editText01.error = "Please enter valid Email "
            editText01.requestFocus()
            return
        }
        if(editText02.text.toString().isEmpty()){
            editText02.error = "Please enter Password"
            editText02.requestFocus()
            return
        }
        if(editText04.text.toString().isEmpty()){
            editText04.error = "Please Re-enter Password"
            editText04.requestFocus()
            return
        }


        auth.createUserWithEmailAndPassword(editText01.text.toString(), editText04.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val progressbar = findViewById<ProgressBar>(R.id.progressBarRegister)
                    progressbar.visibility = View.VISIBLE
                    val user:FirebaseUser? = auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(baseContext, "Verification email sent",
                                    Toast.LENGTH_SHORT).show()
                                uploadDataToFireBase()
                                startActivity(Intent(this, VerificationActivity::class.java))
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(baseContext, "Please Try Again",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadDataToFireBase(){
        val uid  = FirebaseAuth.getInstance().uid ?:""
        val data = hashMapOf(
            "useruid" to uid,
            "username" to editText03.text.toString(),
            "useremail" to editText01.text.toString(),
            "userpassword" to editText02.text.toString()
        )
        db.collection("users").document(uid)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { Log.d("RegisterActivity", "DocumentSnapshot successfully written!") }

    }
}


