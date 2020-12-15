package com.europesip.comerciales_ferias

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String,@Field("password") password: String) : Call<Logincl>

    @GET("checkthetoken")
    fun checkthetoken(@Header("token")token: String) : Call<Tokenrs>

    @GET("getfairnames")
    fun getfairnames(@Header("token")token: String) : Call<GetFairNames>

    @FormUrlEncoded
    @POST("createclient")
    fun createclient(@Header("token") token: String , @Field("name") name: String, @Field("email") email: String, @Field("phone") phone: String, @Field("fairid") fairid: String) : Call<Message>

    @GET("getmyclients")
    fun getmyclients(@Header("token")token: String) : Call<getmyclientsrs>

    @FormUrlEncoded
    @POST("deleteclient")
    fun deleteclient(@Header("token") token: String , @Field("clientid") clientid: String) : Call<Message>

    @FormUrlEncoded
    @POST("updateclient")
    fun updateclient(@Header("token") token: String , @Field("clientid") clientid: String,  @Field("name") name: String,  @Field("email") email: String,  @Field("phone") phone: String, @Field("fairid") fairid: String) : Call<Message>
}
