# Goni95_App
# 사용 기술
* [1. Kotlin Extension Functions](#Kotlin-Extension-Functions)
* [2. retrofit](#retrofit-config)
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
//문자열이 제이슨 형태인지. 제이슨 배열 형태인지 확인
fun String?.isJsonObject():Boolean {
    return this?.startsWith("{") == true && this.endsWith("}")
    // 위와 같으면 true 아니면 false
}
fun String?.isJsonArray():Boolean {
    return this?.startsWith("[") == true && this.endsWith("]")
}
~~~

<br>

* Constants.kt
~~~kotlin
enum class RESPONSE_STATE {
    OK,
    FAIL
}

object API {
    const val BASE_URL = "https://api.unsplash.com/"
    const val CLIENT_ID = "YOUR ACCESS KEY"
    const val SEARCH_PHOTOS = "search/photos"
    const val SEARCH_USERS = "search/users"
}
~~~
### enum class의 상수들은 객체로 취급하며, 레트로핏 통신의 성공 유무를 구분하기 위해서 선언
### retrofit으로 네트워크 통신에 사용할 기본 자료형만 사용할 수 있는 상수를 object로 class를 정의(싱글턴 패턴)
<br>
    //람다식으로 response_state와 string을 Unit type(리턴값이 없이 없음을 나타냄)

<br>

* IRetrofit_Service.kt
~~~kotlin
interface IRetrofit_Service {
    @GET(API.SEARCH_PHOTOS)
    fun serachPhotos(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사진 검색 서비스 http://www.unsplash.com/serach/photos/?query=입력값

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm : String) : Call<JsonElement>
    // 사용자 검색 서비스
}
~~~

### 레트로핏은 HTTP API를 인터페이스로 변환해준다. @GET Annotation을 이용해 인수로 전달한 API.SEARCH_PHOTOS URL에 @Query Annotation을
사용해 URL feild를 나타내는 key에 value값을 추가하여 HTTP Request를 처리하도록 하고 반환되는 타입을 Call<객체타입>로 한다.

<br>

* RetrofitClient.kt
~~~kotlin
object RetrofitClient {
    // 레트로핏 클라이언트 선언

    private var retrofitClient: Retrofit? = null
    // private lateinit var retrofitClient: Retrofit (선택사항)

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(Constants.TAG, "RetrofitClient - getClient() called")

        //로깅 인터셉터 추가(OkHTTP 인스턴스 생성)
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위한 로깅 인터셉터 설정
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

                when {
                    message.isJsonObject() -> Log.d(
                        Constants.TAG, "RetrofitClient - getClient() - log() / message : ${JSONObject(message).toString(4)}"
                    )
                    message.isJsonArray() -> Log.d(
                        Constants.TAG, "RetrofitClient - getClient() - log() / message : ${JSONObject(message).toString(4)}"
                    )
                    else -> {
                        try {
                            Log.d(
                                Constants.TAG, "RetrofitClient - getClient() - log() / message : ${JSONObject(message).toString(4)}"
                            )
                        } catch (e: Exception) {
                            Log.d(
                                Constants.TAG, "RetrofitClient - getClient() - log() / error : $e, message : $message"
                            )
                        }
                    }
                }
            }
        })
        // 위에서 생성한 로깅 인터셉터를 okhttp client에 추가
        client.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))

        // 기본 파라미터 인터셉터 설정
        val baseParameterInterceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(Constants.TAG, "RetrofitClient - getClient() interceptor() / chain : $chain")
                //원래의 요청(baseParmeter가 들어가기 전의 요청)
                val originalRequest = chain.request()

                //Query Parameter 추가(CLIENT_ID를 Url에 추가)
                val addClientIdUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()

                val finalRequest = originalRequest.newBuilder()
                    .url(addClientIdUrl)
                    .method(originalRequest.method, originalRequest.body)
                    .build()

                return chain.proceed(finalRequest)
            }
        })
        // 위에서 생성한 기본 파라미터 인터셉터를 okhttp clent에 추가
        client.addInterceptor(baseParameterInterceptor)

        // 커넥션 타임아웃
        client.connectTimeout(3000, TimeUnit.MILLISECONDS)     //커넥션 작업의 타임아웃
        client.readTimeout(3000, TimeUnit.MILLISECONDS)       //읽기 작업의 타임아웃
        client.writeTimeout(3000, TimeUnit.MILLISECONDS)       //쓰기 작업의 타임아웃
        client.retryOnConnectionFailure(false)     //실패할 경우 다시 시도할 것인가?


        //빌더를 통해 인스턴스 생성
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()    
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                // 위에서 설정한 client로 retrofit client를 설정한다.
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}
~~~

### OkHttp의 Interceptor를 사용
### val client = OkHttpClient.Builder() : kHttpClient.Builder() 클래스를 사용해 인스턴스를 생성
### val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger { ... }) : 네트워크 요청과 응답에 대한 로그를 볼 수 있다.
### client.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)) : 생성한 client에 interceptor를 추가

### Retrofit을 이용해 REST API에 네트워크 요청을 보내기위해 
### retrofitClient = Retrofit.Builder() : Retrofit.Builder 클래스를 사용해 인스턴스를 생성
### .addConverterFactory(GsonConverterFactory.create()) : json 응답을 parsing하여 객체로 변환하기 위한 컨버터
### .baseUrl(baseUrl) : 요청할 서버의 기본 URL을 지정
### .build() : Retrofit 객체 생성









