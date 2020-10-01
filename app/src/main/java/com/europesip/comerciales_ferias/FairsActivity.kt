package com.europesip.comerciales_ferias

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class FairsActivity : AppCompatActivity() {

    // Variables
    private lateinit var fairsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fairs)

        // Initialized variables
        fairsListView = findViewById(R.id.fairs_list_view)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //List view adapter
        fairsListView.adapter = FairsCustomAdapter(this)
    }

    // Function so the toolbar button make a backpress
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Custom adapter needed to build list view contents
    private class FairsCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        // Test arrays
        private val fairNames = arrayListOf<String>("Fair 1", "Fair 2", "Fair 3", "Fair 4", "Fair 5", "Fair 6", "Fair 7")
        private val fairLocations = arrayListOf<String>("Location 1", "Location 2", "Location 3", "Location 4", "Location 5", "Location 6", "Location 7")
        private val fairDates = arrayListOf<String>("Date 1", "Date 2", "Date 3", "Date 4", "Date 5", "Date 6", "Date 7")

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



}