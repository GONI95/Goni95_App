package com.example.goni95_app.Model

import java.io.Serializable

//생성자
data class User(var username: String, var profile_image: String) :Serializable
// Serializable을 상속받아 직렬화가 가능하도록 한다.