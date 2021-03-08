package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val dashBoard : BottomNavigationItemView = findViewById(R.id.nav_sch)
        val msg : BottomNavigationItemView = findViewById(R.id.nav_message)
        val not : BottomNavigationItemView = findViewById(R.id.nav_social)
        val signOut : Button = findViewById(R.id.signout)
        dashBoard.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        msg.setOnClickListener{
            startActivity(Intent(this, MessageActivity::class.java))
            finish()
        }
        not.setOnClickListener{
            startActivity(Intent(this, NotifActivity::class.java))
            finish()
        }
        signOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        setDetails()

    }

    private fun setDetails() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("NewMsgActivity", "DocumentSnapshot data: ${document.data}")
                    Log.d("NewMsgActivity", "DocumentSnapshot data: ${document.get("username")}")
                    val username = document.get("username").toString()
                    val uni = document.get("useruniversity").toString()
                    val degree = document.get("userdegree").toString()
                    val des = document.get("useruniDes").toString()
                    val place = document.get("useruniPlace").toString()
                    pr_degree.text = degree
                    pr_uni.text = uni
                    pr_name.text = username
                    pr_des.text = des
                    pr_add.text = place
                }
            }
    }

    /*private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return
        val filename  = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
    }*/
}