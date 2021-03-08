package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.notif_card_view.view.*

class MessageActivity : AppCompatActivity() {


    //private var adapter: RecyclerView.Adapter<MainAdapter.CustomViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)


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

        //adapter = MainAdapter()
        //msg_recycler_view.adapter = adapter

      //  val newAdapter = GroupieAdapter()
        fetchUsers()

        // if user clicks at new msg
       val newMsg : Button = findViewById(R.id.msg_new)
        newMsg.setOnClickListener{
            startActivity(Intent(this, NewMsgActivity::class.java))
            finish()
        }
        // user calls save msg fxn
    }
    private fun fetchUsers(){
        val msg_recycler_view : RecyclerView = findViewById(R.id.msgrecycler)
        val ref  =  FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    val user = it.getValue(msgInfo::class.java)
                    if(user != null)
                        adapter.add(UserItem(user))
                }
                Log.d("NewMsgActivity", "Fetched Users")
                msg_recycler_view.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: msgInfo): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.msg_pr_name.text = user.username
        viewHolder.itemView.msg_pr_des.text = user.msg
        viewHolder.itemView.pr_uni.text = user.uni
        viewHolder.itemView.pr_add.text = user.place
    }

    override fun getLayout(): Int {
        return R.layout.notif_card_view
    }
}

