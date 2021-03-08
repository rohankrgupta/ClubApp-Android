package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val dashBoard : BottomNavigationItemView = findViewById(R.id.nav_sch)
        val msg : BottomNavigationItemView = findViewById(R.id.nav_message)
        val not : BottomNavigationItemView = findViewById(R.id.nav_social)

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

    }
}