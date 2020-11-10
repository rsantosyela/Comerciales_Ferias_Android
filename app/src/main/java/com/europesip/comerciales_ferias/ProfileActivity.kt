package com.europesip.comerciales_ferias

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService

class ProfileActivity : AppCompatActivity() {

    //Variables
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var closeSessionButton: Button
    private lateinit var ls: LocalStorage

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


        ls = LocalStorage(this)



        //Close session button
        closeSessionButton?.setOnClickListener {


            AlertDialog.Builder(this).setTitle("Atención").setMessage("¿Confirma usted que quiere cerrar sesión?")
                .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                    ls.removeToken()
                        System.exit(0)



                }).setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->



                    }).show()

        }


        val username_and_email: Array<String> = ls.getUser()

        nameTextView.text = username_and_email[0]
        emailTextView.text = username_and_email[1]


    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}