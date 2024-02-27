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
 * - DataBinding                    //툴 베이스임. 라이브러리 dependency 관계 설정해줄 필요 x
 *          android {               //build.gradle 파일에 데이터 바인딩 지원을 해달라고 이렇게 쓰면 됨. 그럼 이것에 의해서 많은 코드가 자동으로 만들어 짐.
 *              dataBinding {
 *                  enabled = true
 *              }
 *          }
 *
 *          <?xml version="1.0" encoding="utf-8" ?>
 *          <layout>
 *              <LinearLayout xmlns:android="https://schemas.android.com/apk/res/android">
 *                  <TextView
 *                      android:id="@+id/nameResultView"/>
 *                  <Button
 *                      android:id="@+id/button"/>
 *              </LinearLayout>
 *          </layout>
 *
 *
 *
 *
 *
 *
 *
 *
 */