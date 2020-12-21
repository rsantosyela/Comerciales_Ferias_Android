package com.europesip.comerciales_ferias

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.RelativeLayout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchContacts : AppCompatActivity() {

    // Variables
    private lateinit var myContactsListView: ListView
    private lateinit var loadingpanel: RelativeLayout
    public lateinit var mcontext: Context


    // RETROFIT
    private lateinit var retrofit: Retrofit
    private lateinit var service: ApiService

    //LocalStroage
    private lateinit var ls: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_contacts)


        // Initialized variables
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
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle("Buscar por nombre de cliente")
    }



    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}