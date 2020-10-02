package com.europesip.comerciales_ferias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TermsActivity : AppCompatActivity() {

    lateinit var accept_button: Button;
    lateinit var deny_button: Button;
    lateinit var legal_text: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        var textolegal: String = " ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH.ALL WE DO HEAR FROM YOU IS BLAH BLAH BLAH SO ALL WE EVER DO IS GO JAJAJA, CAUSE IS JA JA JA JA JA BLAH BLAH BLAH BLAH."
        //Initalice vars
        accept_button = findViewById(R.id.accept_button)
        deny_button = findViewById((R.id.deny_button))
        legal_text = findViewById((R.id.legal_text))

       // legal_text.text = "asdf"
        legal_text.text = textolegal + textolegal +textolegal +textolegal +textolegal +textolegal +textolegal +textolegal +textolegal +textolegal


    deny_button.setOnClickListener {
        val intent = Intent(this, NewContactActivity::class.java)
        intent.putExtra("accept_terms", false)
        startActivity(intent)
        finish()
    }

        accept_button.setOnClickListener {
            val intent = Intent(this, NewContactActivity::class.java)
            intent.putExtra("accept_terms", true)
            startActivity(intent)
            finish()
        }


    }



    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}