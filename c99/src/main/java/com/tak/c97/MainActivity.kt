package com.tak.c97

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


/**
 * 파트 26 ) ViewModel 을 이용해 MVVM 모델을 적용해 보자.
 *
 * C99) ViewModel
 *
 * * AAC 의 구성 요소
 *
 * <ViewModel>
 *                (View)   (Model)
 * - MVVM 의 핵심은 화면 에서 B/L 을 분리해서 개발하는 것         (Model / View / ViewModel)               (B/L : 비즈니스 로직)        (MVVM 모델 적용할 필요가 없는 경우가 있다 하더라도 ViewModel 까지는 써 볼만 하다.)
 * - Activity, Fragment 에서 UI 를 처리하고 ViewModel 에서 B/L 을 처리                                      //실제로는 ViewModel 도 Model(Repository)를 요청(이용)을 해서 처리한 데이터를 다시 갖고오는 것. //View 입장에서는 마치 ViewModel 에서 비즈니스 업무 로직이 처리 되는 것처럼 느껴진다.
 * - Activity 에서 onSaveInstanceState() 함수를 이용해 작성하는 Bundle 데이터 저장도 ViewModel 로 대체가 가능   //액티비티를 개발하다 보면, onSaveInstanceState() 함수 등을 이용해서 이 Activity 의 상태 데이터를 Bundle 에 저장하는 작업이 들어가 줘야 한다. (근데 이 작업이 좀 귀찮다)
 *                                                                                                     //이걸 ViewModel 을 이용하면, 이 액티비티의 상태 데이터 유지를 Bundle 을 이용하지 않아도 쉽게 구현할 수 있다. ViewModel 을 이용하면, 개발 코드를 상당히 줄여줄 수 있다.
 *          implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1'                          //그러다 보니까 전체적으로 AAC 의 구성요소 중에서 ViewModel 이 가장 많이 이용되는 것 같다.
 *
 *          //라이브러리 dependency 관계 설정 등록
 *
 *
 * - ViewModel 상속으로 작성 (아래는 작성 방법)
 *
 *          class MyViewModel : ViewModel() {                       //ViewModel 을 상속받아 만든 개발자 클래스
 *              val user: MutableLiveData<User>
 *                  get() {
 *                      val user = MutableLiveData<User>()
 *                      user.postValue(User("gildong", "hong1"))
 *                      return user
 *                  }
 *          }
 *
 *
 *
 */