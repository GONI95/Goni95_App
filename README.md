# Goni95_App
# 사용 기술
* [1. Kotlin Extension Functions](#Kotlin-Extension-Functions)
* [2. retrofit + Gson](#retrofit-config)
* [3. OkHttp HttpLoggingInterceptor, Interceptor](#OkHttp-HttpLoggingInterceptor)
* [4. High Order Function](#High-Order-Function)
* [5. Application Class Handle](#Application-Class)
* [6. SharedPreference](#SharedPreference_config)


<br><br><br>
# 특징
* [1. Material NoActionBar](#Material-NoActionBar)
* [2. Material Theme Handle](#Material-Theme-Handle)
* [3. Grid RecyclerView](#Grid-RecyclerView)
* [4. SearchView](#SearchView_config)
* [5. SearchHistory 검색어 저장 모드, 전체 삭제, item 삭제, item 선택 구성](#SearchHistoryRecyclerView-config)

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
#### enum class의 상수들은 객체로 취급하며, 레트로핏 통신의 성공 유무를 구분하기 위해서 선언
#### retrofit으로 네트워크 통신에 사용할 기본 자료형만 사용할 수 있는 상수를 object로 class를 정의(싱글턴 패턴)
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

#### 레트로핏은 HTTP API를 인터페이스로 변환해준다. @GET Annotation을 이용해 인수로 전달한 API.SEARCH_PHOTOS URL에 @Query Annotation을
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

<br>

## OkHttp-HttpLoggingInterceptor
<https://square.github.io/okhttp/interceptors/>

#### val client = OkHttpClient.Builder() : OkHttpClient.Builder() 클래스를 사용해 인스턴스를 생성
#### val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger { ... }) : 네트워크 요청과 응답에 대한 로그를 볼 수 있다.

<br>

#### -NONE : 로그가 없습니다.
#### -BASIC : 요청 및 응답 라인을 기록합니다.
#### -HEADERS : 요청 및 응답 라인과 해당 헤더를 기록합니다.
#### -BODY : 요청 및 응답 라인과 해당 헤더 및 본문 (있는 경우)을 기록합니다.

<br>

#### client.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)) : 생성한 client에 interceptor를 추가

<br>

## OkHttp-Interceptor

#### 요청에 대해 Header 또는 Parameter 등을 재작성할 수 있다.
#### 응답으로 온 chain 객체를 이용해 요청에 대해 공통적인 data를 달아줄 때 유용하고 Retrofit Api 인터페이스 정의 시 @Header Annotation을 달아줄 필요가 없어진다.
####  return chain.proceed(finalRequest) : proceed()메서드로 서버에 통신하고 응답을 받아온다.

<br><br>

## Retrofit Client

#### Retrofit을 이용해 REST API에 네트워크 요청을 보내기위해 
#### retrofitClient = Retrofit.Builder() : Retrofit.Builder 클래스를 사용해 인스턴스를 생성
#### .addConverterFactory(GsonConverterFactory.create()) : json 응답을 parsing하여 객체로 변환하기 위한 컨버터
#### .baseUrl(baseUrl) : 요청할 서버의 기본 URL을 지정
#### .client(client.build()) : Interceptor로 생성한 OkHttpClient를 요청에 사용할 수 있도록 해준다. 
#### .build() : Retrofit 객체 생성

<br>

* IRetrofit_Service.kt
~~~kotlin
class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
        // 객체생성
    }

    // http call 생성
    // 레트로핏 인터페이스 가져오기
    private val iretrofitService : IRetrofit_Service? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit_Service::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let { it } ?: ""
        val call = iretrofitService?.serachPhotos(searchTerm = term).let { it } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.raw(): ${response.raw()}")
                Log.d(Constants.TAG, "RetrofitManager - onResponse() called : response.body(): ${response.body()}")

                completion(RESPONSE_STATE.OK, response.raw().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called : t: $t")

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }
 }
~~~

<br>

## Network Request

#### enqueue() : 비동기 Request를 보내고 Response가 돌아오면 콜백으로 앱에게 알려준다. 네트워크에 대한 작업은 비동기로 작업하도록 Background 스레드에서 처리해야 합니다.
#### onResponse(), onFailure() 콜백 메소드를 구현해야합니다. 수행한 Request에 대한 Response를 전달받아 처리할 수 있습니다.

<br><br>

## High-Order-Function

####  일반적으로 함수는 매개변수와 반환값이 데이터지만, 고차함수는 매개변수로 함수를 전달받거나, 함수를 반환하는 함수를 말합니다.
<https://androidtest.tistory.com/116> 
<br>
<https://taehyungk.github.io/posts/android-kotlin-high-order-function/>


<br><br><br><br><br>
# Branch : 03_grid_recyclerview
## Application-Class

<br>

* App.kt
~~~kotlin
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
~~~

#### 어플리케이션 컴포넌트들 사이에서 공동으로 멤버들을 사용할 수 있게 해주는 편리한 공유 클래스로, 컴포넌트들이 공통되게 사용할 목적으로 하는 것을 작성하면, 해당 내용을 어디서든 접근이 가능하게 되고, class를 AndroidManifest.xml에 상속받아 만든 class를 추가하면 됩니다. (예: context)

<br><br>

## Data-Parsing
~~~kotlin
 when (response.code()) {  // 응답 코드 구분
                    200 -> {
                        // 파싱 작업
                        response.body()?.let {
                            var parsedPhotoDataArray = ArrayList<Photo>()

                            // 최상위 JsonObject (total{}, results[])
                            val body = it.asJsonObject  // Json 객체
                            val results = body.getAsJsonArray("results") // result라는 Json Array
                            val total = body.get("total").asInt // 검색 결과 수

                            Log.d(
                                Constants.TAG,
                                "RetrofitManager - onResponse() called / total : ${total}"
                            )

                            // JsonArray result[] 내부의 JsonObject를 하나씩 받아 반복 수행
                            results.forEach { resultItem ->
                                val resultItemObject = resultItem.asJsonObject // get object
                                val user =
                                    resultItemObject.get("user").asJsonObject    // object -> user
                                val username = user.get("username").asString    // user -> username
                                val likeCount =
                                    resultItemObject.get("likes").asInt     // object -> likes
                                val thumbnail =
                                    resultItemObject.get("urls").asJsonObject.get("thumb").asString      // urls -> thumb
                                val createdAt =
                                    resultItemObject.get("created_at").asString  // object -> create_at

                                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                // 실제 Date 형태
                                val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                // 변경하려는 Date 형태

                                val outputDateString = formatter.format(parser.parse(createdAt))
                                // Date를 원하는 형태로 치환

                                //Log.d(Constants.TAG, "RetrofitManager - onResponse() called / outputDateString : ${outputDateString}")

                                val photoItem = Photo(
                                    author = username,
                                    likesCount = likeCount,
                                    thumbnail = thumbnail,
                                    createdAt = outputDateString
                                )

                                parsedPhotoDataArray.add(photoItem)
                                //ArrayList에 Photo 타입의 데이터를 추가가
                            }
                            completion(RESPONSE_STATE.OK, parsedPhotoDataArray)
                        }
                    }
                }
~~~
#### 비동기 콜백 메서드로 들어오는 Response를 parsing하는 작업


<br><br><br><br><br>
# Branch : 03_grid_recyclerview
## Intent, Bundle을  데이터 전달

<br>

* HomeActivity.kt
~~~kotlin
// SEARCH 버튼 이벤트
        binding.include.searchButton.setOnClickListener {
            Log.d(Constants.TAG, "HomeActivity SEARCH 버튼 클릭 - currentSearchType : ${currentSearchType}")

            //검색 api 호출
            val userSearchInput = binding.searchEditText.text.toString()
            RetrofitManager.instance.searchPhotos(searchTerm = userSearchInput, completion = {
                    response_state, responsePhotoArrayList ->
                    // responsePhotoArrayList : RetrofitManager.kt에서 Parsing한 Photo타입의 ArrayList

                when(response_state){
                    RESPONSE_STATE.OK -> {
                        Log.d(Constants.TAG, "HomeActivity api 호출 성공 : ${responsePhotoArrayList?.size}")

                        val intent = Intent(this, CollectionActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("photo_array_list", responsePhotoArrayList)
                        
                        intent.putExtra("array_bundle", bundle)
                        //intent.putExtra()로 해당 번들을 넣는다.
                        intent.putExtra("searchTerm", userSearchInput)
                        // 사용자가 입력한 입력값(AppBar의 Title로 사용)
                        startActivity(intent)
                    }
                    RESPONSE_STATE.FAIL -> {
                        Log.d(Constants.TAG, "HomeActivity api 호출 실패 : ${responsePhotoArrayList}")
                    }
                }
            })

            handleSearchButton()
        }
~~~

<br>

* Photo.kt
~~~kotlin
data class Photo(var thumbnail: String?, var author: String?, var createdAt: String?, var likesCount: Int?) :Serializable
~~~
<https://www.crocus.co.kr/1560>
#### response_state의 값이 OK인 경우 Intent를 이용해 CollectionActivity.kt로 이동
#### Intent를 이용해 Activity를 전환하는 과정에서 Unsplash API로 받아 Parsing한 data를 Bundle을 이용하여 직렬화하여 전달했다.
#### 직렬화 : 객체를 바이트 스트림으로 바꾸어, 객체에 저장된 데이터를 스트림에 쓰기위해 연속적인 serial 데이터로 변환하는 작업
#### Bundle : 여러가지의 타입의 값을 저장하는 Map class이고, Parcelable 객체를 상속받아 구현된 직렬화 class이다. Parcelable로 구현되어 있어 간단한 데이터 전달에 유용하다. 객체가 Serialzable을 상속받고 있다면 putSerializable() 메서드를 이용하여 객체를 보낼 수 있다.

<br>

* CollectionActivity.kt
~~~kotlin
 val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("searchTerm")
        // intent로 보낸 bundle을 받기

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        // 받은 bundle에서 직렬화하여 보낸 Photo타입의 리스트를 받기
~~~
#### 직렬화하여 전달된 데이터를 받는다.

<br>

## Grid-RecyclerView

<br>

1. RecyclerView의 각 Item으로 사용될 layout_photoitem.xml을 정의
2. Adapter, ViewHolder 정의

<br>

* PhotoGridRecyclerViewAdapter.kt
~~~kotlin
class PhotoGridRecyclerViewAdapter : RecyclerView.Adapter<PhotoItemViewHolder>() {
    private var photoList = ArrayList<Photo>()

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        return PhotoItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_photoitem, parent, false)) // layout, viewGroup, ?
    }
    
    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다
    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bindWithView(this.photoList[position])   // 아이템을 통해 뷰와 데이터가 연결되는 작업

    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return this.photoList.size
    }

    // 외부에서 어답터에 데이터 배열을 넣어준다.
    fun submitList(photoList: ArrayList<Photo>){
        this.photoList = photoList
    }
}
~~~

<br>

* PhotoItemViewHolder.kt
~~~kotlin
class PhotoItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    //포토아이템뷰홀더가 생성될 때 itemVIew를 생성자로 받아 상속받는 ViewHolder에 필요한 view를 넣어준다.

    private var binding: LayoutPhotoitemBinding = LayoutPhotoitemBinding.bind(itemView.rootView)

    //뷰를 가져온다
    private val photoImage = binding.ivPhoto
    private val photocreatedAt = binding.tvCreatedAt
    private val photolikesCount = binding.tvLikesCount

    // 데이터와 뷰를 묶음
    fun bindWithView(photoItem: Photo){
        Log.d(Constants.TAG, "PhotoItemViewHolder - bindWithView() called")

        photocreatedAt.text = photoItem.createdAt
        photolikesCount.text = photoItem.likesCount.toString()

        // https://github.com/bumptech/glide
        Glide.with(App.instance)    // context
            .load(photoItem.thumbnail)  // 출력시킬 사진
            .placeholder(R.drawable.ic_photo) // 작동되지 않을 시 기본값 설정
            .into(photoImage)   // View
    }
}
~~~

<br>

* CollectionActivity.kt
~~~kotlin
       photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()

        photoGridRecyclerViewAdapter.submitList(photoList)  //생성자로 받는다면 필요없음

        binding.collectionRecyclerview.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        // context, 가로 item 수, 출력 방향, item의 첫, 끝 중 시작 위치
        binding.collectionRecyclerview.adapter = photoGridRecyclerViewAdapter
~~~

<br>

activity_collection.xml
~~~xml
    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:liftOnScroll="true"
        app:liftOnScrollTargetViewId="@id/collection_recyclerview">
        <!-- recyclerview를 스크롤 시 appbar를 lift -->

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/topAppBar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:title="@string/page_title"
            app:menu="@menu/top_app_bar_menu"
            app:navigationIcon="@drawable/ic_menu"
            style="@style/Widget.MaterialComponents.Toolbar.Primary"
            app:layout_scrollFlags="scroll|enterAlways"
            />
        <!-- scroll : 이 뷰가 화면에서 사라질 수 있음을 나타낸다. scroll이 정의되지 않으면 뷰는 사라지지 않는다.
             enterAlways : scroll 옵션과 함께 사용될 시 위쪽으로 스크롤할 경우 사라지고, 아래쪽으로 스크롤할 경우 표시된다.
         -->

    </com.google.android.material.appbar.AppBarLayout>
~~~
#### RecyclerView를 아래로 scroll할 경우 AppBar가 lift 되도록 xml 속성을 설정 

<br><br><br><br><br>
# Branch : 04_appbar_search_ui
## SearchView-config

<br>

* CollectionActivity.kt
~~~kotlin
class CollectionActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

//서비뷰, 서치뷰 에디트
    private lateinit var mSearchView: androidx.appcompat.widget.SearchView
    private lateinit var mSearchViewEditText: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
      ...
      setSupportActionBar(binding.topAppBar)
      // ToolBar를 Activity의 App Bar로 사용할 수 있다.
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 매뉴를 생성

        Log.d(Constants.TAG, "CollectionActivity - onCreateOptionMenu() called")
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        // 매뉴인플레이터로 매뉴xml과 매개변수로 들어오는 매뉴와 연결

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        // System으로 부터 SearchManager를 가져옴

        this.mSearchView = menu?.findItem(R.id.search_menu_item)?.actionView as androidx.appcompat.widget.SearchView

        this.mSearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@CollectionActivity)
            // 위에서 정의한 구현할 setOnQueryTextListener와 현재 setOnQueryTextListener를 연결

            this.setOnQueryTextFocusChangeListener { _, hasFocus ->
                //hasFocus : SearchView의 활성화, 비활성화 상태를 가져오는 리스너
                when(hasFocus){
                    // Linear_Search_History 활성화 유무를 변경
                    true -> {
                        Log.d(Constants.TAG, "CollectionActivity - 서치뷰 활성화")
                        binding.linearSearchHistory.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(Constants.TAG, "CollectionActivity - 서치뷰 비활성화")
                        binding.linearSearchHistory.visibility = View.INVISIBLE
                    }
                }
            }

            // SearchView의 EditText를 가져온다.
            mSearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        
        // SearchView의 EditText에 길이 제한, 컬러를 수정
        mSearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(15))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        return true
    }
~~~
#### AppBar의 돋보기 OptionMenu를 SearchView와 연결하여, hint, 검색어 길이 제한, SearchView 활성화 유무 등을 설정했다. 

<br>

* CollectionActivity.kt
~~~kotlin
class CollectionActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener{
    
    override fun onCreate(savedInstanceState: Bundle?) {
      ...
    }
    
    // 1. 서치뷰 검색 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        // 키보드에서 돋보기를 클릭 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 버튼이 클릭, quary : $query")

        // isNullOrEmpty() : null, ""인 경우 true
        if (!query.isNullOrEmpty()) {
            binding.topAppBar.title = query

        }
        //this.mSearchView.setQuery("", false)    // SearchView의 입력값을 빈값으로 초기화
        //this.mSearchView.clearFocus()     // 키보드가 내려간다
        this.binding.topAppBar.collapseActionView()   // 액션뷰가 닫힌다.

        return true
    }
    // 2. 서치뷰 입력 이벤트
    override fun onQueryTextChange(newText: String?): Boolean {
        // text 입력 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 값이 변경, newText : $newText")

        val userInputText = newText.let { it } ?: ""

        if (userInputText.count() == 15){
            Toast.makeText(this, getString(R.string.String_length_limit), Toast.LENGTH_SHORT).show()
        }

        return true
    }

    // 3. Search_History_Save_Mode
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView){
            binding.searchHistorySaveMode ->
                if (isChecked == true){
                    Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 활성화")
            } else {
                    Log.d(Constants.TAG, "CollectionActivity - 검색어 저장기능 비활성화")
            }
        }
    }

    // 4. Search_History_Clear
    override fun onClick(v: View?) {
        when(v){
            binding.searchHistoryClear -> 
                Log.d(Constants.TAG, "CollectionActivity - 검색어 삭제 호출")
        }
    }

}
~~~
#### 1. onQueryTextSubmit() : 키보드에서 완료 또는 돋보기 버튼이 클릭될 시 입력된 text를 받을 수 있는 메소드
#### 설명 : 전달받은 text가 null 또는 ""가 아닌 경우 AppBar의 Title을 변경하고, SearchView를 닫는다.

