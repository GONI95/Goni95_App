package com.example.goni95_app.recyclerview

interface ISearchHistoryRecyclerView {

    //SearchHistory 아이템 삭제
    fun onSearchItemDeleteClicked(position: Int)

    //SearchHistory 아이템 이벤트
    fun onSearchItemClicked(position: Int)

}