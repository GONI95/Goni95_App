package com.example.goni95_app.ActivityDirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.Model.User
import com.example.goni95_app.R
import com.example.goni95_app.util.Constants

class UserInfoActivity : AppCompatActivity() {

    //데이터
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val searchTerm = intent.getStringExtra("searchTerm")
        // intent로 보낸 bundle을 받기
        val bundle = intent.getBundleExtra("array_bundle")

        userList = bundle?.getSerializable("user_array_list") as ArrayList<User>

        Log.d(Constants.TAG,
            "CollectionActivity - getSerializable / user_arraysize : ${userList.count()}"
        )
    }
}