#### 2. onQueryTextChange() : SearchView의 EditText에 값이 입력될 때 마다 호출되어 EditText에 입력된 text를 전달받을 수 있는 메소드
#### 설명 : 입력된 text의 문자 count()가 15를 넘지않도록 제한하고, Toast message 출력

### 3, 4. : 각각 클릭된 상황에 맞게, 저장 기능 활성화 유무와 검색어 삭제 로그만 출력되도록 했다.
<br>

* activity_collection.xml
~~~xml
 <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/collection_recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


<LinearLayout
        android:id="@+id/linear_search_history"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        android:visibility="invisible"
        android:orientation="vertical"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">
        <!-- layout_behavior : App bar의 하단에 위치할 위젯을 추가하면 App bar와 연결됨 -->

        <androidx.constraintlayout.widget.ConstraintLayout
            android:paddingHorizontal="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <TextView
                android:id="@+id/textView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/search_history_save"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/search_history_save_mode"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <com.google.android.material.switchmaterial.SwitchMaterial
                android:id="@+id/search_history_save_mode"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="3dp"
                android:checked="false"
                android:theme="@style/MySwitchButton"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toEndOf="@+id/textView"
                app:layout_constraintTop_toTopOf="parent" />

            <Button
                android:id="@+id/search_history_clear"
                android:textColor="@color/gray"
                android:text="@string/search_history_clear"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="?attr/selectableItemBackground"
                android:drawableEnd="@drawable/ic_delete_sweep"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>
    </LinearLayout>
