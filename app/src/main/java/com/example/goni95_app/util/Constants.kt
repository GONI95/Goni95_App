package com.example.goni95_app.util

object Constants {
    const val TAG : String = "로그"
    // 일반적으로 val는 런타임에 할당되지만 const val는 컴파일 시간에 결정되는 상수
    // 즉, const는 함수, 클래스의 생성자에게도 결코 할당될 수 없고, 기본 자료형만 할당되어야한다.
}

enum class SEARCH_TYPE{
    // 코드의 단순화, 가독성(인스턴스 생성, 상속 방지/ 상수 값의 타입 안정성 보장)
    // 레트로핏에 서치 타입에 넣으려고 생성
    PHOTO,
    USER
}