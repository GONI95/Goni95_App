package com.example.goni95_app.ActivityDirectory

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goni95_app.Model.Photo
import com.example.goni95_app.Model.SearchHistoryData
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityCollectionBinding
import com.example.goni95_app.recyclerview.ISearchHistoryRecyclerView
import com.example.goni95_app.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.goni95_app.recyclerview.SearchHistoryRecyclerViewAdapter
import com.example.goni95_app.retrofit.RetrofitManager
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.example.goni95_app.util.SharedPreferenceManager
import com.example.goni95_app.util.toFormatString
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

// 검색 인터페이스 설정 : https://developer.android.com/training/search/setup?hl=ko
// Behavior과 CoordinatorLayout 관계 : https://m.blog.naver.com/PostView.nhn?blogId=pistolcaffe&logNo=221016672922&proxyReferer=https:%2F%2Fwww.google.com%2F
//RxJava 메모리 누수 막기 : https://beomseok95.tistory.com/60
class CollectionActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, ISearchHistoryRecyclerView {

    //ViewBinding
    private lateinit var binding: ActivityCollectionBinding

    //서비뷰, 서치뷰 에디트
    private lateinit var mSearchView: androidx.appcompat.widget.SearchView
    private lateinit var mSearchViewEditText: EditText

    //데이터
    var photoList = ArrayList<Photo>()

    //CompositeDisposable에 subscribe() 함수의 반환형인 Disposable을 담아서 처리
    //CompositeDisposable class를 이용하여 생성된 모든 Observable을 생명주기에 맞춰 해제가 가능
    private var compositeDisposable = CompositeDisposable()

    //검색 기록 배열
    private var searchHistoryList = ArrayList<SearchHistoryData>()

    //어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter
    private lateinit var searchHistoryRecyclerViewAdapter: SearchHistoryRecyclerViewAdapter

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
        //PhotoGrid 리사이클러뷰 준비
        photoGridRecyclerViewSetting(photoList)

        // Listener를 연결
        binding.searchHistorySaveMode.setOnCheckedChangeListener(this)
        binding.searchHistoryClear.setOnClickListener(this)

        getsearchHistorySaveMode()  //검색어 저장모드 가져오기

        binding.topAppBar.title = searchTerm
        setSupportActionBar(binding.topAppBar)
        // ToolBar를 Activity의 App Bar로 사용할 수 있다.

        //저장될 검색 기록 가져오기
        this.searchHistoryList =
            SharedPreferenceManager.getSearchHistory() as ArrayList<SearchHistoryData>
        this.searchHistoryList.forEach {
            Log.d(Constants.TAG, "저장된 검색 기록 - it : ${it.timeSet} ${it.value}")
        }
        //검색 기록 리사이클러뷰 준비
        searchHistoryRecyclerViewSetting(searchHistoryList)