~~~
#### Behavior과 CoordinatorLayout 관계 : <https://m.blog.naver.com/PostView.nhn?blogId=pistolcaffe&logNo=221016672922&proxyReferer=https:%2F%2Fwww.google.com%2F>
#### SearchView가 활성화 유무에 따라 대응하는 두 Layout이 표시되도록 하기위해서 RecyclerView에 대응하는 LinearLayout을 정의하였다. 

<br><br><br><br><br>
# Branch : 05_sharedPreference
## SharedPreference_config

<br>

* SharedPreferenceManager.kt
~~~kotlin
    // 메모리를 사용하고 그것을 계속 사용하기 위해 싱글턴
object SharedPreferenceManager {
    //SharedPreference의 KEY로 사용될 상수를 선언
    private const val SHARED_SHEARCH_HISTORY = "shared_search_history"  // shared 이름 key
    private const val KEY_SEARCH_HISTORY = "key_search_history" // shared에 저장될 data key

    //검색 목록 저장(Gson - 컨버팅)
    fun storeSearchHistory(searchHistoryList: MutableList<SearchHistoryData>){
        Log.d(Constants.TAG, "SharedPreferenceManager - storeSearchHistory() called")

        //매개변수로 들어온 배열 -> 문자열로 변환(toJson() : java -> json
        // 객체의 인스턴스를 Json으로 직렬화 하는 것도 가능)
        val searchHistoryString : String = Gson().toJson(searchHistoryList)
        Log.d(Constants.TAG, "storeSearchHistory() - sHS : $searchHistoryString")

        //sharedPreference 세팅
        val shared =
            App.instance.getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)

