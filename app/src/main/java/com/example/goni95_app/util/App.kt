package com.example.goni95_app.util

import android.app.Application

// 매니패스트 파일에 android:name을 해당 class 설정
class App : Application() {
    companion object{
        // 싱글턴 패턴
        lateinit var instance: App
            private set
        // 자기자신을 가져온다.
    }

    //onCreate()
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}