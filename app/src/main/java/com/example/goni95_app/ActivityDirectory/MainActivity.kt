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

    // 제작하다가, 메인에 네비게이션 달아서 사용할지, 따로 사용할지 그리고 MVVM 패턴으로 변경할지 정해야함
    // 파싱 작업은 완료, 정상적으로 데이터를 받아 파싱한 정보를 핸들링하는 작업을 해야함
}