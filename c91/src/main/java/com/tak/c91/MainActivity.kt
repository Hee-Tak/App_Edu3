package com.tak.c91

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tak.c91.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val oneFragment = OneFragment()
        val twoFragment = TwoFragment()

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.add(R.id.fragment_content, oneFragment)
        transaction.commit()    //커밋하면 트랜잭션 객체가 닫힘. 그래서 다시 제어하려면 다시 얻어야함.

        binding.oneButton.setOnClickListener {
            val tran = manager.beginTransaction()
            tran.replace(R.id.fragment_content, oneFragment)
            tran.commit()
        }

        binding.twoButton.setOnClickListener {
            val tran = manager.beginTransaction()
            tran.replace(R.id.fragment_content, twoFragment)
            tran.commit()
        }
    }
}

/**
 * 파트 25) JetPack 의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * C91) Fragment
 *
 * * 주제 : 안드로이드 화면 구성
 * * 지금까지 화면 구성하기 위해 사용한 View 들은 표준 api에서 제공되고 있는 View.
 * * 실전으로 개발할 때는, 표준 api에서 제공되는 View 만 이용하는게 아니라, JetPack 쪽에서도 화면을 구성하기 위한 다양한 View 들을 제공하고 있다. (구글의 추가 라이브러리 라고 보면 됨.)
 * * 즉, JatPack 에서 이용되고 있는 View 들을 알아보자. (일단 대표적인 Fragment 부터)
 *
 * <JetPack>
 * - JetPack 이란 구글에서 안드로이드 개발을 위해 지원하는 다양한 라이브러리의 집합
 * - 대부분 androidx 로 시작하는 패키지명을 사용하는 라이브러리
 *
 * - 앱 개발의 권장 아키텍처 제공 (AAC)
 * - API Level 에 의한 호환성 문제 해결 (이걸 위한 라이브러리도 있다.)
 * - 표준에서 제공하지 못하는 다양한 기능 제공
 *
 * * 위와 같은게 묶여져 있는게. JetPack 이다.
 *
 * * 표준 API (Platform API) 이 외에 구글에서 제공되고 있는 라이브러리 들이, 과거에는 support 라이브러리라고 해서 제공이 됐다. 이걸 2017년 이후로 JetPack 으로 통합해 버렸다. 이게 모두 androidx 자가 붙은 라이브러리가 된다.
 *
 *
 * - androidx.appcompat     : 앱의 API Level 호환성 라이브러리    (앱의 가장 코어 부분을 제공하고, 거기에 더불어서 API 호환성 문제를 해결하기 위한 라이브러리)
 * - androidx.recyclerview  : 목록화면 구성 라이브러리
 * - androidx.viewpager2    : 스와이프에 의한 화면 구성 라이브러리
 * - androidx.fragment      : 액티비티 처럼 동작하는 뷰 제공 라이브러리
 * - androidx.drawerlayout  : 옆에서 밀리면서 보여지는 화면 구성 라이브러리
 * * 몇 개만 소개. 실제는 이거보다 훨씬 많음.
 *
 *
 *
 * <Fragment>
 * - 프레그먼트는 뷰.
 * - 자체적으로는 화면에 아무것도 출력되지 않는다. (자체적인 UI는 가지지 않는다. 그럼 레이아웃이냐? 그것도 아님)
 * - 액티비티처럼 동작하는 뷰 (***이게 핵심)
 * - 액티비티에 작성할 수 있는 모든 코드를 프레그먼트에 작성이 가능
 *
 * * 이 프레그먼트는, 핵심적인 개념이 뷰 이다. (뷰 라는 것이 핵심이라는 뜻인 듯). 결국 개발자가 액티비티에서 출력을 시켜줘야 화면에 나온다. 라는 뜻
 *
 *          Activity --------------------------- View   - Button
 *              ↓                                       - TextView
 *           UI 구성                                     - ImageView
 *           Menu 구성                                   - .....
 *  lifecycle 함수(onCreate 등)                           - Fragment      => 뷰는 뷰인데, 다른 뷰들이랑 능력이 좀 다름. ==> 액티비티 처럼 동작하는 뷰! => 액티비티에 작성할 수 있는 것을 프레그먼트로 다 분리시킬 수 있다는 얘기.
 *    ↓                                                        ↓
 *   이러한 것들을 프레그먼트에도 작성할 수 있다라는 것.              UI 구성 (물론 XML 파일 하나 프레그먼트를 위한 것을 만들고 inflate 시켜서 화면을 출력하면 됨)
 *                                                          menu 구성
 *                                                     LifeCycle 함수 (액티비티랑 동일한 라이프사이클을 가지고 있다.)
 *
 * * 그러면 액티비티에다가 작성하지 왜 프레그먼트에다가 함?
 * * 액티비티에서 하나의 화면을 구성한다고 생각해보자. 유저 화면이 너무 복잡하다고 생각. 근데 하나의 화면은 하나의 액티비티임. -> 화면도 복잡하고 업무로직도 상당히 복잡해질 수 있다.
 *      이 부분을, 하나의 액티비티니까 하나의 액티비티 내에다 다 작성해야 하는데, 그럼 코드가 너무 길어진다는 문제가 있다.
 *      그래서 화면에 구성요소의 일부분을 코드적으로 분리시키고 싶다!
 *      그러면 이게 액티비티 화면이니까 액티비티 출력이 되려면, 어쨌든 뷰는 있어야함. 근데 일반 View 로는 분리할 수가 없다. 왜? -> 액티비티에 원래 작성돼 있어야 되는 부분을 코드 분리하겠다. 라는건데 일반 다른 뷰들은 액티비티처럼 동작하지 않는다.
 *      근데 이게 프레그먼트면, 뷰는 뷰기는 함. Fragment A, Fragment B -> 액티비티에 작성돼 있던 것을 그대로 프레그먼트로 분리시키는 거지. 그런 다음에, 최종 액티비티가 이 View 2개를 뿌려버리면 된다 라는 거다.
 *      이렇게되면 액티비티 코드가 길어질 건 없고, 하나의 클래스에 작성되던게 두 개 클래스로 나누어진 거니까 작성하기도 편하다.
 *
 *      또는, 화면 2장짜리가 있다고 생각해보자. 그럼 Activity A, Activity B 이렇게 두 개를 만들어 쓸 수도 있겠지. 그러고 intent 에 의해서 화면 전환하게 만들어도 됨.
 *      그런데, 프레그먼트를 이용한다면 -> 그냥 액티비티 하나만 만들고, (어쨌던 액티비티는 있어야 하니까,) 그 다음에, 프레그먼트를 두 개를 만들어 버린다. -> 이 프레그먼트에서 각각의 UI를 구성해준다.
 *      그런 다음에, 액티비티 쪽에서 이쪽 프레그먼트 A 를 띄웠다가, 저쪽 프레그먼트 B를 띄우는 식으로 할 수 있다.
 *      유저 입장에서 완벽히 화면 2장이 분리 되어 있는 듯 하게 구현할 수 있다라는 것.
 *
 *
 *
 * - 프레그먼트는 Fragment 를 상속받아 작성하는 클래스
 *
 *          class OneFragment: Fragment() {     //Fragment 를 상속받은 개발자 클래스 => 프레그먼트 클래스라고 부름.
 *
 *
 *          } // 이렇게 하나의 프레그먼트가 만들어졌는데, 결국 이걸 액티비티에서 출력해줘야함.
 *
 * - 액티비티의 레이아웃 XML 에 등록하여 프레그먼트 출력 (static 한 방법)
 *          <fragment
 *              android:name="com.example.OneFragment"          //name 속성으로 프레그먼트 클래스를 등록.
 *              android:id="@+id/fragmentView"
 *              android:layout_width="match_parent"
 *              android:layout_height="match_parent />
 *
 *
 * - 액티비티 코드에서 프레그먼트 출력     (동적인 방법 - Fragment A 를 띄웠다가 Fragment B 를 띄웠다가, 왔다갔다 동적으로 제어) => FragmentManager 이용 => FragmentTransaction 을 얻어냄. => 그리고 아래와 같이 제어.
 * - add(int containerViewId, Fragment fragment)        : 새로운 Fragment 를 화면에 추가. id 영역에 추가.
 * - replace(int containerViewId, Fragment fragment)    : id 영역에 추가된 Fragment 를 대체
 * - remove(Fragment fragment)                          : 추가된 Fragment 제거
 * - commit()                                           : 화면 적용
 *
 *          val fragmentManager: FragmentManager = supportFragmentManager
 *          val transaction: FragmentTransaction = fragmentManager.beginTransaction()
 *
 *          transaction.add(R.id.fragment_content, oneFragment)     //oneFragment 는 아까 만든 프레그먼트 클래스의 객체
 *          transaction.commit()
 *
 *
 */