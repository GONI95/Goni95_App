# Goni95_App

# readme

### 1. git과 안드로이드 프로젝트 연결
<pre>
- GitBash 처음 사용할 경우 git config --global user.name "Your Name Here" git config --global user.email "your_email@youremail.com"

- Repository를 생성 : mkdir /MyProject

- 디렉토리로 이동 : cd ~/MyProject   (로컬 컴퓨터의 최상위 단계 디렉토리, 즉 Users 폴더를 의미)

- 로컬 저장소와 깃허브 원격 저장소 연결 : git remote add origin 깃 주소

- 리모트 저장소 연결조회 : git remote와 git remote -v

- Readme.md 파일 생성 : touch Readme.md

- Readme.md 글 작성 : vi Readme.md (insert모드 전환 : i, 취소 : esc, 저장 : :wq)

- 로컬 저장소에 프로젝트 생성

- 로컬 저장소에 추가해놓기 : git add -A

- 커밋(+메시지) : git commit -m “init Project”

- 파일 올리기 : git push

</pre>

### 2. build.gragle 라이브러리 추가 
<pre>
- implementation 'com.google.android.material:material:1.2.1'
</pre>


### 3. 메테리얼 테마를 NoActionBar로 적용
* themes.xml 
<code>
~~~xml
   <style name="Theme.MyApp" parent="Theme.MaterialComponents.NoActionBar">
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
</code>

* AndroidManifest.xml
<code>
~~~xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MyApp">
~~~
</code>
