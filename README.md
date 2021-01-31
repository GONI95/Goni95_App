# Goni95_App
# 사용 기술
* [1. Kotlin Extension Functions](#Kotlin-Extension-Functions)
* [2. retrofit-config](#retrofit-config)
* 

<br><br><br>
# 특징
* [1. Material NoActionBar](#Material-NoActionBar)
* [2. Material Theme Handle](#Material-Theme-Handle)

------------


<br><br><br><br><br>
# git과 안드로이드 프로젝트 연결
<pre>
- GitBash 처음 사용할 경우 : git config --global user.name "Your Name Here" 
                            git config --global user.email "your_email@youremail.com"

- Repository를 생성 : mkdir /MyProject

- 디렉토리로 이동 : cd ~/MyProject (로컬 컴퓨터의 최상위 단계 디렉토리, Users 폴더를 의미)

- 로컬 저장소와 깃허브 원격 저장소 연결 : git remote add origin 깃 주소

- 리모트 저장소 연결조회 : git remote와 git remote -v

- Readme.md 파일 생성 : touch Readme.md

- Readme.md 글 작성 : vi Readme.md (insert모드 전환 : i, 취소 : esc, 저장 : :wq)

- 로컬 저장소에 프로젝트 생성

- 로컬 저장소에 추가해놓기 : git add -A

- 커밋(+메시지) : git commit -m “init Project”

- 파일 올리기 : git push

- 브런치 생성 : git branch 브런치명

- 브런치 변경 : git checkout 브런치명

- 브런치에서 올리기 : git add -A
                     git commit -m "메시지"
                     git push --set-upstream 로컬저장소 브런치명 (로컬을 브런치로 올리기)
                         
- master 브런치 합병 : git checkout 마스터브런치명
                      git merge 브런치명
                      git push
                      git pull 로컬저장소명 마스터브런치명                
</pre>


<br><br><br><br><br>
# Branch : test
## Material-NoActionBar

<br>

* themes.xml 
~~~xml
    <style name="Theme.MyApp" parent="Theme.MaterialComponents.Light.NoActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/white</item>
        <!-- Secondary brand color. -->
        <item name="colorSecondary">@color/teal_200</item>
        <item name="colorSecondaryVariant">@color/teal_700</item>
        <item name="colorOnSecondary">@color/black</item>
        <!-- Status bar color. -->
        <item name="android:statusBarColor" tools:targetApi="l">?attr/colorPrimaryVariant</item>
        <!-- Customize your theme here. -->
    </style>
~~~

<br>

* AndroidManifest.xml
~~~xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MyApp">
~~~


<br><br><br><br><br>
# Branch : 01_ui
## Kotlin-Extension-Functions 

<br>

* Extensions.kt
~~~kotlin
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
      
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
~~~

<br>

* HomeActivity.kt
~~~kotlin
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
      
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
~~~

<br><br><br>
## Material-Theme-Handle

<br>

* activity_home
~~~xml
        <RadioGroup
            android:id="@+id/search_radioGroup"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="30dp"
            android:orientation="horizontal"
            app:layout_constraintBottom_toTopOf="@+id/search_text_layout"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/imageView3">

            <!-- 메테리얼 사진검색 라디오버튼 -->
            <com.google.android.material.radiobutton.MaterialRadioButton
                android:id="@+id/radiobutton_photo"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:checked="true"
                android:text="@string/Photo_search" />

            <!-- 메테리얼 사용자검색 라디오버튼 -->
            <com.google.android.material.radiobutton.MaterialRadioButton
                android:id="@+id/radiobutton_user"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:checked="false"
                android:text="@string/User_search" />
        </RadioGroup>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/search_text_layout"
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="30dp"
            android:hint="@string/Photo_search"
            app:boxStrokeColor="@color/design_default_color_primary"
            app:counterEnabled="true"
            app:counterMaxLength="15"
            app:endIconMode="clear_text"
            app:helperText="@string/Enter_search_word"
            app:layout_constraintBottom_toTopOf="@+id/include"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.075"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/search_radioGroup"
            app:shapeAppearance="@style/ShapeAppearance.MaterialComponents.MediumComponent"
            app:startIconDrawable="@drawable/ic_photo_library">
            <!-- hint부터 : 클릭 시 힌트 출력, 입력 창 아래 설명추가, 글자 수 카운트 기능 활성
            , 글자 수 설정, 입력창 시작부분에 아이콘, 클릭 시 입력창 색 변화, 입력시 클리어 버튼 -->

            <!-- 메테리얼 EditText -->
            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/search_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="10dp"
                android:maxLength="15" />

        </com.google.android.material.textfield.TextInputLayout>
~~~



<br><br><br><br><br>
# Branch : 02_retrofit
## retrofit-config

<br>

* Extensions.kt
~~~kotlin
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
      
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
~~~
