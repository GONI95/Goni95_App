package com.example.goni95_app.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

// 문자열이 제이슨 형태인지, 제이슨 배열 형태인지 확인
fun String?.isJsonObject():Boolean {
    return this?.startsWith("{") == true && this.endsWith("}")
    // 위와 같으면 true 아니면 false
}
fun String?.isJsonArray():Boolean {
    return this?.startsWith("[") == true && this.endsWith("]")
}

/*
if(this?.startsWith("{") == true && this.endsWith("}")) return true
else return false
 */

// 코틀린 확장 함수(Kotlin extension functions)
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){
    // 빈 값이 올 수 있으니 Editable?
    // this == EditText
    this.addTextChangedListener(object : TextWatcher {
        // TextWatcher : TextWatcher가 편집이 가능한 항목에 첨부되면 텍스트가 변경될 때 해당 메서드가 호출된다.
        // 's' - 현재 입력되있는 값, 'start' - s에서 새로 추가된 문자열의 시작 위치, 'count' - s에 새로운 문자열이 추가된 후의 길이, 'after' - 새로 추가될 문자열 길이
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // 's' 문자열에서 "start" 위치로 부터 "count" 길이만큼 "after"로 변경될 예정임을 알림
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 's'가 start 위치로 부터 count 길이만큼 변경되었다는 것을 알려준다. 이전 문자열에서 before 길이만큼 변경되었다는 것을 알린다.
        }

        override fun afterTextChanged(editable: Editable?) {
            // 's' 내의 어떤 문자열이 변경되었다는 것을 알려준다.
            completion(editable)
        }
    })
}

// 날짜 포맷
fun Date.toFormatString() : String{
    var format = SimpleDateFormat("yy.MM.dd  HH:mm:ss")
    // 시:분:초 포맷으로 Date의 포맷을 정한다.
    return format.format(this)
}
