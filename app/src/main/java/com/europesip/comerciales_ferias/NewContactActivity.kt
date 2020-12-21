package com.europesip.comerciales_ferias

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewContactActivity : AppCompatActivity() {

    //Variables
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var fairSpinner: Spinner
    private lateinit var termsCheckBox: CheckBox
    private lateinit var saveButton: Button
    private lateinit var ls: LocalStorage
    private lateinit var mcontext: Context
    private lateinit var fairnames: Array<String>
    private lateinit var loadingpanel: RelativeLayout
    private var loading: Boolean = false
    private lateinit var fairids: Array<String>
    private var selected = 0


    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Agregar Nuevo Cliente"

        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)

        mcontext = this


        ls = LocalStorage(this)

        //Initialized variables
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        phoneEditText = findViewById(R.id.phone_edit_text)
        fairSpinner = findViewById(R.id.fair_spinner)
        termsCheckBox = findViewById(R.id.terms_check_box)
        saveButton = findViewById(R.id.save_button)
        loadingpanel = findViewById(R.id.loadingPanel)


         setupdata()


        fairSpinner.adapter


        // transicion a pantalla con terms

        //Save button click
        saveButton!!.setOnClickListener {

        checkfields()

        }

        termsCheckBox.setOnClickListener {

            if (!loading) {

                if (termsCheckBox.isChecked) {
                    termsCheckBox.isChecked = false
                    ls.setAcceptedTerms(false)
                    val intent = Intent(this, TermsActivity::class.java)
                    startActivity(intent)
                } else {
                    termsCheckBox.isChecked = false
                    ls.setAcceptedTerms(false)

                }

            }


            }


        fairSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View,
                arg2: Int, arg3: Long
            ) {

                selected = arg2

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        })



    }

    override fun onResume() {
        super.onResume()

        termsCheckBox.isChecked = ls.getAcceptedTerms()

    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun checkfields(){

        var canproceed: Boolean = true

        var emailAddress = emailEditText.getText().toString().trim()

        if (nameEditText.getText().toString().equals("")) {
            nameEditText.setError("Introduzca Nombre")
            nameEditText.requestFocus()
            canproceed = false
        } else {
            nameEditText.setError(null)
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
            emailEditText.setError(null);
        }

        if (phoneEditText.getText().toString().equals("")) {
            phoneEditText.setError("Introduzca número de teléfono")
            phoneEditText.requestFocus()
            canproceed = false
        } else if (phoneEditText.getText().toString().length != 9) {
            phoneEditText.setError("Introduzca un número de teléfono válido")
            phoneEditText.requestFocus()
            canproceed = false
        } else {
            phoneEditText.setError(null)
        }

        if (!termsCheckBox.isChecked){
            termsCheckBox.setError("Es necesario aceptar los términos")
            canproceed = false
        } else {
            termsCheckBox.setError(null)

        }


        if (canproceed){
            saveClientRequest()
        }


    }


    fun setupdata(){

        canclick(false)

        service.getfairnames(ls.getToken()).enqueue(
            object : Callback<GetFairNames> {
                override fun onResponse(
                    call: Call<GetFairNames>,
                    response: Response<GetFairNames>
                ) {

                    if (response.code() == 200) {

                        fairnames = response.body()!!.fairnames
                        fairids = response.body()!!.fairids

                        setupspinner()

                        canclick(true)


                    } else if (response.code() == 401) {

                        var msg =
                            "No se encuentra ninguna Feria en la base de datos. Por favor, contacte con el administrador del panel de control"

                        alerta(msg)

                        finish()

                    } else {

                        var msg =
                            "No se ha podido conectar a la base de datos. Vuelva a iniciar sesión"

                        alerta(msg)

                        ls.removeToken()

                        System.exit(0)

                    }


                }

                override fun onFailure(call: Call<GetFairNames>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

        fun alerta(message: String){

            AlertDialog.Builder(mcontext).setTitle("Atención").setMessage(message)
                .setPositiveButton(
                    "Vale",
                    DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->


                    }).setCancelable(false).show()

        }

    fun canclick(yesno: Boolean){

        nameEditText.isFocusable = yesno
        nameEditText.isFocusableInTouchMode = yesno

        emailEditText.isFocusable = yesno
        emailEditText.isFocusableInTouchMode = yesno

        phoneEditText.isFocusable = yesno
        phoneEditText.isFocusableInTouchMode = yesno

        loading = !yesno
        termsCheckBox.isEnabled = yesno

        saveButton.isEnabled = yesno

        if (yesno){
            loadingpanel.visibility = View.GONE
        } else {
            loadingpanel.isVisible = true

        }

    }

   fun setupspinner(){

       val spinnerArrayAdapter = ArrayAdapter<String>(
           this, R.layout.spinnerstyle, fairnames
       )
       fairSpinner.setAdapter(spinnerArrayAdapter)


   }

    fun saveClientRequest(){

        canclick(false)

        service.createclient(ls.getToken() ,nameEditText.text.toString(), emailEditText.text.toString(),phoneEditText.text.toString() , fairids[selected] ) .enqueue(
            object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {

                    Log.d("asdf", "" + response.code());

                    if (response.code() == 200) {

                        canclick(true)
                       alerta(response.body()!!.message)
                        nameEditText.setText("")
                        emailEditText.setText("")
                        phoneEditText.setText("")

                        ls.setAcceptedTerms(false)
                        termsCheckBox.isChecked = false




                    } else {

                        canclick(true)
                        alerta("Error de autenticidad, por favor vuelva a intentarlo. Si el error persiste cierre sesión y vuelva a identificarse")


                    }

                }

                override fun onFailure(call: Call<Message>, t: Throwable) {

                    canclick(true)
                    alerta("Error en la conexión de internet")


                }
            })

    }



}