        //sharedPreference editor 세팅
        val editor = shared.edit()

        editor.putString(KEY_SEARCH_HISTORY, searchHistoryString)
        editor.apply()  // 변경사항 저장
    }

    // 검색 목록 가져오기
    fun getSearchHistory() : MutableList<SearchHistoryData>{
        Log.d(Constants.TAG, "SharedPreferenceManager - getSearchHistory() called")

        val shared =
            App.instance.getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)

        //저장되어있던 data를 가져오기
        val storeSearchHistoryString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storeSearchHistoryList = ArrayList<SearchHistoryData>()

        if(storeSearchHistoryString.isNotEmpty()){
            //저장된 문자열을 객체 배열로 변경
            storeSearchHistoryList =
                Gson().fromJson(storeSearchHistoryString, Array<SearchHistoryData>::class.java)
                    .toMutableList() as ArrayList<SearchHistoryData>
            // fromJson() : json -> java
        }

        //sharedPreference editor 세팅
        return storeSearchHistoryList
    }
}
~~~
#### Gson 가이드 : <https://github.com/google/gson/blob/master/UserGuide.md>

<br>

* CollectionActivity.kt
~~~kotlin

    //검색 기록 배열
    private var searchHisttoryList = ArrayList<SearchHistoryData>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        ...
         //저장될 검색 기록 가져오기
        this.searchHisttoryList = SharedPreferenceManager.getSearchHistory() as ArrayList<SearchHistoryData>

        this.searchHisttoryList.forEach {
            Log.d(Constants.TAG, "저장된 검색 기록 - it : ${it.timeSet} ${it.value}")
        }
    }

    // 1. 서치뷰 검색 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        // 키보드에서 돋보기를 클릭 시 호출되며, 입력된 text를 받음
        Log.d(Constants.TAG, "CollectionActivity - 검색 버튼이 클릭, quary : $query")

        // isNullOrEmpty() : null, ""인 경우 true
        // 검색 버튼이 클릭이 되었으며, 빈 값이 아니면 저장
        if (!query.isNullOrEmpty()) {
            binding.topAppBar.title = query

            // SearchHistoryDate 형식의 인스턴스를 생성
            val newSearchData = SearchHistoryData(timeSet = Date().toString(), value = query)

            // 검색 기록 배열에 인스턴스를 추가
            this.searchHisttoryList.add(newSearchData)

            // SharedPreferenceManager의 저장 메서드에 검색 기록 배열을 전달
            SharedPreferenceManager.storeSearchHistory(this.searchHisttoryList)
        }
        //this.mSearchView.setQuery("", false)    // SearchView의 입력값을 빈값으로 초기화
        //this.mSearchView.clearFocus()     // 키보드가 내려간다
        this.binding.topAppBar.collapseActionView()   // 액션뷰가 닫힌다.

        return true
    }
