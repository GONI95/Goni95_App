package com.example.goni95_app.util

import android.content.Context
import android.util.Log
import com.example.goni95_app.App
import com.example.goni95_app.Model.SearchHistoryData
import com.google.gson.Gson

// Gson 가이드 : https://github.com/google/gson/blob/master/UserGuide.md
// 메모리를 사용하고 그것을 계속 사용하기 위해 싱글턴
object SharedPreferenceManager {
    //SharedPreference의 KEY로 사용될 상수를 선언
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"  // shared 이름 key
    private const val KEY_SEARCH_HISTORY = "key_search_history" // shared에 저장될 data key

    //저장 모드
    private const val SHARED_SEARCH_HISTORY_MODE = "shared_search_history_mode"
    private const val KEY_SAVE_MODE = "key_save_mode"

    //검색어 저장 모드 설정
    fun setSaveMode(isActivated: Boolean){
        Log.d(Constants.TAG, "SharedPreferenceManager - setSaveMode() called")

        //sharedPreference 세팅
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        //sharedPreference editor 세팅
        val editor = shared.edit()

        editor.putBoolean(KEY_SAVE_MODE, isActivated)
        editor.apply()  // 변경사항 저장
    }

    // 검색어 저장 모드 가져오기
    fun getSaveMode() : Boolean {
        Log.d(Constants.TAG, "SharedPreferenceManager - getSearchHistoryMode() called")

        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        //저장되어있던 data를 가져오기
        return shared.getBoolean(KEY_SAVE_MODE, false)
    }

    //검색 목록 저장(Gson - 컨버팅)
    fun storeSearchHistory(searchHistoryList: MutableList<SearchHistoryData>){
        Log.d(Constants.TAG, "SharedPreferenceManager - storeSearchHistory() called")

        //매개변수로 들어온 배열 -> 문자열로 변환(toJson() : java -> json
        // 객체의 인스턴스를 Json으로 직렬화 하는 것도 가능)
        val searchHistoryString : String = Gson().toJson(searchHistoryList)
        Log.d(Constants.TAG, "storeSearchHistory() - sHS : $searchHistoryString")

        //sharedPreference 세팅
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        //sharedPreference editor 세팅
        val editor = shared.edit()

        editor.putString(KEY_SEARCH_HISTORY, searchHistoryString)
        editor.apply()  // 변경사항 저장
    }

    // 검색 목록 가져오기
    fun getSearchHistory() : MutableList<SearchHistoryData>{
        Log.d(Constants.TAG, "SharedPreferenceManager - getSearchHistory() called")

        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        //저장되어있던 data를 가져오기
        val storeSearchHistoryString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storeSearchHistoryList = ArrayList<SearchHistoryData>()

        if(storeSearchHistoryString.isNotEmpty()){
            //저장된 문자열을 객체 배열로 변경
            storeSearchHistoryList =
                Gson().fromJson(storeSearchHistoryString, Array<SearchHistoryData>::class.java)
                    .toMutableList() as ArrayList<SearchHistoryData>
            // fromJson() : json -> java
        }

        //sharedPreference editor 세팅
        return storeSearchHistoryList
    }

    // 검색 목록 지우기
    fun clearSearchHistoryList(){
        Log.d(Constants.TAG, "SharedPreferenceManager - clearSearchHistoryList() called")

        //sharedPreference 세팅
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        //sharedPreference editor 세팅
        val editor = shared.edit()

        editor.clear()  // 해당 데이터 삭제
        
        editor.apply()  // 변경사항 저장
    }
}
