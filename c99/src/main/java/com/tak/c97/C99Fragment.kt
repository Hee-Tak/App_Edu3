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
 * Use the [C99Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C99Fragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_c99, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment C99Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            C99Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
 *          class MyViewModel : ViewModel() {                       //ViewModel 을 상속받아 만든 개발자 클래스 (ViewModel 을 상속받아서 작성하는게 핵심.)
 *              val user: MutableLiveData<User>                     //개발자 임의의 함수. 이런 것들을 등록하면 된다. <- 이것을 이용하는 쪽은 당연히 View 쪽. (그렇다면 Activity 나 Fragment 에서 이용을 할 것.)
 *                  get() {
 *                      val user = MutableLiveData<User>()          //단순하게 처리했지만
 *                      user.postValue(User("gildong", "hong1"))    // 이 두줄.. 이쪽에서 Repository 를 이용하게 될 거고, Repository 쪽에서 데이터가 발생이 되어서 넘어올 것.
 *                      return user
 *                  }                                               //이렇게 발생한 데이터를, View 에게 전달해주는 역할을 한다.
 *          }
 *
 *
 * * 결국 View 에서 원래 Repository 를 직접 이용해야 되는데,
 *      중간에 가교 역할을 해서... View 에서는 ViewModel 만 이용하고, ViewModel 이 Repository 를 대신 실행시켜 주고, 결과값을 넘겨주는 이런 구조.
 *
 *
 *
 *
 * - Activity 에서 ViewModel 이용.
 * - ViewModelProvider 를 이용한 객체 생성
 * - 직접 객체생성도 가능                    //우리가 만든 클래스니까 직접 생성이 불가능하냐? ㄴㄴ 가능함. 근데, 별로 안좋음. 왜? => ViewModel 을 이용할 때 얻을 수 있는 이점 중에 하나가, Activity 의 상태 데이터를 Bundle을 이용하지 않고서도, 쉽게 우리가 저장해 낼 수 있다는 측면인데, 객체를 직접 생성해서 이용하면 이 기능이 사라짐.
 *                                       //ViewModelProvider 를 이용을 해서 객체를 생성해야만 이 ViewModel 의 라이프사이클이 Activity 와 다르게 움직인다.
 *                                       // 만약에 직접 생성하면 Activity 와 Lifecycle 이 동일하게 움직이다 보니까... 그러다 보면 액티비티가 종료될 때  같이 종료되어버려서 액티비티 상태 데이터 유지가 안됨. ViewModelProvider 를 이용하면 라이프사이클이 상이하게 가서, 액티비티가 종료됐다고 하더라도 액티비티에서 이용하고 있는 ViewModel 이 종료되지 않는다. -> 액티비티의 데이터가 계속 유지될 수 있다.
 * - ViewModelProvider 을 이용한 객체생성해야 Activity 상태 변화에 따른, ViewModel 이 재생성 되는 것을 막을 수 있음.
 *
 *          val model = ViewModelProvider(this).get(MyViewModel::class.java)        //=> 우리가 만든 ViewModel 클래스 (MyViewModel 클래스)에 정보를 주고, 얘에 의해서 생성하는 방법이 있다.
 *          model.user.observe(this, {                                              // 그런 다음에, ViewModel 쪽에 있는 함수들을 이런 식으로 콜해서 이용하는 것.
 *              binding.textView.text = "${it.firstName} - ${it.lastName}"
 *          })
 *
 *
 *
 * * Activity 에서 이제 ViewModel 이 하나 만들어 졌으면, ViewModel 을 이용을 해야 하는데,
 *      이 ViewModel 이 개발자가 만든 클래스이다. (아까 만든 MyViewModel), 그럼 이제 이 객체를 생성해줘야 하는데, 생성하는 방법이 ViewModelProvider 를 이용해서 생성하는 방법이 있다.
 *
 *
 *
 *
 * * 결국, ViewModelProvider 를 가지고 객체 생성을 하는데, 이것을 좀 더 단순화 시킨 delegate 가 제공이 된다.
 *
 * - Kotlin property delegate 이용                        //거의 대부분이 delegator 를 이용한다.
 *
 *          val model: MyViewModel by viewModels()       // 이렇게 by 가 들어가는거는 다 delegator 를 얘기하는 것.
 *
 *          implementation "androidx.activity:activity-ktx:$activity_version"       //delegator 이용을 위한 라이브러리 dependency 관계 설정
 *
 *
 *                                                                           <Activity Lifecycle>       <ViewModel Lifecycle>
 * - ViewModel Lifecycle                     Activity created       (액티비티활성상태)      onCreate   |
 * - Activity Scope 내에서 Singleton                                        ""            onStart    |                                                         (onCreate 쪽)
 *                                                                         ""            onResume   |                           --> 비즈니스 업무 로직 데이터에 의해 여기쪽에서 data 가 발생됐고 data 를 Activity(onResume) 쪽으로 넘겨줘서 찍었다.
 *                                           Activity rotated --------------------------------------                            // 그런 상태에서 화면 회전
 *                                                                                       onPause    |
 *                                                                                       onStop     |
 *                                                                                       onDestroy  |    ViewModel              //destroy 까지 가버림. 이 얘기는, Activity 의 데이터는 날아간다는 얘기. 근데 액티비티에서 이용하는 데이터가, ViewModel 에 있다 보니까 .. 그리고 ViewModel 은 종료가 안되다보니까 유지가 된다!
 *                                                                                       onCreate   |       Scope               //그랬다가 다시 Create 되고
 *                                                                                       onStart    |
 *                                                                                       onResume   |                           //다시 Resume 됐을 때, 어쨌던 ViewModel 여기에 data 가 있으니까, 이 데이터를 그냥 이용만 하면 되는 측면.
 *                                                          finish()--------------------------------
 *                                                                                       onPause    |
 *                                                                                       onStop     |
 *                                                                                       onDestroy  |
 *                                                          Finished                                    onCleared()             //물론 이 액티비티가 최종 종료되면, ViewModel 도 종료된다.... (그런데, 위와 같이 상태변화에 의한 ... 그런 종료 때에는 ViewModel 은 종료가 안된다는 얘기)
 *
 *
 *
 * * ViewModel Lifecycle 을 보자. 원래는 액티비티나 프래그먼트가 사용이 되는건데, 그러니까 Activity(Fragment) ---여기에서---> ViewModel 객체를 생성해서 이용하는 것..
 *      그러면 액티비티에서 이용하는 거니까 액티비티가 상태변화(화면 회전등)가 생기면 종료됨 (그리고 곧바로 생성되긴한다.)..... 그러면 ViewModel 도 같이 종료가 돼야하는데, 종료가 안된다.
 *      그러다보니까 Activity 에서 찍어야하는 data 가 ViewModel 에 있다고 하면, 액티비티 내에 있으면은 데이터가 사라지지만, ViewModel 에서 얻어서 사용하는 것이니까, 이 ViewModel 이 종료가 안되면 안사라진다.
 *      그래서 상태변화에 의한 상태 데이터를 유지할 수 있는 기법으로 우리가 사용이 되는 것. (ViewModel 을 쓰는 이유 중 하나 라는 듯)
 *
 * * 하나의 액티비티에서 여러번 생성 한다 하더라도, ViewModel 은 Singleton 으로 유지가 된다.
 *
 *
 *
 *
 * * 일반적으로는 ViewModel 을 상속받아서 만드는 게 일반적이고.
 *
 *
 * - AndroidViewModel 은 ViewModel 의 서브 클래스
 *
 * - ViewModel()                                //일반적으로는 그냥 ViewModel 상속받아서 만드는게 일반적. ViewModel 의 생성자에는 매개변수가 없다.
 * - AndroidViewModel(Application application)  // 가끔 ViewModel 을 만들때 AndroidViewModel 을 상속받아서 만드는 경우가 있다. 그 경우는 뭐때매 그러냐? 컨텍스트 객체, 어플리케이션 객체를 이용하고자 할 때. ViewModel 내에서 이 어플리케이션 객체를 이용하고자 할 때
 *                                              // 그럴 때는 AndroidViewModel 을 상속받아서 만들어 주면 된다.
 *
 *
 * * 액티비티를 우리가 구현할 때, 액티비티에서 뭔가 화면을 구현하는 것이 아니라, 이 액티비티에서 프래그먼트 F1, 혹은 프래그먼트 F2,... 이런식으로 우리가 뿌리는 구조라면 ==> Fragment 도 일종의 View 기 때문에, 이 F1, F2 에서도 ViewModel 을 이용할 수 있다.
 *     얘네 화면 (F1, F2) 를 위한 업무로직이 있을 거고, 그러면 이쪽을 위한 ViewModel 이 나와야 할거고... 이런식으로 구현을 할 수 있다. (일단 여기서는 f1, f2 각각의 프레그먼트에 ViewModel 객체를 생성해 준다... 뭐 그런의미인듯)
 *      그럼 이 Fragment1(f1), Fragment2(f2) 에서 이용하는 각각의 ViewModel 들... 이 ViewModel 들의 라이프사이클이 해당 프레그먼트와 같이 가는거냐? ==> 이거는 또 라이프사이클 Owner 를 누구로 지정했냐에 따라 다르다.......
 *      그래서 프레그먼트 하나당 ViewModel 하나라고 하면 프레그먼트와 라이프사이클이 같이 가게 만들어 줄 수도 있지만,
 *      하나의 액티비티라고 하니까, 이 ViewModel 두 개가 하나의 액티비티 내에서 공유 되게끔, 이렇게 만들고 싶은 경우도 있을거다. 라는 얘기가 되는 것. ==> 라이프사이클 Owner 를 누구를 주느냐에 따라서 상이하게 이용할 수 있다.
 *
 *
 * - Fragment 에서 ViewModel 이용.
 * - Activity 내에 Fragment 가 여러 개 있는 경우 Fragment 간 ViewModel 공유 필요
 *
 *
 * ------------------Activity----------------------------               ------------------Activity----------------------------
 * |                                                    |               |                                                    |
 * |    ----OneFragment-----      ---TwoFragment----    |               |                  -----------                       |
 * |   |                   |     |                 |    |               |                 | ViewModel |                      |
 * |   |                   |     |                 |    |               |                  -----------                       |
 * |   |    ------------   |     |   -----------   |    |               |              ↗                 ↖                   |
 * |   |   | ViewModel  |  |     |  | ViewModel |  |    |               |            ↗                     ↖                 |
 * |   |   -------------   |     |   -----------   |    |               |    ----OneFragment-----      ----TwoFragment-----  |
 * |   ---------------------     -------------------    |               |    |                  |      |                  |  |
 * |                                                    |               |    --------------------      -------------------   |
 * |                                                    |               |                                                    |
 * -----------------------------------------------------                -----------------------------------------------------
 *     ↑ 이렇게 액티비티는 하난데, 프레그먼트는 두개인 상태에서                         ↑ 이렇게 ViewModel 을 하나를 두고 같이 이용하는 식으로 될 수도 있다.
 *     각각의 ViewModel을 따로따로 둬서 각각 이용할 수도 있고                              (어느쪽이 무조건적으로 좋다라기 보다는 상황에따라 이렇게도, 저렇게도 쓸 수 있다는 말인 듯)
 *
 *
 * * 이렇게 ViewModel 공유해서 하나로 쓰는거는 어떻게 쓰느냐?
 * - ViewModel 의 Owner 를 Activity 로 변경.
 *
 *          val model = ViewModelProvider(requireActivity()).get(MyFragmentViewModel::class.java)
 *                                         ↓
 *                                      Lifecycle Owner
 *                                      여기에 누구를 주느냐에 따라 개발자가 제어를 할 수 있을 것.
 *
 *      (위 코드 설명)
 * * 우리가 ViewModel 을 생성할 때, ViewModelProvider 를 이용을 하고, 여기 get 함수에다가 이 ViewModel 객체의 정보를 주는 것.
 *      이 ViewModelProvider 의 매개변수에 들어가는 것(requireActivity())이 Lifecycle Owner 이다.
 *      즉, 여기에 Activity 를 지정하면, Activity 의 어떤 ViewModel 을 이용 하다 보니까, 프레그먼트가 여러개라 하더라도 공유해서 쓸 수 있는 것.
 *      만약에 저 자리에, Fragment를 주면 그 Fragment 의 Lifecycle 만 감지하는 것이니까, 그 Fragment 만을 위해서.... 다른 프레그먼트에는 공유가 안된다는 것.
 *
 *
 *
 *
 */