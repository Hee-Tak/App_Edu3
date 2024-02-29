package com.tak.c90

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/**
 *
 * 파트24) ViewBinding 기법으로 효율적인 앱 개발을 해보자.
 *
 * C89) View 활용 기법들
 *
 *
 * <Glide>
 *
 * - 레이아웃 XML 파일에 선언한 View 객체를 코드에서 쉽게 이용하기 위한 기법
 *
 *
 * - findViewById()
 * - Butterknife (프레임워크의 일종)
 * - Kotlin Android Extension
 * - DataBinding (구글에서 제시)
 * - ViewBinding (구글에서 제시)
 *
 *
 *
 * * 안드로이드 UI 프로그램은 대부분 레이아웃 XML 파일을 이용한다. 이렇게 빼버리면 당연히 코드에 UI 준비코드가 안들어가니까, 알고리즘의 코드만 남으면 되고, 훨씬 좋은 방법.
 *
 *          layout XML                                      code
 *          <TextView
 *                  ....                                 Activity/Fragment...etc
 *
 *                                                          findViewById() <- 여태까지는 얘를 이용해서 View 객체 획득해왔음. (가장 기초적인 방법이면서 가장 단순하고 가장 코드가 길게 나오는 방법) <- 이 부분이 문제가 있다고 생각하여 개발자들이 뷰 객체를 획득할 더 편한 방법을 모색.
 *          />
 *
 *
 *
 * - findViewById()
 *          <?xml version="1.0" encoding="utf-8" ?>
 *              <TextView
 *                  android:id="@+id/nameResultView"/>
 *              <Button
 *                  android:id="@+id/button"/>
 *
 *
 *          lateinit var nameResultView: TextView
 *          lateinit var button: Button
 *          override fun onCreate(savedInstanceState: Bundle?) {
 *              super.onCreate(savedInstanceState)
 *              setContentView(R.layout.activity_main)
 *              nameResultView = findViewById<TextView>(R.id.nameResultView)        //실전 앱을 개발한다고 생각하면 View 갯수가 엄청 많아질텐데, 그럼 findViewById도 엄청쓸테니까...이거보다 더 효율적인 것을 찾는 거지
 *              button = findViewById<Button>(R.id.button)
 *
 *              button.setOnClickListener {
 *                  //........
 *              }
 *          }
 *
 *
 *
 * - Butterknife 프레임워크                      //이 프레임워크를 dependency 관계를 설정한 다음에 사용.
 *          @BindView(R.id.nameResultView)     //어노테이션으로 정보를 줘서 승부를 보는 것.   그럼 ButterKinfe 라고하는 프레임워크가 알아서 해당하는 뷰를 findViewById 를 써서, 대입하는 코드를 작성해주겠다 라는 개념
 *          lateinit var nameResultView: TextView
 *
 *          @BindView(R.id.button)
 *          lateinit var button: Button
 *
 *          var nameResult = "kkang"
 *          override fun onCreate(savedInstanceState: Bundle?) {
 *              super.onCreate(savedInstanceState)
 *              setContentView(R.layout.test1)
 *              ButterKnife.bind(this)
 *              nameResultView.text = nameResult
 *          }
 *
 *          @OnClick(R.id.button)               //이벤트도 마찬가지. 원래는 setOnClickListener 인데 이렇게 어노테이션에 아이디 값을 주고, 이런식으로 이벤트 콜백 함수에다가 어노테이션만 붙여놓으면, 실제 이벤트 콜백 함수명과 함수명이 다르다고 하더라도, 이벤트 발생 시에 자동으로 콜 된다.
 *          fun onButtonClick(view: View?) {    //원래는 onButtonClick이 아니라 onClick인데 이 정보는 어노테이션으로 줬으니까 상관없다는거
 *              //......
 *          }
 *
 *
 *
 * - Kotlin Android Extension                           //뷰를 이용하는 방법중에 코드적으로는 가장 짧다
 *          plugins {                                   //gradle 파일에다가 kotlin android extension 이용하겠다고 플러그인으로 선언만 해주면 됨.
 *              id 'kotlin-android-extensions'
 *          }
 *
 *          import kotlinx.android.synthetic.main.activity_main.*   //그런 다음에, xml 파일이 뭔지는 알려줘야함. import 문으로. xml파일명 붙여주는게 파일명따라 다르겠지(activity_main.*)
 *
 *          class MainActivity : AppCompatActivity() {
 *              override fun onCreate(savedInstanceState: Bundle?) {
 *                  super.onCreate(savedInstanceState)
 *                  setContentView(R.layout.activity_main)
 *
 *                  nameResultView.text = "hello world"     //아이디 값으로 변수명이 이미 지정되어 있는 것.
 *                  button.setOnClickListener {             //아이디 값으로 변수명이 이미 지정되어 있는 것.
 *
 *                  }
 *              }
 *          }
 * * 굉장히 편리하고 좋아보이는데, 그런데, deprecation 됐다. (왜? 새로운 방법을 제시하면서. 그리고 명료성이 떨어지는 문제가 있어서)
 *
 *
 *
 * - DataBinding                    //툴 베이스임. 라이브러리 dependency 관계 설정해줄 필요 x     //dataBinding은 findViewById 를 줄이자! 에서 한층 더 나아가, 일종의 아키텍처 요소가 된다. -> 뷰와 관련되어있는 코드를 뷰가 등록된 xml에서 다 하자. 라는 것 => findViewById, data setting, event 등록 등
 *          android {               //build.gradle 파일에 데이터 바인딩 지원을 해달라고 이렇게 쓰면 됨. 그럼 이것에 의해서 많은 코드가 자동으로 만들어 짐.
 *              dataBinding {
 *                  enabled = true
 *              }
 *          }
 *
 *
 *          <?xml version="1.0" encoding="utf-8" ?>
 *          <layout>                                    //이 layout 태그로 전체 xml을 감싸는게 중요 (core. 필수 규칙.)
 *              <LinearLayout xmlns:android="https://schemas.android.com/apk/res/android">
 *                  <TextView
 *                      android:id="@+id/nameResultView"/>
 *                  <Button
 *                      android:id="@+id/button"/>
 *              </LinearLayout>
 *          </layout>
 *
 *
 *          class MainActivity : AppCompatActivity() {
 *              override fun onCreate(savedInstanceState: Bundle?) {
 *                  super.onCreate(savedInstanceState)
 *                  val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)     //=> 데이터 바인딩 객체 준비 (ActivityMainBinding 이게 자동으로 generate 된 클래스. 기본적으로는 xml 파일 명으로 만들어짐. activity_main.xml => ActivityMainBinding : 이 클래스 내에 뷰와 관련된 코드가 자동으로 되어 있음.)
 *                  binding.nameResultView.text = "hello world"
 *                  binding.button.setOnClickListener{   }
 *              }
 *          }
 *
 *          //사실 만약 이렇게쓸려고한다면, ViewBinding 이 더 편하다고 한다.
 *
 *          //Activity 코드영역에서 업무로직이 실행되어 질것이고, 그럼 서버사이드와 네트워킹을 하든 뭘하든 여기서 데이터 발생.
 *          //이 데이터를 원래 View 에 찍어야 하는데, 이 데이터를 xml에 넘겨서 xml에서 View 에 찍게끔.   => 그럴려면 xml에 받을 data가 하나 선언이 되어 있어야 한다. (변수 선언)
 *
 *
 *          <data>
 *              <variable
 *                  name="user"                                         //변수명: user     (User 클래스 객체의 데이터)
 *                  type="com.example.test_databinding.User"/>          //데이터 타입 : User 클래스 타입
 *          <data>
 *
 *
 *          <TextView
 *              android:layout_width="match_parent"
 *              android:layout_height="wrap_content"
 *              android:text="@{user.name + '-' + user.address}"/>
 *
 *
 *          val user = User("gildong", "seoul")     //데이터 준비
 *          binding.user = user                     //binding 객체, 변수명 user 에다가 데이터 대입 => xml 로 넘어가서 xml에 찍힌다는 얘기
 *
 *          //결국 data -> view 이걸 xml에서 한다는 것. => 이럼 코드에서 findViewById 는 물론, 뷰 객체를 핸들링할 필요도 없다.
 *
 *
 */


/**
 * C90) ViewBinding
 *
 * * 데이터 바인딩과 더불어서 뷰 바인딩이 구글에서 뷰를 핸들링하기 위해서 제시하고 있는 기법
 * * 구조적으로 봤을 때, 데이터 바인딩이 큰 프레임웍이고, 데이터 바인딩의 핵심 내용 중에서 일부분을 추출해서 뷰바인딩으로 하는 개념 정도.
 */