package com.tak.c97

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class MyViewModel : ViewModel() {
    var sum = 0
    fun calSum(): MutableLiveData<String> {
        val liveData = MutableLiveData<String>()

        //시간이 걸리는 업무를 가정했기에, 여기 그 업무를 thread 를 가지고 처리하는 걸로.
        thread {
            for(i in 1..10){
                sum += i

                liveData.postValue(sum.toString())        //데이터 발행   ... 그러면 이 순간에 Activity 쪽 observer 에 데이터가 전달이 되는 것. 이 경우에는 10번 전달이 되겠네
                SystemClock.sleep(100)              // 일부러 지체시간 주고

            }

        }


        return liveData
    }
}

// 이렇게 ViewModel 준비 완. 이제 Activity 에서 이 ViewModel 을 이용하면 테스트가 될 것.


/**
 *
 *
 *     fun calSum(): MutableLiveData<String> {
 *         val liveData = MutableLiveData<String>()
 *
 *         return liveData
 *     }
 *
 *
 * //이렇게 되면, 액티비티에서 이 함수를 콜하자마자, 바로 얘가 리턴 됨. 그런 다음에 이 LiveData 를 가지고 observer 를 걸어놓으면 언제인지는 모르겠으나 LiveData 에 데이터가 발행되는 순간 옵저버 부분이 실행이 된다는 얘기.
 */