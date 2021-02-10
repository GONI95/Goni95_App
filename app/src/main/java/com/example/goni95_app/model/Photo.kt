package com.example.goni95_app.model

import java.io.Serializable

//생성자
data class Photo(var thumbnail: String?, var author: String?, var createdAt: String?, var likesCount: Int?) :Serializable
// Serializable을 상속받아 직렬화가 가능하도록 한다.