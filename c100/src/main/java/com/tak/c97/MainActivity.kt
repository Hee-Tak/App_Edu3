package com.tak.c97

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tak.c97.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)           //이렇게 두 코드
        setContentView(binding.root)                                        //뷰 바인딩

        val model : MyViewModel by viewModels()                             // 이렇게 delegator 를 이용하면, ViewModelProvider 를 이용한 결과와 동일. 아무튼 이렇게 뷰모델 객체 준비

        //뷰모델 함수콜은, 이벤트 처리해서... 뷰모델의 함수를 콜
        binding.button.setOnClickListener {
            model.calSum().observe(this ){ //이렇게 되면 리턴하는게 LiveData. 그러면 LiveData 발행 시에 감지할 수 있는 옵저버 observer 를 등록하면 된다.
                                                            // 이 블록이, 이제 데이터가 전달이 됐을 때 실행이 되는 것. 일단 우리는 화면에 찍는게 목적.
                binding.resultView.text = it

            }
        }

    }

    // 이 앱은, 일단 버튼이 눌렸을 때, ViewModel 의 업무를 실행하고, 그 업무 실행 결과값이 LiveData 로 발행이 돼서 이 위에 찍히는 것을 목적으로.
}

/**
 * 파트 26) ViewModel 을 이용해 MVVM 모델을 적용해 보자.
 *
 * C100 ) LiveData
 *
 * * ViewModel 을 이용하면 거의 대부분 LiveData 가 같이 이용된다. LiveData 는 ViewModel 뿐만 아니라, 여러 곳에서 유용하게 이용할 수 있는 클래스이다.
 *
 * <LiveData>
 *
 * - ViewModel 의 결과
 * - ViewModel 에서 String 등의 결과를 리턴 시킬 수도 있지만 LiveData 를 리턴 시켜 Observer 로 결과 이용
 *
 *              class MyViewModel : ViewModel() {                   //ViewModel 상속받은 개발자 ViewModel 클래스
 *                  fun someData() : String {                       // 선언되어있는 함수 두 개를 View 에서 사용. (액티비티나 프레그먼트에서 이용)... 이 함수를 이용했을 때 여기에 뭔가 비즈니스 업무가 처리가 된 다음에, 결과값이 넘어가야 된다고 생각해보자
 *                      return "hello"                              // 지금 이 함수처럼 String data를 직접 넘길 수도 있긴 있다. 뭐 잘 넘어가서 쓸 수 있겠지. 근데 이 구조에서 문제가 되는 것이 있다. View 가 이 결과값이 넘어오기 전까지 대기 상태에 빠질 수 있다.
 *                  }                                               // 실제 샘플 로직에서는 그냥 바로 문자열을 넘겼지만, 업무로직이 구현이 된다고 하면 시간이 좀 걸릴 수 있을 거고, 그 시간이 걸리는 동안 View 는 대기상태에 빠진다는 문제가 있다.
 *
 *                  fun someData2() : MutableLiveData<String> {     //View 에서 ViewModel 을 이용하면 결과값이 LiveData 인걸 넘겨 받게 될 것. 이 LiveData 를 일단은 무조건 (View에게) 넘겨줘버린다.
 *                      val liveData = MutableLiveData<String>()
 *                      thread {
 *                          SystemClock.sleep(3000)                 //일부러 시간이 걸리는 3초 정도의 업무를 담아놨는데, 결국 이 뒤의 데이터는 최소한 3초가 지나야 데이터가 발생이 된다는 것. 근데, thread 로 돌렸기 때문에, 바로 그냥 요청하자마자 LiveData 를 리턴 했기 때문에
 *                          liveData.postValue("world")             //View는 일단 LiveData를 받을 거고, 대기없이 움직일거 계속 움직일것. 그랬다가, 3초가 지난 다음에, 실제로 업무가 발생했을 때 이 데이터(지금 이 코드 한줄)... 이 데이터를 LiveData 에 포함시켜서 넘겨버리면.. 비동기적으로 데이터를 받을 수 있다는 측면.
 *                      }
 *
 *                      return liveData                             //View는 얘를 일단 리턴을 받을 거고. 그럼 일단 View는 대기 없이 움직일거 계속 움직인다는 것.
 *                  }
 *              }
 *
 * * LiveData 가 가장 많이 이용되는 곳은, ViewModel 의 결과값을 View 에 옮기기 위해서 (넘기기 위해서) LiveData 타입으로 넘긴다.
 *      물론, ViewModel 의 결과값을 View 에 그냥 일반적인 데이터 타입으로 넘기는 것이 불가능한 것은 아니지만, LiveData 로 넘기면 Observer 를 이용해서 결과값을 받을 수 있다 라는 것.
 *      (비동기 모델을 이야기 하는 것.)
 *
 * ** 일단 LiveData로 이용되는 이유가. 업무처리된 다음에 데이터가 실제로 발생해서 넘겨질텐데 그럼 이 대기시간을 없애야하니까
 *      View 입장에서는 요청해서 LiveData 를 리턴 받아놓고. 할거하다가 저쪽에서 업무처리가 끝나서 실제 데이터가 발생하면 이 데이터를 제대로 넘겨받게 되는... 이러한 비동기 구조라고 한다.
 *
 *
 *
 *
 *
 * * 여기 아래는 View 쪽 코드.
 *
 *
 * - LiveData 의 변경을 감지하는 observer 는 Observer 를 구현한 클래스
 * - LiveData 의 값이 변경되면 Observer 의 onChanged() 함수가 자동 호출
 * - 더 이상 감지가 필요 없는 경우 명시적으로 removeObservers 함수 소출
 *                                                                  //이쪽의 코드는 액티비티나 프레그먼트의 코드.
 *              val observer = object : Observer<String> {          // observer 를 하나 준비. Observer 를 구현한 클래스.
 *                  override fun onChanged(t: String?) {            // onChanged() 함수 override 받아 작성
 *                      Log.d("tak", "onChanged.......$t")
 *                  }
 *              }
 *              model.someData2().observe(this, observer)           //model.somdeData2() ==> ViewModel 의 함수를 호출. 이쪽에 LiveData 가 전달이 된다.
 *                                                                  //그 LiveData 의 observe. -> LiveData 로 부터 데이터가 발행이 되면 매개변수로 주어진 observer 가 감시하면 된다.
 *                                                                  // 여기까지만 코드작성된대로 쭉 해놓고 자기할일 쭉~ 한다. 그랬다가 3초 후에 실제 데이터가 넘어오면, 이 observer 로 등록되어 있는 onChanged 함수가 콜 되어서, 그 데이터를 이용할 수 있게끔...  이렇게 처리가 된다는 이야기.
 *                                                                  // 한번만 발생하는게 아니라, 데이터가 발행이 될 때마다 또 콜되고, 발행되고, 콜되고, 넘어오고 그런식임.
 *              val liveData = model.someData2()
 *              //.......................
 *              liveData.removeObservers(this)                      //이렇게 감지를 끊을 수도 있다.
 *
 * * 위 코드 설명.
 *      그러면 View 에서 LiveData 를 먼저 넘겨 받았는데, 받은 다음에 언제 넘어올 줄 모른다는 것.
 *      LiveData 에서 데이터 넘어오는 변경감지를 해줘야 한다.      ===> 이것을 옵저버 observer 를 등록한다고 표현.
 *
 *
 * * 이미 제공되고 있는 LiveData 가 있어서, MutableLiveData 이런 것들을 쓰면 된다.
 *      그런데, 원한다면 개발자 LiveData 클래스를 만들어서 써도 됨.
 *
 *
 *
 * - Custom LiveData
 * - LiveData 를 상속받아 작성
 * - ViewModel 이외에 다른 곳에서 사용 가능
 *
 *              class MyLiveData : LiveData<String>() {         //LiveData 를 상속받아 만든 개발자 클래스
 *                  fun sayHello(name: String) {                //개발자 임의의 함수 등록시켜서 처리
 *                      postValue("Hello $name")
 *                  }
 *              }
 *
 *
 *              val liveData1 = MyLiveData()
 *              liveData1.observe(this) {                       //observe -> 실제 데이터가 발행이 되면 뭔가를 처리해 달라.
 *                  Log.d("tak", "result : $it")
 *              }
 *              liveData1.sayHello("tak")                       //실제 데이터 발행은 이 LiveData 의 개발자가 추가한 함수를 이런식으로 call 해서 쓰면 됨.  그러면 observe 부분이 실행이 된다.
 *                                                              // 이 샘플 예제는, 데이터를 발행하는 곳과 데이터를 이용하는 곳을 같이 작성해서 그렇지만, 원래는 따로따로 작성될 것.
 *                                                              // 그래서 val liveData1 = MyLiveData(); liveData1.observe(this) { Log.d("tak", "result : $it") }  이 부분은 액티비티에서 작성하고
 *                                                              // 아래의 liveData1.sayHello("tak") 이쪽 부분은 ViewModel 에서 작성하고... 그렇게 될 것.
 *
 *
 *
 *
 *
 *
 * 5 : 25 실습
 *
 *
 */