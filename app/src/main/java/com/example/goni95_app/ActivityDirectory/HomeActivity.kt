package com.example.goni95_app.ActivityDirectory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityHomeBinding
import com.example.goni95_app.retrofit.RetrofitManager
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.RESPONSE_STATE
import com.example.goni95_app.util.SEARCH_TYPE
import com.example.goni95_app.util.onMyTextChanged

// 직렬화 : https://altongmon.tistory.com/814
class HomeActivity : AppCompatActivity() {

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("UseCompatLoadingForDrawables", "InflateParams")
    // Lint가 주석이 달린 요소에 대한 value로 지정된 경고를 무시하는 것을 의미
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(Constants.TAG, "HomeActivity : onCreate() call")

        //라디오 그룹
        binding.searchRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // 라디오그룹은 사용하지 않으므로 _
            when (checkedId) {
                R.id.radiobutton_photo -> {
                    Log.d(Constants.TAG, "HomeActivity : 사진검색 라디오 버튼 클릭(search_text_layout 변경)")
                    binding.searchTextLayout.apply {
                        hint = getString(R.string.Photo_search)
                        startIconDrawable =
                            resources.getDrawable(R.drawable.ic_photo_library, resources.newTheme())
                    }
                    currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.radiobutton_user -> {
                    Log.d(Constants.TAG, "HomeActivity : 사용자검색 라디오 버튼 클릭(search_text_layout 변경)")
                    binding.searchTextLayout.apply {
                        hint = getString(R.string.User_search)
                        startIconDrawable =
                            resources.getDrawable(R.drawable.ic_people, resources.newTheme())
                        // 새 테마 개체를 생성한다, 처음에는 비어있는 상태로 시작됨
                    }
                    currentSearchType = SEARCH_TYPE.USER
                }
            }
            Log.d(Constants.TAG, "HomeActivity OnCheckedChanged() - currentSearchType : ${currentSearchType}")
        }

        // text가 변경되었을 때
        binding.searchEditText.onMyTextChanged {
            // 입력된 글자가 있다면
            if(it.toString().count() > 0) binding.apply {
                // 검색 버튼 띄움
                include.root.visibility = View.VISIBLE
                searchTextLayout.helperText = " "
                // 스크롤뷰 올리기
                homeScrollview.scrollTo(0, 400)
            }
            // 입력된 글자가 없다면
            else binding.apply {
                include.root.visibility = View.INVISIBLE
                searchTextLayout.helperText = getString(R.string.Enter_search_word)
            }
            //검색어 제한 토스트
            if(it.toString().count() == 12) Toast.makeText(this, getString(R.string.String_length_limit), Toast.LENGTH_SHORT).show()
        }

        // SEARCH 버튼 이벤트
        binding.include.searchButton.setOnClickListener {
            Log.d(Constants.TAG, "HomeActivity SEARCH 버튼 클릭 - currentSearchType : ${currentSearchType}")

            //검색 api 호출
            val userSearchInput = binding.searchEditText.text.toString()
            RetrofitManager.instance.searchPhotos(searchTerm = userSearchInput, completion = {
                    response_state, responsePhotoArrayList ->

                when(response_state){
                    RESPONSE_STATE.OK -> {
                        Log.d(Constants.TAG, "HomeActivity api 호출 성공 : ${responsePhotoArrayList?.size}")

                        val intent = Intent(this, CollectionActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("photo_array_list", responsePhotoArrayList)
                        //bundle은 Parcelable 객체를 상속받아 구현된 직렬화 class로, bundle 객체는 내부적으로 HashMap을 사용하며,
                        // Parcelable로 구현되어 있어 간단한 데이터 전달에 유용하다.
                        // putSerializable()을 이용하면 객체가 Serialzable을 상속받고 있다면 객체를 보낼 수 있다.
                        // 직렬화 : 객체를 바이트 스트림으로 바꾸어, 객체에 저장된 데이터를 스트림에 쓰기위해 연속적인 serial 데이터로 변환
                        intent.putExtra("array_bundle", bundle)
                        //intent.putExtra()로 해당 번들을 넣는다.
                        intent.putExtra("searchTerm", userSearchInput)
                        // 사용자가 입력한 입력값
                        startActivity(intent)
                    }
                    RESPONSE_STATE.FAIL -> {
                        Log.d(Constants.TAG, "HomeActivity api 호출 실패 : ${responsePhotoArrayList}")
                    }
                }
            })

            handleSearchButton()
        }
    }// onCreate

    private fun handleSearchButton(){
        binding.include.btnProgress.visibility = View.VISIBLE
        binding.include.searchButton.text = ""

        Handler().postDelayed({
            binding.include.btnProgress.visibility = View.INVISIBLE
            binding.include.searchButton.text = getString(R.string.search)
        }, 1500)

    }
}