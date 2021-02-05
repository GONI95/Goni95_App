package com.example.goni95_app.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goni95_app.Model.SearchHistoryData
import com.example.goni95_app.R
import com.example.goni95_app.util.Constants

class SearchHistoryRecyclerViewAdapter(searchHistoryList: ArrayList<SearchHistoryData>,
    iSearchHistoryRecyclerView: ISearchHistoryRecyclerView) : RecyclerView.Adapter<SearchHistoryViewHolder>(){
    private var searchHistoryList = searchHistoryList

    //interface
    private var iSearchHistoryRecyclerView : ISearchHistoryRecyclerView

    init {
        Log.d(Constants.TAG, "SearchHistoryRecyclerViewAdapter - init() called")
        this.iSearchHistoryRecyclerView = iSearchHistoryRecyclerView
    }

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_searchhistory, parent, false)
            , iSearchHistoryRecyclerView)
        //LayoutInflater.from() : LI에 static으로 정의된 LI.from()을 통해 LI를 만드는 방법
        //내부적으로 getSystemService를 호출하고 있으며, 같은 context에서는 같은 객체를 리턴하기
        // 때문에 굳이 멤버 변수로 선언해 놓지 않고 필요할 때 마다 호출해서 사용해도 괜찮다.

        //inflate() : resource: View를 만들고 싶은 레이아웃 파일의 id
        //root: 생성될 View의 parent를 명시해줍니다. null일 경우 LayoutParams 값을
        // 설정할 수 없어 XML 내의 최상위 android:layout_xxxxx 값들이 무시되어 merge tag를 사용할 수 없다
        // attachToRoot : true로 설정하면 root의 자식 View로 자동으로 추가됨, 이때 root는 null일 수 없다
        // return : attachToRoot에 따라서 리턴값이 달라집니다. true일 경우 root, false일 경우 xml 내 최상위 뷰가 리턴

    }
    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다
    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bindWithView(searchHistoryList[position])
    }
    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return searchHistoryList.size
    }
}