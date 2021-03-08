package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class NotifActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)

        val profile : BottomNavigationItemView = findViewById(R.id.nav_profile)
        val msg : BottomNavigationItemView = findViewById(R.id.nav_message)
        val dashBoard : BottomNavigationItemView = findViewById(R.id.nav_sch)

        dashBoard.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        profile.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        msg.setOnClickListener{
            startActivity(Intent(this, MessageActivity::class.java))
            finish()
        }
    }
}