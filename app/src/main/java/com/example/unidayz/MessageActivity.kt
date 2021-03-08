package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*

class MessageActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MainAdapter.CustomViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val msg_recycler_view : RecyclerView = findViewById(R.id.msgrecycler)
        val dashBoard : BottomNavigationItemView = findViewById(R.id.nav_sch)
        val not : BottomNavigationItemView = findViewById(R.id.nav_social)
        val profile : BottomNavigationItemView = findViewById(R.id.nav_profile)

        dashBoard.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        profile.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        not.setOnClickListener{
            startActivity(Intent(this, NotifActivity::class.java))
            finish()
        }
        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
        msg_recycler_view.layoutManager = layoutManager

        adapter = MainAdapter()
        msg_recycler_view.adapter = adapter

        // if user clicks at new msg
        val newMsg : Button = findViewById(R.id.msg_new)
        newMsg.setOnClickListener{
            startActivity(Intent(this, NewMsgActivity::class.java))
            finish()
        }

        // user calls save msg fxn

    }

    private fun saveMsgToDatabase(){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val docRef = db.collection("users").document(uid)

        var username : String = "A"
        var uni: String = "D"
        var profileImg: String = "S"
        val msg_str:String = "Hello World"


        docRef.get()
            .addOnSuccessListener { task ->
                val document = task.data

                if (document != null) {

                    if (document.getValue("useruniversity") != null)
                        uni = document.getValue("useruniversity").toString()
                    if (document.getValue("userprofilepic") != null)
                        profileImg = document.getValue("userprofilepic").toString()
                    if (document.getValue("username") != null)
                        username = document.getValue("username").toString()
                }
            }

        val user = msgInfo(uid, username, msg_str, uni, profileImg)
        ref.setValue(user)
    }

}

