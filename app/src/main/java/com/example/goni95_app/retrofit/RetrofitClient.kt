package com.example.goni95_app.retrofit

import android.util.Log
import com.example.goni95_app.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 오브젝트 : 자동 싱글턴 패턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언

    private var retrofitClient: Retrofit? = null
    // private lateinit var retrofitClient: Retrofit (선택사항)

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String) : Retrofit? {
        Log.d(Constants.TAG, "RetrofitClient - getClient() called")

        //빌더를 통해 인스턴스 생성
        if(retrofitClient == null){
                retrofitClient = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
        }
        return retrofitClient
    }
}