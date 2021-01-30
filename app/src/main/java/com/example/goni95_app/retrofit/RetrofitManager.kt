package com.example.goni95_app.retrofit

import android.util.Log
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
        // 객체생성
    }

    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService : IRetrofit_Service? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit_Service::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let { it } ?: ""
        val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.raw(): ${response.raw()}")
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.body(): ${response.body()}")

                completion(RESPONSE_STATE.OK, response.raw().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called : t: $t")

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }


    //사용자검색 api 호출
    fun searchUsers(){

    }
}

/*
import android.util.Log
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
        // 객체생성
    }

    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService : IRetrofit_Service? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit_Service::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let { it } ?: ""
        val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.raw(): ${response.raw()}")
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.body(): ${response.body()}")

                completion(RESPONSE_STATE.OK, response.raw().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called : t: $t")

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }


    //사용자검색 api 호출
    fun searchUsers(){

    }
}
 */