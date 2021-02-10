package com.example.goni95_app.retrofit

import android.annotation.SuppressLint
import android.util.Log
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
        // 객체생성
    }

    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService: IRetrofit_Service? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit_Service::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<Photo>?) -> Unit) {
        val term = searchTerm.let { it } ?: ""
        //val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return

       /*
        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 수행한 Request에 대한 Response를 받는 콜백 메서드
            @SuppressLint("SimpleDateFormat")   // SimpleDateFormat 경고 무시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(
                    Constants.TAG,
                    "RetrofitManager - onResponse() called : response.raw(): ${response.raw()}"
                )
                Log.d(
                    Constants.TAG,
                    "RetrofitManager - onResponse() called : response.body(): ${response.body()}"
                )

                when (response.code()) {  // 응답 코드 구분
                    200 -> {
                        // 파싱 작업
                        response.body()?.let {
                            var parsedPhotoDataArray = ArrayList<Photo>()

                            // 최상위 JsonObject (total{}, results[])
                            val body = it.asJsonObject  // Json 객체
                            val results = body.getAsJsonArray("results") // result라는 Json Array
                            val total = body.get("total").asInt // 검색 결과 수

                            Log.d(
                                Constants.TAG,
                                "RetrofitManager - onResponse() called / total : ${total}"
                            )

                            //검색 결과 수가 0이 아닌 경우
                            if(total != 0){
                                // JsonArray result[] 내부의 JsonObject를 하나씩 받아 반복 수행
                                results.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject // get object
                                    val user =
                                        resultItemObject.get("user").asJsonObject    // object -> user
                                    val username = user.get("username").asString    // user -> username
                                    val likeCount =
                                        resultItemObject.get("likes").asInt     // object -> likes
                                    val thumbnail =
                                        resultItemObject.get("urls").asJsonObject.get("thumb").asString      // urls -> thumb
                                    val createdAt =
                                        resultItemObject.get("created_at").asString  // object -> create_at

                                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    // 실제 Date 형태
                                    val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                    // 변경하려는 Date 형태

                                    val outputDateString = formatter.format(parser.parse(createdAt))
                                    // Date를 원하는 형태로 치환

                                    //Log.d(Constants.TAG, "RetrofitManager - onResponse() called / outputDateString : ${outputDateString}")

                                    val photoItem = Photo(
                                        author = username,
                                        likesCount = likeCount,
                                        thumbnail = thumbnail,
                                        createdAt = outputDateString
                                    )

                                    parsedPhotoDataArray.add(photoItem)
                                    //ArrayList에 Photo 타입의 데이터를 추가가
                                }
                                completion(RESPONSE_STATE.OK, parsedPhotoDataArray)
                            } else{
                                //검색 결과 수가 0인 경우
                                completion(RESPONSE_STATE.NONE, null)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called : t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })
        */
    }


    //사용자검색 api 호출
    fun searchUsers() {

    }
}