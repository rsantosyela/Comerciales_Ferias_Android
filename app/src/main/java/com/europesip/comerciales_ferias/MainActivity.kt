package com.europesip.comerciales_ferias

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_terms.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    //Variables
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var warningtext: TextView
    private lateinit var loadingpanel: RelativeLayout
    private lateinit var ls:LocalStorage

    // RETROFIT
   private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)


        //Initialized variables
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        warningtext = findViewById(R.id.warningtextview)
        loadingpanel = findViewById(R.id.loadingPanel)

        warningtext.isVisible = false

        ls = LocalStorage(this)

        var credentials: Array<String> = ls.getcredentials()
        emailEditText.setText(credentials[0])
        passwordEditText.setText(credentials[1])

        checktoken()

        //Login button click
        loginButton?.setOnClickListener {

checkfields()

        }



    }


    fun checkfields() {

        var canproceed: Boolean = true

        var emailAddress = emailEditText.getText().toString().trim()

        if (passwordEditText.getText().toString().equals("")) {
            passwordEditText.setError("Introduzca contraseña")
                    passwordEditText.requestFocus()
            canproceed = false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailEditText.setError("El email introducido no es válido")
                    emailEditText.requestFocus()
            canproceed = false
        } else if (emailEditText.getText().toString().equals("")) {
            emailEditText.setError("Introduzca email");
                    emailEditText.requestFocus()
            canproceed = false
        } else {
            emailEditText.setError(null)
        }

        if (canproceed){

            requestlogin()
        }

    }

    fun nextscreen(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

        finish()
    }

    fun requestlogin(){

        loginButton.text = "Cargando..."
        loginButton.setTextColor(Color.parseColor("#4BD314"))
        ls.savecredentials(emailEditText.text.toString(), passwordEditText.text.toString())

        loginButton.isEnabled = false
        emailEditText.isFocusable = false
        passwordEditText.isFocusable = false

        service.login(emailEditText.text.toString().toLowerCase(), passwordEditText.text.toString()).enqueue(
            object : Callback<Logincl> {
                override fun onResponse(call: Call<Logincl>, response: Response<Logincl>) {

                    if (response.code() == 200) {

                    val username = response.body()?.username.toString()
                    val token = response.body()?.token.toString()
                        val email = response.body()?.email.toString()

                        ls.saveToken(token,username, email)

                        nextscreen()

                    } else {
                        loginButton.text = "INICIAR SESIÓN"
                        loginButton.setTextColor(Color.parseColor("#000000"))
                        warningtext.isVisible = true
                        loginButton.isEnabled = true
                        emailEditText.isFocusable = true
                        emailEditText.isFocusableInTouchMode = true

                        passwordEditText.isFocusable = true
                        passwordEditText.isFocusableInTouchMode = true


                    }

                }

                override fun onFailure(call: Call<Logincl>, t: Throwable) {

                    loginButton.text = "INICIAR SESIÓN"
                    loginButton.setTextColor(Color.parseColor("#000000"))
                    warningtext.isVisible = true
                    loginButton.isEnabled = true
                    emailEditText.isFocusable = true
                    emailEditText.isFocusableInTouchMode = true

                    passwordEditText.isFocusable = true
                    passwordEditText.isFocusableInTouchMode = true


                }
            })

    }


    fun checktoken(){

        loginButton.isEnabled = false
        emailEditText.isFocusable = false
        passwordEditText.isFocusable = false

        val token : String = ls.getToken()

        if (token != ""){

            service.checkthetoken(token).enqueue(
                object : Callback<Tokenrs>{
                    override fun onResponse(call: Call<Tokenrs>, response: Response<Tokenrs>) {

                        if (response.code() == 200){

                            val username = response.body()?.username.toString()
                            val email = response.body()?.email.toString()


                            ls.saveUser(username, email)

                            nextscreen()

                        } else {

                            loginButton.isEnabled = true
                            emailEditText.isFocusable = true
                            emailEditText.isFocusableInTouchMode = true

                            passwordEditText.isFocusable = true
                            passwordEditText.isFocusableInTouchMode = true

                            ls.removeToken()

                            loadingpanel.isVisible = false

                        }

                    }

                    override fun onFailure(call: Call<Tokenrs>, t: Throwable) {

                        loginButton.isEnabled = true
                        emailEditText.isFocusable = true
                        emailEditText.isFocusableInTouchMode = true

                        passwordEditText.isFocusable = true
                        passwordEditText.isFocusableInTouchMode = true

                        loadingpanel.isVisible = false

                    }

                }
            )

        } else {


            loginButton.isEnabled = true
            emailEditText.isFocusable = true
            emailEditText.isFocusableInTouchMode = true

            passwordEditText.isFocusable = true
            passwordEditText.isFocusableInTouchMode = true


            loadingpanel.visibility = View.GONE


        }


    }

}