package com.example.goni95_app.ActivityDirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityCollectionBinding
import com.example.goni95_app.databinding.ActivityHomeBinding
import com.example.goni95_app.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.goni95_app.util.Constants

class CollectionActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityCollectionBinding

    //데이터
    var photoList = ArrayList<Photo>()

    //어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(Constants.TAG, "CollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("searchTerm")
        // intent로 보낸 bundle을 받기

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        // 받은 bundle에서 직렬화하여 보낸 Photo타입의 리스트를 받기

        Log.d(Constants.TAG, "CollectionActivity - getBundleExtra / searchTerm : $searchTerm")
        Log.d(Constants.TAG, "CollectionActivity - getSerializable / arraysize : ${photoList.count()}")

        binding.topAppBar.title = searchTerm

        photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()

        photoGridRecyclerViewAdapter.submitList(photoList)  //생성자로 받는다면 필요없음

        binding.collectionRecyclerview.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        // context, 가로 item 수, 출력 방향, item의 첫, 끝 중 시작 위치
        binding.collectionRecyclerview.adapter = photoGridRecyclerViewAdapter



    }   //onCreate
}