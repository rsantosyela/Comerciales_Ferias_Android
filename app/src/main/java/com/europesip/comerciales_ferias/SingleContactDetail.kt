package com.europesip.comerciales_ferias

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_single_contact_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates


class SingleContactDetail : AppCompatActivity() {

    private lateinit var ls: LocalStorage
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var fairTextView: TextView
    private lateinit var nameTitle: TextView
    private lateinit var emailTitle: TextView
    private lateinit var phoneTitle: TextView
    private lateinit var fairTitle: TextView
    private lateinit var nameTitle2: TextView
    private lateinit var emailTitle2: TextView
    private lateinit var phoneTitle2: TextView
    private lateinit var fairTitle2: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var editbutton: Button
    private lateinit var deletebutton: Button
    private lateinit var loadingpanel: RelativeLayout
    private var editing: Boolean = false
    private lateinit var fairnames: Array<String>
    private lateinit var fairids: Array<String>
    private lateinit var mcontext: Context
    private lateinit var fairSpinner: Spinner
    private var Selected = 0


    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_contact_detail)


        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)
        mcontext = this


        nameTextView = findViewById(R.id.NameTextView)
       emailTextView = findViewById(R.id.EmailTextView)
       phoneTextView = findViewById(R.id.PhoneTextView)
       fairTextView = findViewById(R.id.FairTextView)

        nameEditText = findViewById(R.id.NameEditText)
        emailEditText = findViewById(R.id.EmailEditText)
        phoneEditText = findViewById(R.id.PhoneEditText)
        fairSpinner = findViewById(R.id.FairsSpinner)

        nameTitle = findViewById(R.id.textView)
        emailTitle = findViewById(R.id.textView2)
        phoneTitle = findViewById(R.id.textView3)
        fairTitle = findViewById(R.id.textView4)
        nameTitle2 = findViewById(R.id.textView5)
        emailTitle2 = findViewById(R.id.textView6)
        phoneTitle2 = findViewById(R.id.textView7)
        fairTitle2 = findViewById(R.id.textView8)

         editbutton = findViewById(R.id.EditButton)
         deletebutton = findViewById(R.id.DeleteButton)
        loadingpanel = findViewById(R.id.loadingPanel)

        ls = LocalStorage(this)

        val clientarray: Array<String> = ls.getSingleClientArray()


        nameTextView.setText(clientarray[0])
        emailTextView.setText(clientarray[1])
        phoneTextView.setText(clientarray[2])
        fairTextView.setText(clientarray[4])


        nameEditText.setText(clientarray[0])
        emailEditText.setText(clientarray[1])
        phoneEditText.setText(clientarray[2])


        getfairs()


        editbutton.setOnClickListener {

            if (!editing) {
                gonnaedit(true)
            } else {

                    if (checkfields()) {
                        AlertDialog.Builder(this).setTitle("Atención").setMessage("¿Confirma usted que quiere aplicar los cambios?")
                            .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                                requestchange()

                            }).setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->


                            }).show()
                    }

            }


        }

        deletebutton.setOnClickListener {

            if (!editing){

                AlertDialog.Builder(this).setTitle("Atención").setMessage("¿Confirma usted que quiere aplicar los cambios?")
                    .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                        requestdelete()

                    }).setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->


                    }).show()

            } else {

                gonnaedit(false)

            }

        }

        fairSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View,
                arg2: Int, arg3: Long
            ) {

                Selected = arg2

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        })




    }


    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun gonnaedit(yesno: Boolean){

        editing = yesno

        if (yesno){
            nameTextView.visibility = View.GONE
            emailTextView.visibility = View.GONE
            phoneTextView.visibility = View.GONE
            fairTextView.visibility = View.GONE

            nameEditText.isVisible = true
            emailEditText.isVisible = true
            phoneEditText.isVisible = true
            fairSpinner.isVisible = true

            editbutton.setText("GUARDAR")
            editbutton.setTextColor(Color.parseColor("#4BD314"))

            deletebutton.setText("CANCELAR")
            deletebutton.setTextColor(Color.parseColor("#000000"))


            nameTitle.visibility = View.GONE
            emailTitle.visibility = View.GONE
            phoneTitle.visibility = View.GONE
            fairTitle.visibility = View.GONE

            nameTitle.isVisible = true
            emailTitle2.isVisible = true
            phoneTitle2.isVisible = true
            fairTitle2.isVisible = true



        } else {

            nameTextView.isVisible = true
            emailTextView.isVisible = true
            phoneTextView.isVisible = true
            fairTextView.isVisible = true

            nameEditText.visibility = View.GONE
            emailEditText.visibility = View.GONE
            phoneEditText.visibility = View.GONE
            fairSpinner.visibility = View.GONE


            editbutton.setText("EDITAR")
            editbutton.setTextColor(Color.parseColor("#000000"))

            deletebutton.setText("ELIMINAR")
            deletebutton.setTextColor(Color.parseColor("#D32214"))

            nameTitle.isVisible = true
            emailTitle.isVisible = true
            phoneTitle.isVisible = true
            fairTitle.isVisible = true

            nameTitle2.visibility = View.GONE
            emailTitle2.visibility = View.GONE
            phoneTitle2.visibility = View.GONE
            fairTitle2.visibility = View.GONE


        }

    }

    fun dialog(){
        AlertDialog.Builder(this).setTitle("Atención").setMessage("¿Confirma usted que quiere cerrar sesión?")
            .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->


            }).setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->


            }).show()



    }

    fun checkfields(): Boolean{

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


        return canproceed


    }

    fun requestchange(){

        cantouch(false)

        var client = ls.getSingleClientArray()
        var name: String = nameEditText.text.toString()
        var email: String = emailEditText.text.toString()
        var phone: String = phoneEditText.text.toString()
        var clientid = client[3]
        var fairid: String = fairids[Selected]


        service.updateclient(ls.getToken(),clientid, name, email, phone, fairid).enqueue(
            object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {

                    if (response.code() == 200) {

                        alerta(response.body()!!.message)

                    } else {

                        cantouch(true)
                        alerta("Error de autenticidad, por favor vuelva a intentarlo. Si el error persiste cierre sesión y vuelva a identificarse")


                    }                  }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    cantouch(true)
                    alerta("Error en la conexión de internet")                  }

            }
        )

    }

    fun requestdelete(){

        cantouch(false)

        var client = ls.getSingleClientArray()
        var clientid = client[3]

          service.deleteclient(ls.getToken(),clientid).enqueue(
              object : Callback<Message> {
                  override fun onResponse(call: Call<Message>, response: Response<Message>) {

                      if (response.code() == 200) {

                          alerta(response.body()!!.message)

                      } else {

                          cantouch(true)
                          alerta("Se ha producido un error, puede probar a cerrar sesión e iniciar sesión nuevamente")


                      }                  }

                  override fun onFailure(call: Call<Message>, t: Throwable) {
                      cantouch(true)
                      alerta("Error en la conexión de internet")                  }

              }
          )

    }

    fun cantouch(yesno: Boolean){

        editbutton.isEnabled = yesno
        deletebutton.isEnabled = yesno

        if (yesno){
            loadingpanel.visibility = View.GONE
        } else {
            loadingpanel.isVisible = true
        }



    }

    fun getfairs(){

        cantouch(false)

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

                        cantouch(true)


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

                    finish()

                }).setCancelable(false).show()

    }

    fun setupspinner(){

        val spinnerArrayAdapter = ArrayAdapter<String>(
            this, R.layout.spinnerstyle, fairnames
        )

        var clientobj = ls.getSingleClientArray()

        var myfairid = clientobj[5]

        for (i in fairids.indices) {

            if (fairids[i] == myfairid){

                Selected = i
                fairTextView.setText(fairnames[i])

            }


        }

        fairSpinner.setAdapter(spinnerArrayAdapter)
        fairSpinner.setSelection(Selected)


    }


}