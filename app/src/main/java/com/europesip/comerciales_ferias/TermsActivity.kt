package com.europesip.comerciales_ferias

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TermsActivity() : AppCompatActivity() {

    lateinit var accept_button: Button;
    lateinit var deny_button: Button;
    lateinit var legal_textView: TextView;
    private lateinit var loadingpanel: RelativeLayout
    private lateinit var mcontext: Context

    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService

    //LocalStroage
    private lateinit var ls: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        mcontext = this
        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        ls = LocalStorage(this)

        //Initalice vars
        accept_button = findViewById(R.id.accept_button)
        deny_button = findViewById((R.id.deny_button))
        legal_textView = findViewById((R.id.legal_text))
        loadingpanel = findViewById(R.id.loadingPanel)

        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)

cantouch(false)

        service.getlegaltext(ls.getToken()).enqueue(
            object : Callback<Message>{

                override fun onResponse(call: Call<Message>, response: Response<Message>) {

                    if (response.code() == 200) {
                        legal_textView.text = response.body()!!.message.toString()
                        cantouch(true)
                    } else {
                        cantouch(true)
                        alerta("Error de autenticidad, por favor vuelva a intentarlo. Si el error persiste cierre sesión y vuelva a identificarse")
                    }

                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    cantouch(true)
                    alerta("Error en la conexión de internet")
                }

            }
        )


        deny_button.setOnClickListener {

        ls.setAcceptedTerms(false)

        finish()
    }

        accept_button.setOnClickListener {

            ls.setAcceptedTerms(true)
            finish()
        }


    }



    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun cantouch(yesno: Boolean){

        accept_button.isEnabled = yesno
        deny_button.isEnabled = yesno

        accept_button.isVisible = yesno
        deny_button.isVisible = yesno

        if (yesno){
            loadingpanel.visibility = View.GONE
        } else {
            loadingpanel.isVisible = true
        }
    }

    fun alerta(message: String){

        AlertDialog.Builder(mcontext).setTitle("Atención").setMessage(message)
            .setPositiveButton(
                "Vale",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                    finish()

                }).setCancelable(false).show()

    }

}
