package com.europesip.comerciales_ferias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileActivity : AppCompatActivity() {

    //Variables
    private var nameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var closeSessionButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Initialized variables
        nameTextView = findViewById(R.id.name_text_view)
        emailTextView = findViewById(R.id.email_text_view)
        closeSessionButton = findViewById(R.id.close_session_button)

        //Close session button
        closeSessionButton?.setOnClickListener {
            Toast.makeText(this, "Hey!", Toast.LENGTH_SHORT).show()
        }
    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}