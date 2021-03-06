package com.europesip.comerciales_ferias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MenuActivity : AppCompatActivity() {

    //Variables
    private lateinit var profileButton: Button
    private lateinit var newContactButton: Button
    private lateinit var myContactsButton: Button
    private lateinit var fairsButton: Button
    private lateinit var ls: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var ls: LocalStorage = LocalStorage(this)

        if (ls.getToken() == "") {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                finish()

        }

        //Initialized Variables
        profileButton = findViewById(R.id.profile_button)
        newContactButton = findViewById(R.id.new_contact_button)
        myContactsButton = findViewById(R.id.my_contacts_button)
        fairsButton = findViewById(R.id.fairs_button)

        ls = LocalStorage(this)
        ls.setAcceptedTerms(false)


        //Profile button click
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //New contact button click
        newContactButton.setOnClickListener {
            val intent = Intent(this, NewContactActivity::class.java)
            intent.putExtra("accept_terms", false)
            startActivity(intent)
        }

        //My contacts button click
        myContactsButton.setOnClickListener {
            val intent = Intent(this, MyContacts::class.java)
            startActivity(intent)
        }

        //Fairs button click
        fairsButton.setOnClickListener {
            val intent = Intent(this, FairsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        ls = LocalStorage(this)
        ls.setAcceptedTerms(false)
    }
}