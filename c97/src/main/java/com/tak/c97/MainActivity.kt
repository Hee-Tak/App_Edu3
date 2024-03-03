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
 * 파트 26) ViewModel 을 이용해 MVVM 모델을 적용해 보자.
 *
 *
 * C97) Concepts of AAC
 *
 * * 이번 파트는, JetPack 의 구성 요소인 AAC 에서, 가장 많이 사용된다고 하는 ViewModel 을 살펴보기 위한 파트이다.
 *      ViewModel 이 가장 기초적인 구성요소 이기도 하고 ....
 *
 *
 * <Android Architecture Components>    -> architecture, 목적 자체는 안드로이드 앱을 구성하기 위한 아키텍처를 제공해 주기 위한 것이 목적.
 * - 2017 Google I/O 에서 발표
 * - 2018 Google I/O 에서 발표된 JetPack 으로 통합       -> JetPack : 안드로이드 개발자를 위해서 구글에서 제공해주는 기법들. 기존에 있던 것들 거의 대부분 묶어서 JetPack으로 통합. 크게 (AAC, androix,... 이런걸로 묶임) 이렇게 구성됨. (이것들이 구성요소)
 *
 * - Architecture Components 가 공개되기 전까지는 안드로이드 앱에서 특정 Architecture 가 권장되지 않았다.
 * - 개발자들에 따라 MVP, MVC, MVVM, MVPP 등 다양한 아키텍처가 선택           // MVP, MVC, MVVM, MVPP -> 원래 Front-End 쪽 어플리케이션에서 유명하게 사용됐던 거
 *
 * - 안드로이드 앱을 위한 아키텍처를 정의하고 이를 구현하기 위한 라이브러리를 제공하고자 architecture components 을 공개            (권장 아키텍처를 제시하고 거기에 관련된 라이브러리를 제공 -> AAC)
 *
 * * 액티비티, 서비스, 리시버 이런걸 얘기하는게 아님.
 *
 *
 *
 *
 *
 *
 * <Data Binding> - 아키텍처 요소. 단순하게 ViewBinding 처럼 쓰려면 쓸 수 있음. 이렇게만 써도 findViewById 보단 편함. 근데, 데이터바인딩이 먼저나온애임. 얘에서 findViewById() 줄이는 목적인게 ViewBinding 이고,
 * - Data 를 UI 에 쉽게 Binding 하기 위한 라이브러리                                                             //데이터 바인딩은 findViewById 코드를 줄여주는 것 이상의 아키텍처 요소가 적용. 그래서 우리가 UI 를 구성할 때, XML에 UI를 구성해서 여기에 View가 등록되는데,
     * - findViewById() 에 의한 View 객체 획득 및 이용의 번거로움을 제거하기 위한 목적                                  // 그럼 View 와 관련되어 있는 코드는 xml 에 명시하는 것이 맞지 않냐? 구조상으로..... 이런 개념임. -> view 에 data를 찍거나 event 를 거는거 등등은, XML 에 명시하자! 즉, 아키텍처가 주 목적
     * - DI 로 유명한 Butterknife 등의 라이브러리 이용보다 효율적.
 * - Android Architecture Components 의 구성요소
 *
 *          <layout>                    //data는 당연히 액티비티쪽 코드에서 발생할거니까 이걸 xml에 찍어줘야(바인딩)한다는거
 *              <data>                  // 이 data 태그가, 해당 데이터를 받을 준비를 하는거
 *                  <variable name="model1" type="com.example.test_databinding.User" />     //변수 선언. 타입 명시      //이 상태로 데이터 넘겨받음
 *              </data>
 *              <TextView xmlns:android="http://schemas.android.com/apk/res/android"
 *                  android:layout_width="match_parent"
 *                  android:layout_height="wrap_content"
 *                  android:text='@{"i am include xml.. binding data : "+model1.name}' />   // 위에서 데이터를 넘겨받으면, 여기에다가 어느 뷰에 데이터를 찍을지 명시          => 데이터 바인딩 기법
 *          </layout>
 *
 *
 *
 *
 *
 *
 * <Lifecycle Aware Components>
 * - Activity / Fragment 의 Lifecycle 처리를 따로 구성
 * - Activity 는 Lifecycle Owner 가 되며, 이를 처리하는 곳이 Lifecycle Observer             //그러니까 Activity/Fragment 는, 원래 Lifecycle 함수가 돌아가는 곳... 그래서 Lifecycle Owner          그리고 이 Lifecycle 함수들을 따로 분리시켜서 작성한 걸 Observer 라고 한다.
 *
 * * 실제 액티비티(프레그먼트)를 구현하고 그리고 여기에 lifecycle 함수들이 있는데 (onCreate, onStart, onResume, onPause, onStop, onDestroy 등... 보통 여기서 상황에 맞게 취사선택해서 3개, 4개 이런 정도로 만듦)
 *      그런데, 서버랑 네트워킹하고, 네트워킹에 의한 결과 데이터를 분석하고, 분석된 결과를 화면에 찍고, 유저 이벤트 처리하고,... 이것만으로도 상당히 길다. 그리고 onStart에서 위치추적을 시작하고, onDestroy 에서 종료해라... 뭐 이런식으로 제어가 들어갈 수 있겠지만,
 *      이러한 제어가 당연히 액티비티(프레그먼트)의 라이프사이클 함수에 들어가는 것이 맞긴 맞지만, 부담스럽다 라는 것. 액티비티나 프레그먼트가 라이프사이클 구성요소만 구현되는 것이 아니다보니까, 여기에다가 이런 것들을 구현하고자 하면 너무 부담스럽다.....사실, 그리고 이 라이프사이클과 관련되어 있는 것들을 제어하는 것도 꽤 길 수 있다.
 *
 *      그래서 Lifecycle Aware Components 라는 것은 이러한 것(라이프사이클 함수)들을 분리시키겠다. 라는 것. 이 분리하는 것을 도와주겠다.
 *      결국 Observer 에 구현되어 있는데, 이쪽에 구현되어 있는 코드가 실행되려면, Owner 의 라이프 사이클 상황을 감지해야 되지 않냐?? 라는 것.... 이걸 감지해서, 실행시켜주는 것 까지 자동으로 해주겠다 => Lifecycle Aware Components
 *
 *
 *
 *
 * <Navigation Components>
 * - Navigation Component 는 화면간 이동을 간편하게 작성하게 하는 JetPack 의 Library 혹은 Tool
 * - 하나의 main activity 에 여러 개의 fragment 로 화면을 설계하는 것을 목적으로 함.               => 좋은데??
 *
 * * 이게 액티비티가 결국 화면이니까 먼저인거긴 하지만, 액티비티 위주로 화면 구성을 막 널부러뜨려놓으면 이게 도대체 화면이 어떻게 설계되어 있고, 어떻게 연결되어있는가? 안보인다라는것.
 *      이걸 툴 베이스적으로 도식화시켜서 작성하고 싶다. ==> Navigation Components 의 메인.
 *      Fragment 들로 화면을 구성하는데, 이걸 클래스다이어그램마냥 화면간 이어서 연결하고 뭐 그런식으로 그래프처럼 한눈에 화면 전환이 어떻게되고, 화면이 몇개있고, 이런걸 볼 수 있다는 거.
 *
 *
 *
 * <Room>
 * - SQLite 추상화 라이브러리
 * - 안드로이드 앱의 데이터베이스 이용을 Room 으로 권고.        // 그렇다는건. 중요하다는 것.
 *
 * * Room : 안드로이드의 DBMS 프로그램.
 * * SQlite : 안드로이드의 데이터베이스         <-- 얘를 이용하려면, SQLiteDatabase 클래스의 execSql(sql) 함수 or rawQuery(sql) 함수로 이용. (여기서 sql 은 문자열. 당연하겠지만 SQL문으로 구성).... 이렇게하면 테이블이 구성되고 , Cursor 움직여서 데이터 뽑고,.... 이게 기본.
 *                                              근데, 이렇게 하기 싫잖아? 귀찮잖아? 뭐 많잖아? SQLite 를 이용하긴 이용할건데, 개발자가 직접 SQL 문을 주기 귀찮다는거. => 이런것들을 포함한 많은 것들이 자동화가 되었으면 좋겠다 :: Room
 *                                              현재로써는, SQLite 이용하는 것을 Room 으로 이용할 것을 권장하고 있는 측면이 있다.
 *                                              Retrofit 개념과 비슷하다? 이건 네트웍쪽이긴한데, 사실 여기에도 원래 네트워킹을 하기 위한 함수가 있어야 된다. Retrofit 에서 우리는 이걸 인터페이스로 만듦... 그래서 인터페이스에 getList,... 이런식으로 만듦.. 이런식으로만 만들고 실제 네트워킹이 필요할 때 이 함수를 콜(호출)하겠다!... 근데 사실 인터페이스라 내용을 구현할 수가 없다.
 *                                              우리는, 인터페이스만 이렇게 만들면, 지네가 인터페이스 구현 해서, 실제 우리가 만들어 놓은 getList 에 실제 네트워킹이 가능하게끔 하는 코드를 담아주니까! 우리는 그냥 인터페이스로 이것을 이용만 하면은 그냥 네트워킹이 된다. 이런 개념인건데, Room 도 이거랑 결국 비슷!
 *   Room Database
 *        ▲
 *        |
 *        |                   Data Access Objects
 *        |                        ▲
 *        |                        |
 *        |                        |   Get Entities from DB                               Entities
 *        | Get DAO                |                                                        ▲
 *        |                        |   Parsist changes back to db                           |   get /set field values
 *        ▽                        ▽                                                        ▽
 *    [------------------------------------------Rest of The App--------------------------------------------]
 *
 *
 *
 * * Room을 이용한다고하면 그냥 우리가 추상으로 getList() 이런 함수만 만들어 놓음. (물론 어노테이션 등으로 정보는 줘야함)  @Insert   뭐 이렇게. 그럼 자기네들이 이거(추상함수)를 보고 이 정보대로, DBMS가 되게끔 하기 위한 어떠한 함수를 만들어서 제공하겠다. 그런 개념
 *                                                                                                         getList()
 * * 그래서 실제 내부적으로는 그냥 SQLite 이용하는거임
 * * 아무튼 Retrofit 과 비슷한 사상이고, DBMS 쪽을 위한 컴포넌트다.
 *
 *
 *
 *
 *
 *
 * <Paging> 페이징
 * - RecyclerView 에서 페이징 처리를 쉽고 효율적으로 작성하게 하기 위한 라이브러리              //결국 RecyclerView 이고, 우리가 봤던 대로 구현을 하면 됨. 근데 좀 복잡하게 드렁가면 어려울 수 있음.
 * - 페이징 된 데이터의 메모리 내 캐싱                                            //한번 긁어온 것은 캐싱
 * - 요청 중복 제거 기능이 기본으로 제공
 * - 데이터의 끝까지 스크롤할 때 어댑터가 자동으로 데이터를 요청
 *                                                                                              *** 여기 그림에서 Flow<PagingData>는 ViewModel 범주와 UI 범주 사이에 껴있음. (두개에 포함되는 개념인가)
 *          Repository                          Viewmodel                                                     UI
 *          * PagingSource          =>          * Pager ======== Flow<PagingData> ==============>>>>     * PagingDataAdapter
 *          * RemoteMediator        =>
 *
 *
 * * 만약에 일반 RecyclerView 처럼 항목이 쭈욱 나오고 스크롤이 되고, 그래서 뭐 천건, 만건 나오고... 그러면은 서버사이드 데이터였다면 서버사이드에서 만건을 가지고 올거냐? 이게 사실 말이 안된다.
 *    처음에 50건을 가져왔다. 근데 유저가 스크롤내려서 다봐서 그 다음 50건을 가져온다.... 이렇게 하려면 우리가 이 페이지의 스크롤 이벤트를 잡아서, 유저가 보이는 항목이 더 필요한 순간을 잡아내서, 빠르게 그다음 50건을 받아서 붙이거나 이렇게 해주면 되는데, 이게 작업량이 상당히 많다.
 *    Paging은 이걸 자동화 시켜주겠다는 것.
 *
 * * 그래서 Paging 은 RecyclerView 의 페이징 처리를 목적으로 한다고 보면 됨.
 * * 이게 Paging API
 *
 *
 *
 *
 *
 * <WorkManager>
 * - WorkManager 는 특정 작업을 유예(Defer) 처리, 비동기 처리를 제공
 * - JobScheduler, FirebaseJobDispatcher, AlarmManager 등을 통합한 라이브러리
 *
 * * JobScheduler : 백그라운드 제약이 있다보니, 백그라운드에서 시스템에 Job을 등록시켜 실행시킨다는 것....
 * * 이러한 것들을 통합한 컴포넌트 : WorkManager
 * * 그래서 얘를 쓰면, 알아서 JobScheduler 를 쓰거나, 알아서 FirebaseJobDispatcher를 쓰거나, 알아서 AlarmManager 를 쓰거나,... 알아서 처리한다.
 *
 * * Worker : 우리의 처리될 업무를 갖고 있는 것.
 * * Worker 가 여러 개 있을 때, 순서 지정하는 것이 되게 편하다. (동시에 실행되거나, 순서에 따라 실행되거나, 그다음 조건에 따라 실행되거나 등등)
 *
 *
 *
 *
 *
 * 여기까진 전부다 소개파트고, 실제로는 좀 더 알아보고 써야함.
 *
 *
 *
 *
 *
 *
 */
