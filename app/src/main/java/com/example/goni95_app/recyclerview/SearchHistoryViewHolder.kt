package com.example.goni95_app.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.goni95_app.Model.SearchHistoryData
import com.example.goni95_app.databinding.LayoutSearchhistoryBinding
import com.example.goni95_app.util.Constants

class SearchHistoryViewHolder(itemView : View,
                              iSearchHistoryRecyclerView: ISearchHistoryRecyclerView)
                            : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private var binding: LayoutSearchhistoryBinding = LayoutSearchhistoryBinding.bind(itemView.rootView)

    private lateinit var iSearchHistoryRecyclerView: ISearchHistoryRecyclerView

    private val valueTextView = binding.valueTextview
    private val timeSetTextView = binding.timeSetTextview
    private val deleteImageButton = binding.deleteImageView
    private val constraintSearchHistoryItem = binding.constraintSearchHsitoryItem

    init {
        // Listener를 연결
        deleteImageButton.setOnClickListener(this)
        constraintSearchHistoryItem.setOnClickListener(this)
        this.iSearchHistoryRecyclerView = iSearchHistoryRecyclerView
    }

    fun bindWithView(searchHistoryItem : SearchHistoryData){
        Log.d(Constants.TAG, "SearchHistoryViewHolder - bindWithView() called")
        valueTextView.text = searchHistoryItem.value
        timeSetTextView.text = searchHistoryItem.timeSet
    }

    override fun onClick(v: View?) {
        Log.d(Constants.TAG, "SearchHistoryViewHolder - onClick() called")
        when(v){
            deleteImageButton -> {
                Log.d(Constants.TAG, "SearchHistoryViewHolder - deleteImageButton")
                iSearchHistoryRecyclerView.onSearchItemDeleteClicked(adapterPosition)
                //인터페이스에 정의된 아이템 삭제 함수에 adapterPosition(해당 포지션 알려줌) 전달
            }
            constraintSearchHistoryItem -> {
                Log.d(Constants.TAG, "SearchHistoryViewHolder - constraintSearchHistoryItem")
                iSearchHistoryRecyclerView.onSearchItemClicked(adapterPosition)
            }
        }
    }
}