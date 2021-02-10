package com.example.goni95_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goni95_app.model.Photo
import com.example.goni95_app.retrofit.IRetrofit_Service
import com.example.goni95_app.retrofit.RetrofitClient
import com.example.goni95_app.util.*
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat

class HomeViewModel : ViewModel() {
    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService : IRetrofit_Service

    init {
        iretrofitService = (RetrofitClient.getClient(API.BASE_URL)?.create(
            IRetrofit_Service::class.java) ?: null) as IRetrofit_Service
    }


    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit) {
        val term = searchTerm.let { it } ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return@launch

            var parsedPhotoDataArray = ArrayList<Photo>()

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


                //completion(RESPONSE_STATE.OK, JSONObject(call.asString).toString(4))
            }
        }
    }
}




// jsonelement를 jsonobject, jsonarray로 비교해서 변환하여 출력
/*
fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let { it } ?: ""
        //val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return

        viewModelScope.launch(Dispatchers.IO){
            var result = iretrofitService.serachPhotos(term)
            Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / result : ${result}")


            when {
                result.isJsonObject -> {
                    aa = JSONObject(result.toString()).toString(4)
                    Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result.toString()).toString(4)}"
                    )
                }
                result.isJsonArray -> {
                    aa = JSONObject(result.toString()).toString(4)
                    Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result.toString()).toString(4)}")
                }
                else -> {
                    try {
                        Log.d(
                            Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result.toString()).toString(4)}"
                        )
                    } catch (e: Exception) {
                        Log.d(
                            Constants.TAG, "${HomeViewModel::class.java.simpleName} / error : $e, message : $result"
                        )
                    }
                }
            }
            completion(RESPONSE_STATE.OK, aa)
        }
    }
 */

// jsonelement를 확장함수로 비교해서 변환하여 출력
/*
when {
                result.isJsonObject() -> {
                    result = JSONObject(result).toString(4)
                    Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result).toString(4)}"
                    )
                }
                result.isJsonArray() -> {
                    result = JSONObject(result).toString(4)
                    Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result).toString(4)}")
                }
                else -> {
                    try {
                        Log.d(
                            Constants.TAG, "${HomeViewModel::class.java.simpleName} / message : ${JSONObject(result).toString(4)}"
                        )
                    } catch (e: Exception) {
                        Log.d(
                            Constants.TAG, "${HomeViewModel::class.java.simpleName} / error : $e, message : $result"
                        )
                    }
                }
            }
 */