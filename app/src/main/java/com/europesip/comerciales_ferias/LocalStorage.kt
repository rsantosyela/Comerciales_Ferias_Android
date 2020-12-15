package com.europesip.comerciales_ferias

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class LocalStorage(private val context: Context) {


   public fun saveToken(token: String, username: String, email: String){

       val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

       val editor: SharedPreferences.Editor = sharedPreferences.edit()
       editor.putString("token", token)
       editor.putString("username", username)
       editor.putString("email", email)

       editor.commit()

    }

   public fun removeToken(){

       val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("token", "")
       editor.putBoolean("accept_value", false)


       editor.commit()

    }

    public fun getToken() : String{

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        return sharedPreferences.getString("token", "").toString()



    }

    public fun saveUser(username: String, email: String){

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", email)

        editor.commit()

    }

    public fun getUser(): Array<String> {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)


        val str1: String = sharedPreferences.getString("username", "").toString()
        val str2: String = sharedPreferences.getString("email", "").toString()

    var arraysito: Array<String> = arrayOf(str1, str2)

        return arraysito

    }

    public fun setAcceptedTerms(accept_value: Boolean) {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("accept_value", accept_value)

        editor.commit()

    }

    public fun getAcceptedTerms(): Boolean {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        return sharedPreferences.getBoolean("accept_value", false)


    }

    public fun setContact(name: String, email: String, phone: Int, clientid: String, fairname: String, clientfairid: String){


        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("clientname", name)
        editor.putString("clientemail", email)
        editor.putInt("clientphone", phone)
        editor.putString("clientid", clientid)
        editor.putString("clientfairname", fairname)
        editor.putString("clientfairid", clientfairid)


        editor.commit()

    }


    public fun getSingleClientArray():Array<String> {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val name: String = sharedPreferences.getString("clientname","Error").toString()                 // 0
        val email: String = sharedPreferences.getString("clientemail","Error").toString()               // 1
        val phone: String = sharedPreferences.getInt("clientphone",0).toString()                        // 2
        val clientid: String = sharedPreferences.getString("clientid","Error").toString()               // 3
        val fair: String = sharedPreferences.getString("clientfairname","Error").toString()             // 4
        val clientfairid: String = sharedPreferences.getString("clientfairid","Error").toString()       // 5


        return arrayOf(name,email,phone,clientid, fair, clientfairid)

    }

    public fun savecredentials(email: String, password: String){

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("loginemail", email)
        editor.putString("loginpassword", password)

        editor.commit()


    }

    public fun getcredentials(): Array<String> {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("activity_main", AppCompatActivity.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val email: String = sharedPreferences.getString("loginemail","").toString()
        val password: String = sharedPreferences.getString("loginpassword","").toString()

        return arrayOf(email, password)


    }


}