~~~


<br><br><br><br><br>
# Branch : 06_searchHistory_RecyclerView
## SearchHistoryRecyclerView-config

<br>

* ISearchHistoryRecyclerView.interface
~~~kotlin
interface ISearchHistoryRecyclerView {
    //SearchHistory 아이템 삭제
    fun onSearchItemDeleteClicked(position: Int)
    
    //SearchHistory 아이템 이벤트
    fun onSearchItemClicked(position: Int)
}
~~~

<br>

* CollectionActivity.kt
~~~kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        ...
        //검색 기록 리사이클러뷰 준비
        searchHistoryRecyclerViewSetting(searchHistoryList)
    }
    
     //검색 기록 리사이클러뷰 준비 함수
    private fun searchHistoryRecyclerViewSetting(searchHistoryList : ArrayList<SearchHistoryData>){
        searchHistoryRecyclerViewAdapter = SearchHistoryRecyclerViewAdapter(searchHistoryList,
            iSearchHistoryRecyclerView = this)  // Adapter로 interface를 넘김

        binding.searchHistoryRecyclerview.apply {
            val mlayoutManager = LinearLayoutManager(this@CollectionActivity,
                LinearLayoutManager.VERTICAL, true)
            //출력 위치를 반대로, 순서도 반대로
            mlayoutManager.stackFromEnd = true  
            //출력 위치를 정상으로 되돌림

            layoutManager = mlayoutManager
            adapter = searchHistoryRecyclerViewAdapter
        }
    }
    
    //searchHistory 아이템 삭제 (인터페이스에서 정의한 함수)
    override fun onSearchItemDeleteClicked(position: Int) {
        Log.d(Constants.TAG, "CollectionActivity - onSearchItemDeleteClicked() called : $position")
    }

    //searchHistory 아이템 이벤트 (인터페이스에서 정의한 함수)
    override fun onSearchItemClicked(position: Int) {
        Log.d(Constants.TAG, "CollectionActivity - onSearchItemClicked() called : $position")
    }
