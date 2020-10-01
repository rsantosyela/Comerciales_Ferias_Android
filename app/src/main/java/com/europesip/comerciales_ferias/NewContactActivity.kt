package com.europesip.comerciales_ferias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    //Variables
    private var nameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var phoneEditText: EditText? = null
    private var fairEditText: EditText? = null
    private var termsCheckBox: CheckBox? = null
    private var saveButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Initialized variables
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        phoneEditText = findViewById(R.id.phone_edit_text)
        fairEditText = findViewById(R.id.fair_edit_text)
        termsCheckBox = findViewById(R.id.terms_check_box)
        saveButton = findViewById(R.id.save_button)

        //Save button click
        saveButton?.setOnClickListener {

            //Getting form data
            var name = nameEditText?.text
            var email = emailEditText?.text
            var phone = phoneEditText?.text
            var fair = fairEditText?.text
            var areTermsAccepted = termsCheckBox?.isChecked

            //Checking terms
            if(areTermsAccepted == true){
                //POST petition
                Toast.makeText(this, "Diego!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}