package com.europesip.comerciales_ferias

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class NewContactActivity : AppCompatActivity() {

    //Variables
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var fairEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Agregar Nuevo Cliente"

        //Initialized variables
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        phoneEditText = findViewById(R.id.phone_edit_text)
        fairEditText = findViewById(R.id.fair_edit_text)
        termsCheckBox = findViewById(R.id.terms_check_box)
        saveButton = findViewById(R.id.save_button)

       // termsCheckBox.isChecked = intent.getBooleanExtra("accept_terms",false)

        // transicion a pantalla con terms

        //Save button click
        saveButton!!.setOnClickListener {

            //Getting form data
            var name = nameEditText!!.text
            var email = emailEditText!!.text
            var phone = phoneEditText!!.text
            var fair = fairEditText!!.text
            var areTermsAccepted = termsCheckBox!!.isChecked

            //Checking terms
            if(areTermsAccepted == true){
                //POST petition
               // Toast.makeText(this, "Diego!", Toast.LENGTH_SHORT).show()
            }

            var ischecked: Boolean = intent.getBooleanExtra("accept_terms",false)

            if (ischecked){
                Toast.makeText(this, "es true", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "es false", Toast.LENGTH_SHORT).show()
            }


        }



        termsCheckBox.setOnClickListener {

            if (termsCheckBox.isChecked) {
                termsCheckBox.setChecked(false)
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            }


           // Toast.makeText(this, "has pulsado el caja", Toast.LENGTH_SHORT).show()


        }










    }

    override fun onResume() {
        super.onResume()

        termsCheckBox = findViewById(R.id.terms_check_box)

        var ischeck: Boolean = intent.getBooleanExtra("accept_terms",false)

        termsCheckBox.isChecked = ischeck

    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}