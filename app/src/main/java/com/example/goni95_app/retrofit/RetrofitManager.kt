package com.example.goni95_app.retrofit

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.example.goni95_app.viewmodel.HomeViewModel
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
        // 객체생성
    }

    private val mjob = Job()
    private val mScope = CoroutineScope(Dispatchers.Main + mjob)
    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService: IRetrofit_Service? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit_Service::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<Photo>?) -> Unit) {
        val term = searchTerm.let { it } ?: ""

        mScope.launch {
            var parsedPhotoDataArray = ArrayList<Photo>()

            val job = launch(Dispatchers.IO) {
                val call =
                    iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return@launch

                val body = call.asJsonObject
                val results = body.getAsJsonArray("results")
                val total = body.get("total").asInt

                Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                        "/ message : ${total}")

                if (total != 0) {
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

                        Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                                "/ message : ${user}")
                        Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                                "/ message : ${username}")
                        Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                                "/ message : ${likeCount}")
                        Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                                "/ message : ${thumbnail}")
                        Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} " +
                                "/ message : ${createdAt}")


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
                }
            }

            job.join()
            if(job.isCompleted){
                completion(RESPONSE_STATE.OK, parsedPhotoDataArray)
            }

        }
    }


    //사용자검색 api 호출
    fun searchUsers() {

    }
}