package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val uid  = FirebaseAuth.getInstance().uid ?:""
        val docRef = db.collection("users").document(uid)

        docRef.get()
            .addOnSuccessListener { task ->
                val document = task.data

                if (document != null) {
                    if(document.getValue("useruniversity") != null){
                        startActivity(Intent(this, DashboardActivity::class.java))
                    }
                }else{
                    uploadInitialData()
                }
            }


        val cont : Button = findViewById(R.id.cont_btn)
        cont.setOnClickListener{
            uploadUpdatedInfoData()
        }

    }

    private fun uploadUpdatedInfoData(){

        val uid  = FirebaseAuth.getInstance().uid ?:""
        val profUni : String = college_name.text.toString()
        val profDeg : String = degree.text.toString()
        val uniSt : String = editTextNumber.text.toString()
        val uniEnd:String = editTextNumber2.text.toString()

        if(profUni.isEmpty()){
            college_name.error = "Please Enter Your University"
            college_name.requestFocus()
            return
        }

        if(profDeg.isEmpty()){
            degree.error = "Please Enter your Degree"
            degree.requestFocus()
            return
        }
        if(uniSt.isEmpty()){
            editTextNumber.error = "Please Enter Valid Year"
            editTextNumber.requestFocus()
            return
        }
        if(uniEnd.isEmpty()){
            editTextNumber2.error = "Please Enter Valid  Year"
            editTextNumber2.requestFocus()
            return
        }


        val data = hashMapOf(
            "useruniversity" to profUni,
            "userdegree" to profDeg,
            "useruniyears" to uniSt,
            "useruniyeare" to uniEnd
        )
        db.collection("users").document(uid)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { Log.d("InfoActivity", "Document successfully updated!") }
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun uploadInitialData(){
        val uid  = FirebaseAuth.getInstance().uid ?:""
        val task = GoogleSignIn.getLastSignedInAccount(this)
        val prName = task?.displayName.toString()
        val prEmail = task?.email.toString()
        val prPhoto = task?.photoUrl.toString()
        val info = hashMapOf(
            "userprofilepic" to prPhoto ,
            "username" to prName ,
            "useremail" to prEmail,
            "uid" to uid
        )
        db.collection("users").document(uid)
            .set(info, SetOptions.merge())
            .addOnSuccessListener { Log.d("InfoActivity", " Google Sign In Details successfully written!") }
    }
}












