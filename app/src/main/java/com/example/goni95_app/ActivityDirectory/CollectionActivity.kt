package com.example.goni95_app.ActivityDirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.R
import com.example.goni95_app.util.Constants

class CollectionActivity : AppCompatActivity() {

    var photoList = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        Log.d(Constants.TAG, "CollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("searchTerm")
        // intent로 보낸 bundle을 받기

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        // 받은 bundle에서 직렬화하여 보낸 Photo타입의 리스트를 받기

        Log.d(Constants.TAG, "CollectionActivity - getBundleExtra / searchTerm : $searchTerm")
        Log.d(Constants.TAG, "CollectionActivity - getSerializable / arraysize : ${photoList.count()}")


    }
}