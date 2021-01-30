package com.example.goni95_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class HomeViewModel : ViewModel() {
    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService : IRetrofit_Service

    init {
        iretrofitService = (RetrofitClient.getClient(API.BASE_URL)?.create(
            IRetrofit_Service::class.java) ?: null) as IRetrofit_Service
    }


    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let { it } ?: ""

        viewModelScope.launch(Dispatchers.IO){
            val call = iretrofitService?.serachPhotos(searchTerm = term).let { it.toString() } ?: return@launch
            Log.d(Constants.TAG, "${HomeViewModel::class.java.simpleName} / result : ${JSONObject(call).toString(4)}")
            
            completion(RESPONSE_STATE.OK, JSONObject(call).toString(4))
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