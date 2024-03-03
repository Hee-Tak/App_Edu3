package com.tak.c97

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [C98Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C98Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c98, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment C98Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            C98Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




//=========================================================================================================
//=========================================================================================================
//=========================================================================================================

/**
 *
 * 파트 26) ViewModel 을 이용해 MVVM 모델을 적용해 보자.
 *
 *
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