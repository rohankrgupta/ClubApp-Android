package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_new_msg.*
import kotlinx.android.synthetic.main.activity_register.*

class NewMsgActivity : AppCompatActivity() {

    var msgDetails:String = ""
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_msg)

        val msgSend : Button = findViewById(R.id.msg_send)
        val msgBack : Button = findViewById(R.id.msg_back)

        msgBack.setOnClickListener{
            startActivity(Intent(this, MessageActivity::class.java))
            finish()
        }

        msgSend.setOnClickListener{
            uploadToDatabase()
        }



    }

    private fun uploadToDatabase(){
        if(msg_des.text.toString().isEmpty()){
            editText03.error = "Please enter Name"
            editText03.requestFocus()
            return
        }
        msgDetails = msg_des.text.toString()
        saveMsgToDatabase()
    }


    private fun saveMsgToDatabase(){
        val uid  = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val docRef = db.collection("users").document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("NewMsgActivity", "DocumentSnapshot data: ${document.data}")
                    Log.d("NewMsgActivity", "DocumentSnapshot data: ${document.get("username")}")
                    val username = document.get("username").toString()
                    val uni = document.get("useruniversity").toString()
                    val profileImg = document.get("userprofilepic").toString()
                    val place = document.get("useruniPlace").toString()
                    val user = msgInfo(uid, username, msgDetails, uni, profileImg, place)
                    ref.setValue(user)

                    Toast.makeText(
                        baseContext, "Message Sent",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this, MessageActivity::class.java))
                    finish()
                }
            }



    }
}