package com.example.goni95_app.retrofit

import com.example.goni95_app.util.API
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import com.google.gson.JsonElement

// unsplash api : https://unsplash.com/documentation#search-photos
// unsplash api : https://unsplash.com/documentation#registering-your-application
interface IRetrofit_Service {

    @GET(API.SEARCH_PHOTOS)
    fun serachPhotos(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사진 검색 서비스 http://www.unsplash.com/serach/photos/?query=입력값

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사용자 검색 서비스
}

/*
import com.example.goni95_app.util.API
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import com.google.gson.JsonElement

// unsplash api : https://unsplash.com/documentation#search-photos
// unsplash api : https://unsplash.com/documentation#registering-your-application
interface IRetrofit_Service {

    @GET(API.SEARCH_PHOTOS)
    fun serachPhotos(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사진 검색 서비스 http://www.unsplash.com/serach/photos/?query=입력값

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사용자 검색 서비스
}
 */