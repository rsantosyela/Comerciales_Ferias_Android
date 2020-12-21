package com.europesip.comerciales_ferias

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactsList : AppCompatActivity() {

    // Variables
    private lateinit var myContactsListView: ListView
    private lateinit var loadingpanel: RelativeLayout
    public lateinit var clientnames: Array<String>
    public lateinit var clientemails: Array<String>
    public lateinit var clientphones: Array<Int>
    public lateinit var clientids: Array<String>
    public lateinit var fairname: String
    public lateinit var fairid: String
    public lateinit var mcontext: Context
    public var canrefresh: Boolean = false;


    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService

    //LocalStroage
    private lateinit var ls: LocalStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)


// Initialized variables
        myContactsListView = findViewById(R.id.my_contacts_list_view)
        loadingpanel = findViewById(R.id.loadingPanel)
        ls = LocalStorage(this)
        mcontext = this

        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)
        // Toolbar

        var username = ls.getUser()[0];
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        // Set on item click
        myContactsListView.setOnItemClickListener { parent, view, position, id ->

            ls.setContact(clientnames[position],clientemails[position], clientphones[position], clientids[position], fairname, fairid)

            val nextintent = Intent(this, SingleContactDetail::class.java)
            startActivity(nextintent)




        }

        setupdata()


    }


    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Custom adapter needed to build list view contents
    public class MyContactsCustomAdapter(context: Context, var clientnames: Array<String>, var clientemails: Array<String>, var clientphones: Array<Int>,var fairname: String): BaseAdapter() {

        private val mContext: Context = context
        // Test array

        override fun getCount(): Int {
            return this.clientnames.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.contactslistsadapter, viewGroup, false)

            val nameTextView = row.findViewById<TextView>(R.id.textNombre)
            nameTextView.text = clientnames[position]

            val emailTextView = row.findViewById<TextView>(R.id.textEmail)
            emailTextView.text = clientemails[position]

            val fairTextView = row.findViewById<TextView>(R.id.textFair)
            fairTextView.text = fairname

            return row
        }
    }


    fun cantouch(yesno: Boolean){
        myContactsListView.isEnabled = yesno

        if (yesno){
            loadingpanel.visibility = View.GONE
        } else {
            loadingpanel.isVisible = true
        }


    }

    fun setupdata(){

        cantouch(false)
        canrefresh = false;

        service.getclientsbyfair(ls.getToken(), ls.getFairid()).enqueue(object : Callback<getclientsbyfair> {
            override fun onResponse(
                call: Call<getclientsbyfair>,
                response: Response<getclientsbyfair>
            ) {

                if (response.code() == 200){

                    clientnames = response.body()!!.clientnames
                    clientemails = response.body()!!.clientemails
                    clientphones = response.body()!!.clientphones
                    clientids = response.body()!!.clientids
                    fairname = response.body()!!.fairname
                    fairid = response.body()!!.fairid

                    supportActionBar!!.setTitle("Contactos de " + fairname)

                    setupadapter()
                    cantouch(true)
                    canrefresh = true


                } else if (response.code() == 401){

                    alerta_no_data()

                } else {

                    alerta("Error de autenticidad, por favor vuelva a intentarlo. Si el error persiste cierre sesión y vuelva a identificarse")
                }


            }

            override fun onFailure(call: Call<getclientsbyfair>, t: Throwable) {
                alerta("Error de conexion")
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

    fun alerta_no_data(){

        AlertDialog.Builder(mcontext).setTitle("Atención").setMessage("No existe ningún cliente")
            .setPositiveButton(
                "Vale",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                    finish()

                }).setCancelable(false).show()

    }

    fun setupadapter(){

        // List view adapter
        myContactsListView.adapter = MyContactsCustomAdapter(mcontext,clientnames,clientemails,clientphones,fairname)


    }

    override fun onResume() {
        super.onResume()

        if (canrefresh){
            setupdata()
        }

    }
}