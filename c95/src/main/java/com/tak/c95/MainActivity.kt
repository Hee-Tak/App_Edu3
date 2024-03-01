package com.tak.c95

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tak.c95.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener{
            when(binding.fab.isExtended){
                true -> binding.fab.shrink()    //동그랗게 줄어듦.
                false -> binding.fab.extend()   //확장된다. 커진다.
            }
        }
    }
}

/**
 * 파트 25) JetPack의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * C95) FloatingActionButton
 *
 * * 이 View 도 이용비율이 높음.
 *
 * <ExtendedFloatingActionButton>
 *
 * - ExtendedFloatingActionButton 은 화면에 떠 있는 듯한 버튼을 제공하는 View               //줄여서, FAB 라고 많이들 부름. 그리고 목적 자체가 floating 임. 즉, 화면에 떠있는 듯 하게! (음영효과) (Extended 확장된)
 * - FloatingActionButton 뷰 가 제공되었고 많이 사용
 * - ExtendedFloatingActionButton 은 FloatingActionButton 과 동일한 목적
 * - 버튼에 문자열까지 출력이 가능 (ExtendedFloatingActionButton)
 *
 *
 * * 이게 무슨말이냐? -> 우리가 화면 UI를 구성할 때, 핵심은 화면의 컨텐츠(Content)이다. 그러면 문자열과 이미지가 컨텐츠가 되겠지.
 *                      여기에다가 버튼을 제공한다고 하면 얼마든지 버튼을 제공할 수 있을 텐데,
 *                      그런데 만약에 문자열이나 이미지가 길어서 화면이 스크롤이 된다고 했을 때, 이 버튼이 같이 스크롤 돼야 될거냐? 라는 문제가 있음.
 *                      물론 같이 스크롤 되어야 되는 버튼도 있긴 있겠지.
 *                      근데 버튼은 일반적으로 유저 이벤트를 받아들이기 위한 것이지, 컨텐츠라 볼 순 없다.
 *                      그래서 문자열 위주의 컨텐츠가 스크롤이 된다고 하더라도, 버튼은 스크롤에서 제외해서 문자열과 버튼이 다른 레이어에 있는 것 처럼 UI를 잡아줘야된다.
 *                      그래서 문자열은 스크롤이 된다고 하더라도, 버튼은 고정 위치에 있고, 스크롤이 안되는 다른 레이어에 있는 것 처럼 표현하기 위해서 보통 음영효과를 내서 이 버튼이 문자열 위에 떠 있는 듯 하게. 이렇게 표현을 하고자 한다.
 *                      => 이것을 목적으로 한게, FAB. FloatingActionButton 이다.
 *
 *                      그리고 남발하기 보다는, 가장 핵심 이벤트를 FloatingActionButton 으로 제공하기 위해서 하나 정도 제공하고, 보통 우측 하단에 많이 위치 시킨다.
 *
 *
 *          //레이아웃 xml 파일에 태그 등록
 *
 *          <com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
 *              android:text="extended FAB"
 *              app:icon="@android:drawable/ic_input_add"/>
 *
 *
 *
 *
 *
 * - icon 속성으로 이미지를 지정하면 이 이미지를 둥그렇게 감싸서 화면에 떠 있는듯 하게 출력 (문자열도 마찬가지)
 *
 * - text 정보를 지정하지 않고 icon 정보만 지정되면 둥근 모앙           // 그 + 모양 둥글게 있는 그거!!
 *
 *
 *
 * - 코드에서 확장되어 문자열까지 나오게 하거나 축소되어 아이콘만 나오게 하거나 등의 처리가 가능.
 *
 *          binding.fab.setOnClickListener {        //코드에서, 문자열이 나오거나 안나오거나를 조정할 수 있다.
 *              when(binding.fab.isExtended) {
 *                  true -> binding.fab.shrink()
 *                  false -> binding.fab.extend()
 *              }
 *          }
 *
 *
 *
 *
 * 실습에서는 그냥 FAB만 출력하게해서 클릭하면 커지고 동그랗게모아지고를 확인했을 뿐.
 *
 *
 * 만약에 RecyclerView 로 목록을 뿌리고, 그 위에다 FloatingActionButton 을 위치 시켰다.
 * 그러면 RecyclerView 의 목록이 나오는 부분과 FloatingActionButton 부분이 동일한 레이어가 아니라 떠있는듯하게 표현되고 뭐 그렇게 할 수 있다.
 * (실제 동작하면 어떻게 보일까를 상상한거네, 스크롤 내렸을때 뭐 어떻게 보일지 그런...)
 *
 *
 */