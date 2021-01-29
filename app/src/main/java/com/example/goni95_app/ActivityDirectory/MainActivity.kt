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

    // 제작하다가, 메인에 네비게이션 달아서 사용할지, 따로 사용할지 정해야함
}