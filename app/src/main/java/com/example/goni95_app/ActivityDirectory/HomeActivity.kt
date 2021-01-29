package com.example.goni95_app.ActivityDirectory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goni95_app.R
import com.example.goni95_app.databinding.ActivityHomeBinding
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.SEARCH_TYPE
import com.example.goni95_app.util.onMyTextChanged


class HomeActivity : AppCompatActivity() {

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    private lateinit var binding: ActivityHomeBinding
    //private lateinit var include : CustomlayoutButtonBinding

    @SuppressLint("UseCompatLoadingForDrawables", "InflateParams")
    // Lint가 주석이 달린 요소에 대한 value로 지정된 경고를 무시하는 것을 의미
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //include = binding.include

        Log.d(Constants.TAG, "HomeActivity : onCreate() call")

        //val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val v = inflater.inflate(R.layout.customlayout_button, null)

        //라디오 그룹
        binding.searchRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // 라디오그룹은 사용하지 않으므로 _
            when (checkedId) {
                R.id.radiobutton_photo -> {
                    Log.d(Constants.TAG, "HomeActivity : 사진검색 라디오 버튼 클릭(search_text_layout 변경)")
                    binding.searchTextLayout.apply {
                        hint = "사진검색"
                        startIconDrawable =
                            resources.getDrawable(R.drawable.ic_photo_library, resources.newTheme())
                    }
                    currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.radiobutton_user -> {
                    Log.d(Constants.TAG, "HomeActivity : 사용자검색 라디오 버튼 클릭(search_text_layout 변경)")
                    binding.searchTextLayout.apply {
                        hint = "사용자검색"
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
            else binding.apply {
                include.root.visibility = View.INVISIBLE
                searchTextLayout.helperText = "검색어를 입력하시오"
            }
            if(it.toString().count() == 12) Toast.makeText(this, "검색어 제한 : 15자", Toast.LENGTH_SHORT).show()
        }

        binding.include.searchButton.setOnClickListener {
            Log.d(Constants.TAG, "HomeActivity SEARCH 버튼 클릭 - currentSearchType : ${currentSearchType}")
            handleSearchButton()
        }
    }// onCreate

    private fun handleSearchButton(){
        binding.include.btnProgress.visibility = View.VISIBLE
        binding.include.searchButton.text = ""

        Handler().postDelayed({
            binding.include.btnProgress.visibility = View.INVISIBLE
            binding.include.searchButton.text = "SEARCH"
        }, 1500)

    }
}