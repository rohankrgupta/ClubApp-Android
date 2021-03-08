package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val recycler_view : RecyclerView = findViewById(R.id.dashrecycler)
        val profile : BottomNavigationItemView = findViewById(R.id.nav_profile)
        val msg : BottomNavigationItemView = findViewById(R.id.nav_message)
        val not : BottomNavigationItemView = findViewById(R.id.nav_social)

        profile.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
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

        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recycler_view.adapter = adapter


    }
}
