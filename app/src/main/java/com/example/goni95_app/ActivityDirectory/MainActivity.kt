package com.example.goni95_app.ActivityDirectory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goni95_app.R

//https://www.youtube.com/watch?v=cKvemtEP-Vw&list=PLgOlaPUIbynrDSTJxS8AaE-2zYvPCjWtF&index=22
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    // mvvm 패턴 -> 사용자 검색 -> 메뉴 클릭으로 검색어 히스토리 출력 -> 좋아요 클릭하면 서버로 보낼 수 있는
    // 방법이 있는지 봐야함
}