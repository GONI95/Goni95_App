package com.example.goni95_app.retrofit

import android.util.Log
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.isJsonArray
import com.example.goni95_app.util.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

// 오브젝트 : 자동 싱글턴 패턴
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


/*
import android.util.Log
import com.example.goni95_app.util.API
import com.example.goni95_app.util.Constants
import com.example.goni95_app.util.isJsonArray
import com.example.goni95_app.util.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

// 오브젝트 : 자동 싱글턴 패턴
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
 */