~~~

<br>

* SearchHistoryRecyclerViewAdapter.kt
~~~kotlin
class SearchHistoryRecyclerViewAdapter(searchHistoryList: ArrayList<SearchHistoryData>,
    iSearchHistoryRecyclerView: ISearchHistoryRecyclerView) : RecyclerView.Adapter<SearchHistoryViewHolder>(){
    
    //interface
    private var iSearchHistoryRecyclerView : ISearchHistoryRecyclerView

    init {
        Log.d(Constants.TAG, "SearchHistoryRecyclerViewAdapter - init() called")
        this.iSearchHistoryRecyclerView = iSearchHistoryRecyclerView
    }
    
     // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_searchhistory, parent, false)
            , iSearchHistoryRecyclerView)
    }
    ...
}
~~~

<br>

* SearchHistoryViewHolder.kt
~~~kotlin
 override fun onClick(v: View?) {
        Log.d(Constants.TAG, "SearchHistoryViewHolder - onClick() called")
        when(v){
            deleteImageButton -> {
                Log.d(Constants.TAG, "SearchHistoryViewHolder - deleteImageButton")
                iSearchHistoryRecyclerView.onSearchItemDeleteClicked(adapterPosition)
                //인터페이스에 정의된 아이템 삭제 함수에 adapterPosition(해당 포지션 알려줌) 전달
            }
            constraintSearchHistoryItem -> {
                Log.d(Constants.TAG, "SearchHistoryViewHolder - constraintSearchHistoryItem")
                iSearchHistoryRecyclerView.onSearchItemClicked(adapterPosition)
            }
        }
    }
