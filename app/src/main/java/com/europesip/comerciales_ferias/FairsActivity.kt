package com.europesip.comerciales_ferias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FairsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fairs)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}