package com.example.goni95_app.recyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goni95_app.App
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityHomeBinding
import com.example.goni95_app.databinding.LayoutPhotoitemBinding
import com.example.goni95_app.util.Constants

class PhotoItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    //포토아이템뷰홀더가 생성될 때 itemVIew를 생성자로 받아 상속받는 ViewHolder에 필요한 view를 넣어준다.

    private var binding: LayoutPhotoitemBinding = LayoutPhotoitemBinding.bind(itemView.rootView)

    //뷰를 가져온다
    private val photoImage = binding.ivPhoto
    private val photocreatedAt = binding.tvCreatedAt
    private val photolikesCount = binding.tvLikesCount

    // 데이터와 뷰를 묶음
    fun bindWithView(photoItem: Photo){
        Log.d(Constants.TAG, "PhotoItemViewHolder - bindWithView() called")

        photocreatedAt.text = photoItem.createdAt
        photolikesCount.text = photoItem.likesCount.toString()

        // https://github.com/bumptech/glide
        Glide.with(App.instance)    // context
            .load(photoItem.thumbnail)  // 출력시킬 사진
            .placeholder(R.drawable.ic_photo) // 작동되지 않을 시 기본값 설정
            .into(photoImage)   // View
    }
}