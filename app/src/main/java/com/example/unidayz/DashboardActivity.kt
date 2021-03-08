package com.example.unidayz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

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

    }
}