//=========================================================================================================
//=========================================================================================================
//=========================================================================================================
/**
 * C98) MVVM 모델
 *
 * * 이전시간에 소개된 그런 아키텍처들을 가지고, 어떻게 구성해서 개발을 하자는 거냐? 라고 하는 구조, 모델을 살펴볼 것.
 *
 *
 *
 * <Android MVVM>
 *                                                                                          (근데 뭐 당연히 소프트웨어 모델이 다 이것을 목적으로 함.) (어쨌던 관심의 분리 차원에서 제시하고 있는 것)
 * - 권장사항은 어플리케이션은 Separation of concerns 에 의해 작성       (핵심은, 관심의 분리.) (내가 구현하고자하는 관심. 화면있냐? 비즈니스로직 있냐? 이러한 관심들을 하나하나의 구성요소로 분리시켜서 개발하는 것이 맞다.)
 * - 그로 인한 UI 와 모델이 분리              (화면을 구성하는 UI : View, 비즈니스 업무로직을 제공해주는 Model) (이걸 분리)
 *
 *
 *                                      * Lifecycle         -> LifeCycle 과 관련된 관심대상 (Lifecycle 을 제어하기 위한.) 이럴 때 우리가 처리해야될... 그런게 또 하나의 관심이고. (즉, 이런걸 분리시킬 수 있을 거고, 분리시켜서 개발하자.)
 *                                      * ViewModel         ->  이러한 것들을 직접 연결하지 않게 ViewModel 을 쓰자.... 이러한 그림
 *                                      * LifeData
 *                                      * Room              -> 데이터 영속화
 *
 *
 * * 이 MVVM 모델이 AAC 의 핵심 아키텍처이다. - 구글에서 제시한거라기 보다는, 원래 일반적으로 개발시에 많이 사용했던 소프트웨어 모델.
 *      => 주로 Front-End Application => 즉, 주된 목적이 화면. => 화면은? View.
 *      아무튼, 이걸 위해 제시된 모델이고, 소프트웨어 개발 시에 통용되던 소프트웨어 모델인데,
 *      AAC도 이 MVVM 을 목적으로 한다.
 *      MVVM : Model / View / ViewModel
 *      Model : 실제 비즈니스 업무 로직이 구현되는 부분, 혹은 비즈니스 데이터가 표현되는 부분.
 *      View : 화면. Presentation Logic (P/L)
 *      ViewModel :
 *      -> 이 ViewModel 사상은 결과적으로 우리가 보면은 화면이 있고, 그럼 View 가 있고, 그 다음에 Model 이 있고,....
 *          그러면 프론트엔드 같은 경우에는 이 화면(View)에 Model 데이터가 찍혀야 되니까, 이 View 쪽에서 Model 에 접근을 해서, Model 데이터를 가져오거나,
 *          아니면 여기서 (View) 이벤트가 발생했을 때 Model 쪽에 업무를 진행시키고, 업무에 의한 결과값을 다시 가져오거나,... 이런 식으로 우리가 구현을 해줘야 한다. => 그래서 View 구성 요소를 하나 만들고, Model 쪽에 구성요소를 하나 만들고.... 이렇게 만듦.
 *
 *          근데, MVVM 은 이 View 와 Model 을 직접 연결하면 아키텍처상 안좋다고 얘기를 한다.
 *          그래서 이 둘 간의 관계를 분리시키는 것이 좀 더 유연한 구조다라고 표현을 해서, 이 둘 간의 관계를 분리시키기 위해서 중간에 ViewModel 이라고 하는 역할자를 두자는 것.
 *          그래서 View 와 Model 을 직접 연결시키지 않고,
 *       ***View 입장에서는 ViewModel 을 그냥 실행시키는 것이고, ViewModel 이 실제 업무가 진행되는 곳은 아니지만 업무가 진행되는 Model 을 실행시키고,.... 이 Model 에 의한 결과를 ViewModel 에서 받아서 다시 ViewModel 에서 View 를 이용하게 하는........
 *          이런 구조다.
 *          이렇게 됐을 때, 나중에 Model 이 교체가 되면은, View 에 영향을 못 미친다는 것.
 *          만약에 View 와 Model 이 직접 연결되어 있으면, View 가 Model 이 바뀌면 View 가 영향을 받는다... 그런데 중간에 ViewModel 을 매개로 하면은, View 와 Model 이 직접 연결되어 있지 않기 때문에 Model 이 교체되거나 바뀌었다고 하더라도 View 를 같이 유지보수할 필요는 없는. 이런 것을 목적으로 한다.
 *
 *          * 즉, View 와 Model 의 중간에 매개역할자를 하는게 ViewModel
 *          => MVVM 모델 (Model, View, ViewModel)
 *
 *
 * * 제시하고 있는 전체적인 구조
 *
 *                                  Activity / Fragment     (Share its Lifecycle with ViewModel. Observe LiveData from ViewModel.)
 *                             ↙           ↓               ↖
 *                  Lifecycle   --->    ViewModel      -→     LiveData  (- Observable)          (ViewModel : Monitors the View's Lifecycle. providing it with a LiveData taken from the Repository.)
 *                                          ↕
 *                                      Repository (Take and handle persistent data from Room. make calls to Webservices and so on.)
 *                                     ↕        ↕
 *                                  Room        WebService
 *                              (SQLite Data)
 *
 *
 *
 * * 화면을 구성하기 위해서는, 화면을 구성하기 위한 Activity 나 Fragment 가 나와야 함. (이 Activity 나 Fragment 가 우리 입장에서 봤을 때는 View.)
 * * 그 다음, 실제 데이터가 어디엔가 저장이 돼야 되고, 어딘가 저장되는 데이터를 획득을 해줘야 함. 그 역할자를 흔히, 소프트웨어 개발 시에  Repository 라고 부름.
 *      이 Repository 역할이 DB 일 수도 있고 (그럼 DBMS 프로그램이 들어갈거고....Room), 아니면 서버일 수도 있고 (또 많은 경우에 업무가 서버에서 진행이 되고, 서버에서 데이터가 저장이 되고, 서버 데이터를 긁어 와야 되니까) (서버랑 연동하는 프로그램 작성...Retrofit).. 이런 식의 코드가 이 Repository 쪽 코드.
 *      이런식으로 Repository 는 Room (SQLite 추상화) 이나 Retrofit (네트워킹) 을 이용해서 실제 비즈니스 업무의 비즈니스 데이터를 표현하는 곳이다. 라는 것.
 *
 * * 그러면 원래 View 에서 이벤트가 발생했을 때, 이쪽 Repository 의 업무가 진행이 돼야 되는데
 *      그것을 ViewModel 로 분리시키자. 직접 연결시키지말고.
 *      View 입장에서는 ViewModel 에 요청을 하는 것.
 *      ViewModel 이 적절하게 맞는 Repository 를 실행시켜서 업무가 진행되게끔 하고, 그 결과값이 실제 View 에 넘어가게 처리하자........ 이런 구조
 *
 * * 그런데, 비즈니스 데이터가 View 에 넘어가야 되는데, 직접 넘길수도 있기야 하겠지만, 이 넘기는 것을...... 실제 View 입장에서는 퍼포먼스적으로 유저 이벤트를 받아들이는 것이 가장 중요한 퍼포먼스임.
 *      그런데, 업무 로직을 실행하다 보니까, 서버 연동이나 dbms 시에 시간이 걸릴 수 있다는 것.... 이걸 직접 받기 위해서 대기해야 되냐?? 이럼 문제가 됨. 그렇게 되면은 유저 반응성(유저 화면 반응성)이 늦을 수 밖에 없고, 그럼 퍼포먼스적으로 손해볼 수 밖에 없는 것.
 *      그래서, 직접 받을 수도 있지만, 그걸 직접 받지 않고, 실제 ViewModel 에 요청해서 업무 로직이 실행된 결과값을 LiveData로.
 *      그래서 결과 데이터를 LiveData 로 이용을 해서 받아내자. (비동기구조 라서 대기하지 않고, 받을 수 있다.)
 *
 * * 여기에, Lifecycle 까지 분리시켜서 개발을 하자.
 *
 *
 *
 *
 *

 *
 *
 *
 *
 * <Android MVVM>
 *
 * - Activity / Fragment : View         (아래는 View 의 역할)
 *
 * - UI 를 구성하고 유저와 상호 작용. LifeCycle 변경을 감지.         //UI를 구성하고 유저와 상호작용 하는게 주된 목적임. 그리고 여기서 Lifecycle이 이쪽에서 발생이 되는 거니까 (LifeCycle Owner) 라이프사이클 변경감지 역할까지.
 * - LifeCycle 변경이 발생하면 ViewModel 에 전달.                 //라이프사이클 변경이 발생하고 뭔가를 처리해야된다. 그럼 ViewModel 에 그것을 전달.
 * - ViewModel 에서 전달한 LiveData 의 내용을 화면에 출력.         //ViewModel 에서 적절한 업무 로직을 실행시키고 (실행은 Repository 쪽),  결과 데이터는 LiveData 로 View 에게 전달한다.
 *
 *
 *
 *
 * - ViewModel : View 와 Model 을 분리      (분리하고 중간에 위치하는 매개체)             //View 입장에서는 ViewModel 이 Model 처럼 느껴져서 직접 업무 요청을 하겠지만, 실제 업무는 ViewModel 이 아니니까... 이 ViewModel 에서는 다시 Model 을 이용해 주는 그런 구조.
 *
 * - View 와 Model 을 분리시키기 위한 가교 역할.
 * - View 의 LifeCycle 변경을 감지하고 변경이 발생하면 Repository 를 실행.
 * - Repository 에서 발생한 LiveData 를 View 에 전달.
 *
 *
 *
 *
 *
 * - Repository : 데이터 처리                (Repository : Model) (비즈니스 업무로직이 처리되는 곳이다.)
 *
 * - 데이터의 저장 및 획득, 조작이 주 목적             (네트워킹 : Retrofit) (DB : Room)
 * - Room : 데이터 영속화                 //이러한 작업을 DBMS 프로그램을 통해서 한다고 하면 Room 이용하면됨.
 * - SQLite 매핑 라이브러리.               // Room : SQLite 매핑 라이브러리
 *
 *
 *
 *
 *
 */