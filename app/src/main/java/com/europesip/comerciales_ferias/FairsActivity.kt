package com.europesip.comerciales_ferias

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.view.size
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FairsActivity : AppCompatActivity() {


    // Test arrays
    private lateinit var fairNames: Array<String>
    private lateinit var fairLocations: Array<String>
    private lateinit var fairDates: Array<String>
    private lateinit var fairIds: Array<String>

    // Variables
    private lateinit var fairsListView: ListView
    private lateinit var loadingpanel: RelativeLayout
    public lateinit var mcontext: Context



    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService

    //LocalStroage
    private lateinit var ls: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fairs)

        // Initialized variables
        loadingpanel = findViewById(R.id.loadingPanel)
        ls = LocalStorage(this)
        mcontext = this
        fairsListView = findViewById(R.id.fairs_list_view)

        val url: String = resources.getString(R.string.base_url_api)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<ApiService>(ApiService::class.java)



        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle("Ferias")


         fairsListView.setOnItemClickListener { parent, view, position, id ->
             var selectedItem = parent.getItemAtPosition(position)

             val intent = Intent(this, ContactsList::class.java)
             intent.putExtra("title", fairNames[position])
             ls.setFairid(fairIds[position])
             startActivity(intent)



         }

        setupdata();

    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Custom adapter needed to build list view contents
    private class FairsCustomAdapter(context: Context, var fairNames: Array<String>, var fairLocations: Array<String>, var fairDates: Array<String>): BaseAdapter() {

        private val mContext: Context = context

        // Test arrays

        override fun getCount(): Int {
            return fairNames.size
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
            val row = layoutInflater.inflate(R.layout.row_fairs, viewGroup, false)

            // Fair names
            val fairNameTextView = row.findViewById<TextView>(R.id.fair_name_text_view)
            fairNameTextView.text = fairNames[position]

            // Fair locations
            val fairLocationTextView = row.findViewById<TextView>(R.id.fair_location_text_view)
            fairLocationTextView.text = fairLocations[position]

            // Fair dates
            val fairDateTextView = row.findViewById<TextView>(R.id.fair_date_text_view)
            fairDateTextView.text = fairDates[position]

            return row
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_buscar -> {
            val intent = Intent(this, SearchContacts::class.java)
            startActivity(intent)
            true
        }
        else -> false
    }

    fun cantouch(yesno: Boolean){

        if (yesno){
            loadingpanel.visibility = View.GONE
        } else {
            loadingpanel.isVisible = true
        }


    }


    fun setupdata(){

        cantouch(false)

        service.getfairsdata(ls.getToken()).enqueue(object : Callback<GetFairsData> {
            override fun onResponse(
                call: Call<GetFairsData>,
                response: Response<GetFairsData>
            ) {
                if (response.code() == 200){

                    fairNames = response.body()!!.fairnames
                    fairIds = response.body()!!.fairids
                    fairLocations = response.body()!!.fairlocations
                    fairDates = response.body()!!.fairdates

                    setupadapter()
                    cantouch(true)



                } else if (response.code() == 401){

                    alerta_no_data()

                } else {

                  //  alerta("Error de autenticidad")

                  //  ls.removeToken()
                  //  System.exit(0)

                    alerta("Error de autenticidad, por favor vuelva a intentarlo. Si el error persiste cierre sesión y vuelva a identificarse")

                }


            }

            override fun onFailure(call: Call<GetFairsData>, t: Throwable) {
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

        AlertDialog.Builder(mcontext).setTitle("Atención").setMessage("No hay ningún cliente creado por su usuario")
            .setPositiveButton(
                "Vale",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

                    finish()

                }).setCancelable(false).show()

    }

    fun setupadapter(){

        //List view adapter
        fairsListView.adapter = FairsCustomAdapter(this,fairNames, fairLocations, fairDates)


    }


}