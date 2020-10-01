package com.europesip.comerciales_ferias

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.view.*

class MyContacts : AppCompatActivity() {

    // Variables
    private lateinit var myContactsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contacts)

        // Initialized variables
        myContactsListView = findViewById(R.id.my_contacts_list_view)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        // List view adapter
        myContactsListView.adapter = MyContactsCustomAdapter(this)

        // Set on item click
        myContactsListView.setOnItemClickListener { parent, view, position, id ->

            // Item position
            val itemPosition = position

            // Test intent
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    // Function so the toolbar button makes a back press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Custom adapter needed to build list view contents
    private class MyContactsCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context
        // Test array
        private val contacts = arrayListOf<String>("Contact 1", "Contact 2", "Contact 3", "Contact 4", "Contact 5", "Contact 6", "Contact 7")

        override fun getCount(): Int {
            return contacts.size
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
            val row = layoutInflater.inflate(R.layout.row_my_contacts, viewGroup, false)

            // Contact names
            val contactNameTextView = row.findViewById<TextView>(R.id.contact_name_text_view)
            contactNameTextView.text = contacts[position]

            return row
        }
    }
}