~~~
#### Interface를 생성하여, 저장된 검색어를 삭제하기 위한 onSearchItemDeleteClicked(), 저장된 검색어에 클릭 이벤트를 위한 onSearchItemClicked() 추상 메서드를 정의하였다.
#### CollectionActivity는 정의한 interface를 상속받아, Adapter에 전달하고 정의한 추상 메서드를 구현하였다.
#### Adapter에선 전달받은 interface의 인스턴스를 ViewHolder에 전달하도록 했다.
#### ViewHoler에선 OnClickListener를 상속받아 구현한 onClick() 메서드에서 클릭 이벤트로 인해 받는 View에 따라 interface에 정의된 추상 메서드에 현재 adapter의 위치값을 전달하여, CollectionActivity에서 위치값을 전달받을 수 있도록 했다. 

### Adapter - ViewHolder와 layout의 연결
#### SearchHistoryViewHolder(View, interface) - RecyclerView에서 ViewHolder를 만들때 CustomView에서 xml로 정의된 View를 merge할 때,즉 View 만들기 위해서 LayoutInflater를 사용한다.
#### LayoutInflater.from() : LayoutInflater에 static으로 정의된 LayoutInflater.from()을 통해 LayoutInflater를 만드는 방법 중 하나입니다. 내부적으로 context#getSystemService를 호출하기 있고, 같은 context에선 같은 객체를 리턴하기 때문에 LayoutInflater를 멤버 변수로 선언해 놓지 않고 필요할 때마다 호출해서 사용해도 무방합니다.
#### inflate() : inflater에서 View 객체를 만들기 위해선 inflate()를 사용합니다. 
#### params 1 : resource - View를 만들고 싶은 레이아웃 파일의 id를 의미합니다.
#### params 2 : root - 생성될 View의 parent를 명시해줍니다. null일 경우 LayoutParams 값을 설정할 수 없어 XML 내의 최상위 android:layout_xxxxx 값들이 무시되어 merge tag를 사용할 수 없다
#### params 3 : attachToRoot - true로 설정하면 root의 자식 View로 자동으로 추가됨, 이때 root는 null일 수 없다
#### return : attachToRoot에 따라서 리턴값이 달라집니다. true일 경우 root, false일 경우 xml 내 최상위 뷰가 리턴

## ISSUU

#### SharedPreference에 정의한 Model의 데이터를 저장하고 출력하기 위해서 Gson을 이용하여 컨버팅하는 작업이 쉽지 않았다. 
