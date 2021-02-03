package com.example.goni95_app.ActivityDirectory

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityCollectionBinding
import com.example.goni95_app.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.goni95_app.util.Constants

// 검색 인터페이스 설정 : https://developer.android.com/training/search/setup?hl=ko
// Behavior과 CoordinatorLayout 관계 : https://m.blog.naver.com/PostView.nhn?blogId=pistolcaffe&logNo=221016672922&proxyReferer=https:%2F%2Fwww.google.com%2F
class CollectionActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener{

    //ViewBinding
    private lateinit var binding: ActivityCollectionBinding

    //서비뷰, 서치뷰 에디트
    private lateinit var mSearchView: androidx.appcompat.widget.SearchView
    private lateinit var mSearchViewEditText: EditText

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
        Log.d(
            Constants.TAG,
            "CollectionActivity - getSerializable / arraysize : ${photoList.count()}"
        )

        // Listener를 연결
        binding.searchHistorySaveMode.setOnCheckedChangeListener(this)
        binding.searchHistoryClear.setOnClickListener(this)

        binding.topAppBar.title = searchTerm
        setSupportActionBar(binding.topAppBar)
        // ToolBar를 Activity의 App Bar로 사용할 수 있다.

        photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()

        photoGridRecyclerViewAdapter.submitList(photoList)  //생성자로 받는다면 필요없음

        binding.collectionRecyclerview.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        // context, 가로 item 수, 출력 방향, item의 첫, 끝 중 시작 위치
        binding.collectionRecyclerview.adapter = photoGridRecyclerViewAdapter

    }   //onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 매뉴를 생성

        Log.d(Constants.TAG, "CollectionActivity - onCreateOptionMenu() called")
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        // 매뉴인플레이터로 매뉴xml과 매개변수로 들어오는 매뉴와 연결

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        // System으로 부터 SearchManager를 가져옴

        this.mSearchView = menu?.findItem(R.id.search_menu_item)?.actionView as androidx.appcompat.widget.SearchView

        this.mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@CollectionActivity)
            // 위에서 정의한 구현할 setOnQueryTextListener와 현재 setOnQueryTextListener를 연결

            this.setOnQueryTextFocusChangeListener { _, hasFocus ->
                //hasFocus : SearchView의 활성화, 비활성화 상태를 가져오는 리스너
                when(hasFocus){
                    // Linear_Search_History 활성화 유무를 변경
                    true -> {
                        Log.d(Constants.TAG, "CollectionActivity - 서치뷰 활성화")
                        binding.linearSearchHistory.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(Constants.TAG, "CollectionActivity - 서치뷰 비활성화")
                        binding.linearSearchHistory.visibility = View.INVISIBLE
                    }
                }
            }

            // SearchView의 EditText를 가져온다.
            mSearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        
        // SearchView의 EditText에 길이 제한, 컬러를 수정
        mSearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(15))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        return true
    }

    // 1. 서치뷰 검색 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        // 키보드에서 돋보기를 클릭 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 버튼이 클릭, quary : $query")

        // isNullOrEmpty() : null, ""인 경우 true
        if (!query.isNullOrEmpty()) {
            binding.topAppBar.title = query

        }
        //this.mSearchView.setQuery("", false)    // SearchView의 입력값을 빈값으로 초기화
        //this.mSearchView.clearFocus()     // 키보드가 내려간다
        this.binding.topAppBar.collapseActionView()   // 액션뷰가 닫힌다.

        return true
    }

    // 2. 서치뷰 입력 이벤트
    override fun onQueryTextChange(newText: String?): Boolean {
        // text 입력 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 값이 변경, newText : $newText")

        val userInputText = newText.let { it } ?: ""

        if (userInputText.count() == 15){
            Toast.makeText(this, getString(R.string.String_length_limit), Toast.LENGTH_SHORT).show()
        }

        return true
    }

    // 3. Search_History_Save_Mode
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView){
            binding.searchHistorySaveMode ->
                if (isChecked == true){
                    Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 활성화")
            } else {
                    Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 비활성화")
            }
        }
    }

    // 4. Search_History_Clear
    override fun onClick(v: View?) {
        when(v){
            binding.searchHistoryClear -> 
                Log.d(Constants.TAG, "CollectionActivity - 검색어 삭제 호출")
        }
    }

}