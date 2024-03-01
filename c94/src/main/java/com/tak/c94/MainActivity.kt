package com.tak.c94

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.tak.c94.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }

    // **Inflate 의 의미 : 불어넣다. (안드로이드에서는, XML 레이아웃 파일을 읽어서 그 내용을, 메모리에 객체로 만드는 과정. 즉, XML에 정의된 레아아웃 구조를 실제 View 객체로 변환하는 것.)
    //메뉴는 코드에서 Inflate 시켜줘야 함.

    //메뉴를 만드는 함수. 여기에서 xml 파일을 알려준다.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

}

/**
 * 파트 25) JetPack 의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * C94) Toolbar
 *
 * * 툴바도 많이 이용하는 View
 *
 * - 툴바는 액션바를 개발자 뷰로 이용하기 위한 뷰
 * - 툴바와 액션바는 목적이 동일
 * - 액션바는 액티비티 윈도우가 자동 출력시켜 주는 액티비티의 구성요소
 * - 툴바는 개발자가 직접 핸들링하는 뷰
 *
 *
 * * 액션바 : 액티비티의 구성 요소. 개발자가 직접준비하는게 아니라, 액티비티 출력시에 윈도우가 자동 출력시켜 줌. 근데 이 액션바를 경우에 따라서 개발자가 직접 제어하고 싶은 경우가 있다. 당연히 이를 직접 제어하려면 뷰가 있어야 하고,......
 * * 툴바 : 액션바를 뷰로 이용하기 위한, 그래서 뷰로 이용하기 위한 것을 제공해주는 것이 툴바.
 *
 *
 *          ActionBar           /           Toolbar
 *
 *                          Window
 *
 *
 *                          Content
 *
 *               * 컨텐츠 영역이 툴바를 포함하게 됨
 *               * 툴바가, 컨텐츠를 구현하기 위한 레이아웃 xml 파일에 등록이 되어 있어야 된다.
 *
 *
 * - 액티비티 테마 설정을 통해 액션바가 화면에 출력되지 않게 설정
 * - 레이아웃 XML 파일에 Toolbar 를 등록
 *
 *          <androidx.appcompat.widget.Toolbar                  //클래스명 Toolbar
 *              android:id="@+id/toolbar"
 *              android:layout_width="match_parent"
 *              android:layout_height="wrap_content"
 *              style="@style/Widget.MaterialComponents.Toolbar.Primary"/>          //테마 스타일. 액션바에 적용되는 테마(Material)가 그대로 적용되면 되겠다.
 *
 *
 *
 *
 * - setSupportActionBar(binding.toolbar) 구문에 의해 액티비티의 액션바에 적용되는 내용이 툴바에 적용
 *
 *          setSupportActionBar(binding.toolbar)        //액션바에 있던 내용을, 툴바에 적용해달라
 *                                                      //액션바와 완전 동일하게, View 로 핸들링하는 툴바를 만들어 낼 수 있다.
 *
 *
 *
 *
 *      * themes.xml 파일에 NoActionBar 설정 필요. (근데 난 왜 원래 돼있음?)
 *
 *
 *
 */