        //Home에서 입력한 searchTerm을 받아 검색어 저장 기록에 전달
        if (searchTerm != null) {
            insertSearchTermHistory(searchTerm)
        }
    }
    //onCreate

    override fun onDestroy() {
        //사용했으니 디스포저블 삭제
        compositeDisposable.clear()
        super.onDestroy()
    }

    //PhotoGrid 리사이클러뷰 준비 함수
    private fun photoGridRecyclerViewSetting(photoList: ArrayList<Photo>) {
        photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        photoGridRecyclerViewAdapter.submitList(photoList)  //생성자로 받는다면 필요없음

        binding.collectionRecyclerview.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        // context, 가로 item 수, 출력 방향, item의 첫, 끝 중 시작 위치
        binding.collectionRecyclerview.adapter = photoGridRecyclerViewAdapter
    }

    //검색 기록 리사이클러뷰 준비 함수
    private fun searchHistoryRecyclerViewSetting(searchHistoryList: ArrayList<SearchHistoryData>) {
        searchHistoryRecyclerViewAdapter = SearchHistoryRecyclerViewAdapter(
            searchHistoryList,
            iSearchHistoryRecyclerView = this
        )

        binding.searchHistoryRecyclerview.apply {
            val mlayoutManager = LinearLayoutManager(
                this@CollectionActivity,
                LinearLayoutManager.VERTICAL, true
            )
            //출력 위치를 반대로, 순서도 반대로
            mlayoutManager.stackFromEnd = true
            //출력 위치를 정상으로 되돌림

            layoutManager = mlayoutManager
            adapter = searchHistoryRecyclerViewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 매뉴를 생성

        Log.d(Constants.TAG, "CollectionActivity - onCreateOptionMenu() called")
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        // 매뉴인플레이터로 매뉴xml과 매개변수로 들어오는 매뉴와 연결

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        // System으로 부터 SearchManager를 가져옴

        this.mSearchView =
            menu?.findItem(R.id.search_menu_item)?.actionView as androidx.appcompat.widget.SearchView

        this.mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@CollectionActivity)
            // 위에서 정의한 구현할 setOnQueryTextListener와 현재 setOnQueryTextListener를 연결

            this.setOnQueryTextFocusChangeListener { _, hasFocus ->
                //hasFocus : SearchView의 활성화, 비활성화 상태를 가져오는 리스너
                when (hasFocus) {
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

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.more_menu_item -> {

            }
            R.id.search_menu_item -> {

                //textChanges() : editText의 내용이 있는지 없는지 확인
                //RxBinding을 통해서 text가 변경되면 Observable을 만듬
                val editTextChangeObservable = mSearchViewEditText.textChanges()

                // debounce 오퍼레이터 추가
                //Observable에서 발행된 item들의 원천인 Observable과 최종의 Subscriber 사이에서 조작
                val searchEditTextSubscription: Disposable =
                //글자가 입력되고 0.8초 후 onNext() 이벤트로 데이터 보내기
                    //debounce() : 연속적인 이벤트를 처리하는 흐름 제어 함수
                    editTextChangeObservable.debounce(800, TimeUnit.MILLISECONDS)
                        // subscribeOn() : 작업 스레드를 설정
                        // Schedulers.io() : 동기 I/O를 별도로 처리해 비동기 효율을 얻는 스케줄러
                        .subscribeOn(Schedulers.io())
                        //구독하여 이벤트에 대한 응답을 받게된다.
                        .subscribeBy(
                            onNext = {
                                Log.d(Constants.TAG, "onNext : $it")
                                if (it.isNotEmpty()) {
                                    searchPhotoApiCall(it.toString())
                                }
                            },
                            onComplete = {
                                //실행되면 흐름이 끊김
                                Log.d(Constants.TAG, "onComplete")
                            },
                            onError = {
                                //실행되면 흐름이 끊김
                                Log.d(Constants.TAG, "onError")
                            }
                        )
                compositeDisposable.add(searchEditTextSubscription)
                //Observable 객체에서 발행된 후 반환된 객체의 관리를 위해 compositeDisposable에 추가

                // SearchView의 EditText에 길이 제한, 컬러를 수정
                mSearchViewEditText.apply {
                    this.filters = arrayOf(InputFilter.LengthFilter(15))
                    this.setTextColor(Color.WHITE)
                    this.setHintTextColor(Color.WHITE)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


    // 1. 서치뷰 검색 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        // 키보드에서 돋보기를 클릭 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 버튼이 클릭, quary : $query")

        // isNullOrEmpty() : null, ""인 경우 true
        // 검색 버튼이 클릭이 되었으며, 빈 값이 아니면 저장
        if (!query.isNullOrEmpty()) {

            //searchPhotoApiCall(query)   //사진 검색 Api 호출

            binding.topAppBar.title = query

            insertSearchTermHistory(query)
            //검색어 모드가 true일 때 검색어 저장
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

        if (userInputText.count() == 15) {
            Toast.makeText(this, getString(R.string.String_length_limit), Toast.LENGTH_SHORT).show()
        }

        return true
    }

    // 3. Search_History_Save_Mode
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            binding.searchHistorySaveMode -> if (isChecked == true) {
                Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 활성화")
                SharedPreferenceManager.setSaveMode(isActivated = true)
            } else {
                Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 비활성화")
                SharedPreferenceManager.setSaveMode(isActivated = false)
            }
        }
    }

    // 4. Search_History_Clear (검색어 전체 삭제)
    override fun onClick(v: View?) {
        // 검색 결과가 없을땐 메서드를 생성해 Invisible 처리하는 것도 좋아보이지만 굳이 할 필요없어보임

        when (v) {
            binding.searchHistoryClear -> {
                Log.d(Constants.TAG, "CollectionActivity - 검색어 삭제 호출")

                if (searchHistoryList.size != 0) {
                    SharedPreferenceManager.clearSearchHistoryList()
                    searchHistoryList.clear()
                    searchHistoryRecyclerViewAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "삭제할 검색어가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //searchHistory 아이템 삭제 (인터페이스에서 정의한 함수)
    override fun onSearchItemDeleteClicked(position: Int) {
        Log.d(Constants.TAG, "CollectionActivity - onSearchItemDeleteClicked() called : $position")
        searchHistoryList.removeAt(position)    // list에서 해당 위치 데이터 삭제
        SharedPreferenceManager.storeSearchHistory(searchHistoryList)   // 해당 list로 덮어씀
        searchHistoryRecyclerViewAdapter.notifyDataSetChanged() //데이터 변경을 알리고 새로 씀
    }

    //searchHistory 검색어 아이템 이벤트 (인터페이스에서 정의한 함수)
    override fun onSearchItemClicked(position: Int) {
        Log.d(Constants.TAG, "CollectionActivity - onSearchItemClicked() called : $position")

        //받아온 위치값을 이용해 list의 위치값에 element를 찾아 api를 call
        val query = searchHistoryList[position].value

        searchPhotoApiCall(query)
        binding.topAppBar.title = query
        insertSearchTermHistory(query)
        binding.topAppBar.collapseActionView()
    }

    //사진 검색 api 호출
    private fun searchPhotoApiCall(query: String?) {
        RetrofitManager.instance.searchPhotos(searchTerm = query, completion = { status, list ->
            when (status) {
                RESPONSE_STATE.OK -> {
                    Log.d(Constants.TAG, "CollectionActivity - searchPhotoApiCall() called")

                    if (list != null) {
                        photoList.clear()
                        photoList = list
                        photoGridRecyclerViewAdapter.submitList(photoList)
                        photoGridRecyclerViewAdapter.notifyDataSetChanged()
                    }
                }
                RESPONSE_STATE.NONE -> {
                    Toast.makeText(this, "검색 결과가 없습니다: $query", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 검색어 저장
    private fun insertSearchTermHistory(searchTerm: String) {
        Log.d(Constants.TAG, "CollectionActivity - insertSearchTermHistory() called")

        if (SharedPreferenceManager.getSaveMode() == true) {
            Log.d(Constants.TAG, "저장 모드 확인 후 진입완료")
            // 중복 아이템 삭제

            val iter = searchHistoryList.iterator()
            while (iter.hasNext()) {
                val iter_value = iter.next()
                if (searchTerm.equals(iter_value.value)) {
                    Log.d(Constants.TAG, "value: ${iter_value}")
                    Log.d(Constants.TAG, "serchTerm: $searchTerm")

                    iter.remove()
                    Log.d(Constants.TAG, "삭제 작업했습니다.")
                    break
                }
            }
            // SearchHistoryDate 형식의 인스턴스를 생성
            val newSearchData =
                SearchHistoryData(timeSet = Date().toFormatString(), value = searchTerm)

            // 검색 기록 배열에 인스턴스를 추가
            this.searchHistoryList.add(newSearchData)

            // 기존 데이터에 덮어쓰기
            SharedPreferenceManager.storeSearchHistory(searchHistoryList)
            this.searchHistoryRecyclerViewAdapter.notifyDataSetChanged()
        } else {
            Log.d(Constants.TAG, "검색어 저장 기능 꺼져있음.")
        }
    }

    //검색어 저장 모드를 shared에서 가져옴
    fun getsearchHistorySaveMode() {
        val getSaveMode = SharedPreferenceManager.getSaveMode()
        if (getSaveMode) {
            binding.searchHistorySaveMode.isChecked = true
        } else {
            binding.searchHistorySaveMode.isChecked = false
        }
    }
}