package com.tak.c96

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


/**
 * 파트 25) JetPack 의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * C96) DrawerLayout
 *
 * * 이것도 거의 대부분의 앱의 화면에 적용이 되고 있는 아주 유명한 View
 *
 *
 * <DrawerLayout>
 *
 * - DrawerLayout 은 액티비티 화면에 안보이던 내용이 왼쪽 혹은 오른쪽에서 유저 스와이프 이벤트에 의해 끌리면서 나타나는 기능을 제공.
 *
 *
 *
 *
 * * 액티비티의 뷰이긴한데 기본적으론 보이지 않음. 유저가 손으로 쭈욱 끌어당겼을 때, 안보이던 부분이 끌려나오는 것. 그리고 다시 손으로 끌어서 닫는 것
 * * 얘를 왜 쓰냐? 이 DrawerLayout 이 안보이다 끌려나오는 부분인데, 그럼 이 DrawerLayout 부분을 어떻게 구현할 거냐? 라는 것은, 사실 개발자 마음이고,
 *      그래서 여기에 텍스트 데이터를 뿌릴 수도 있는거고, 이미지를 뿌릴 수도 있는 거고,...
 *      근데 거의 대부분이 메뉴, 목록 ,... 이런식으로 많이 구현한다. (꼭 목록, 메뉴로만 하라는건 아니고. 보통은 그렇다는것)
 *
 * * 그리고 권장사항이 보통 있는데, 유저 이벤트를 우리가 받아들이기 위해서 버튼 같은 것들을 제공하는데, 하나의 화면에서 유저 이벤트 종류가 꽤 여러개가 있다.
 *      그렇다면 컨텐츠(Content)와 같이 스크롤되는 위치에 버튼을 제공하지 말자는 것이 권장사항이다. 컨텐츠를 뿌리기에도 화면이 좀 작다라는 것.
 *      일단, 유저 이벤트 중 가장 중요한 것은 컨텐츠와 상관없이 언제라도 누를 수 있게끔 둥둥둥 떠 있게끔... -> 그래서 FloatingActionButton 을 많이 쓰는 것... 이것조차도 많이 나열하면 별로라 하나 정도만 나열.
 *      그 다음 많이 쓰는 유저 이벤트를 액션 아이템으로, 그리고 나머지 기타 등등의 유저이벤트가 있다고 하면 DrawerLayout 으로 목록으로 제공하라....
 *      그래서 거의 대부분 DrawerLayout 이 끌려 나오면 목록 모양이고, 눌렀을 때 유저 이벤트에 의해서 화면 전환이 되거나,... 이런식으로 많이 구현 한다.
 *      꼭 그래야 한다는 것은 아니지만 권장사항에 의해서 거의 대부분 이렇게 구현하고 있다.
 *
 *
 *
 * - 액티비티 레이아웃 XML 파일에서 Drawer 가 출력되어야 하는 부분의 태그를 DrawerLayout 으로 선언.
 * - DrawerLayout 하위에는 두 개의 뷰(View)가 선언.
 * - 첫 번째 하위 태그 부분을 액티비티 화면에 출력
 * - 두 번째 하위 태그 부분이 안보이다가 끌려 나오며 출력
 *
 *              ex)     <DrawerLayout>
 *                          <TextView />        <!--원래 처음부터 나오는 부분-->
 *                          <ImageView />       <!--안보이다가 끌려나오는 부분-->
 *                      </DrawerLayout>
 *
 * * 이 아래의 내용은, 두 번째 끌려나오는 부분 얘기. 끌려 나오는 영역의 방향을 지정할 수 있음.
 * - android:layout_gravity 속성값을 이용하여 화면에서 나오는 방향을 지정
 * - left, right, start 를 지정
 * - start 값을 주면 언어의 쓰는 방향에 따라 left, right 가 자동 결정 (영어나 한국어나 .. 대부분은 일단 왼쪽에서 오른쪽 순서로 쓴다.)
 *
 *              <androidx.drawerlayout.widget.DrawerLayout
 *                  xmlns:android="http://schemas.android.com/apk/res/android"
 *                  android:id="@+id/drawer"
 *                  android:layout_width="match_parent"
 *                  android:layout_height="match_parent">
 *
 *                  <LinearLayout ................>
 *                      <!--............-->                                 <!-- LinearLayout으로 구성한 화면이 처음부터 보이게 될 것.-->
 *                  </LinearLayout>
 *
 *                  <TextView                                               <!-- 이 TextView 가 처음엔 안나오다가, 손가락이 끌렸을 때 나오게 되는 식 -->
 *                      android:layout_gravity="start"/>
 *              </androidx.drawerlayout.widget.DrawerLayout>
 *
 *
 *
 * - 툴바 영역에 토글 버튼을 같이 제공 가능. (메뉴버튼, 토글 아이콘) -> 열고닫는거 편하게 할라고        (일종의 메뉴 취급이 됨)
 *
 * - 드로우어 토글 버튼은 ActionBarDrawerToggle 클래스에서 제공
 *                                                                   //열렸을때 설명 문자열      //닫혔을때 설명 문자열
 *              toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
 *
 *              supportActionBar?.setDisplayHomeAsUpEnabled(true)
 *              toggle.syncState()                                  //토글 버튼을 눌러서 열리고 닫히고 하는거랑, 손으로 드로우(끌어당기는행위)를 했을 때 열리고 닫기는 거를 어디에선가 한번 이렇게 싱크를 맞춰 달라. 라고 선언을 하는 것.
 *
 *              override fun onOptionsItemSelected(item: MenuItem): Boolean {
 *                  if(toggle.onOptionsItemSelected(item)) {        // 지금 이벤트가 토글 버튼인지 확인.
 *                      return true                                 // 그렇다면, 우리 로직을 타지않고 자체적으로 처리해야하기 때문에 여기서 그냥 return 시켜버림.
 *                  }
 *                  return super.onOptionsItemSelected(item)
 *              }
 *
 * * 토글 버튼은 액션바(ActionBar) 영역에 들어가는 애임. 액션바 영역에 들어가는 아이콘을 흔히 액션아이템(ActionItem) 이라고 불렀음. => 이게 메뉴 기법이어서, 유저가 이 액션 아이템을 터치하면 메뉴 이벤트(Menu Event) 처리로 들어감   (일종의 메뉴 취급이 됨)
 *      그래서 onOptionsItemSelected() 이벤트 함수로 들어감.
 *      근데 원래 이 함수는, 우리가 메뉴의 아이디 값을 가지고 우리가 이벤트 로직을 담아줘야됨.
 *      근데 지금 같은 경우는 토글 버튼을 눌렀을 때, 메뉴 처리가 돼서 이 함수(onOptionsItemSelected()) 가 호출(call)이 되긴 되지만, 우리가 이벤트 처리할 것 없이 자체적으로 Drawer를 Open/Close 할 목적임.
 *      그래서 하나의 처리가 더 필요한데, 어떤 처리냐? -> 메뉴의 이벤트 처리하는 함수 내에서 만약에 지금 이벤트가 토글 버튼 쪽 거라면, 그러면 자체적으로 처리하고. (우리 로직을 안 타야 되니까 거기서 바로 그냥 return 시켜버림)
 *      (그렇지 않으면, 이 로직이 안들어가면 우리의 이벤트를 탈려고하는데, 우리는 이벤트 준비한게 없다? 그러면 아무것도 실행이 안 돼 버리는 이런 상황이 발생할 수 있음)
 *
 *
 *
 *
 *
 *
 * <NavigationView>
 *
 * - NavigationView 는 DrawerLayout 에 의해 끌리면서 출력되는 부분을 위한 뷰(View)
 * - 항목을 나열하는 화면을 구성.
 *
 * * 예를 들어 생각해보자면, DrawerLayout 부분을 끌어당겨왔는데 위에 뭐 로그인 정보 이런 항목이 있을 수 있잖아 그런거 얘기하는거같은데?
 * * 이런 정도로 Drawer 의 화면을 잡는다고 하면, 도와주는 View를 쓰면 된다.
 *      이거를 위해서 우리가 RecyclerView, ListView 를 직접 만들 필요는 없다.
 *      => 암튼 이 도와주는 View 가 NavigationView.
 * * DrawerLayout 의 끌려나오는 부분을 꼭 Navigation 을 써야하는 것은 아니다.
 *      그런데 만약 끌려나오는 부분의 화면이 (프로필, 프로필사진, 설정, 홈 등의 메뉴.....등등) 뭐 이런식으로 구성이 되겠다면 NavigationView 로 하는게 훨씬 더 편하다는 것.
 *      아무튼 주로 DrawerLayout 의 메뉴 구성에 사용됨. (메뉴 항목 정의) 그리고, 항목을 선택했을 때 이벤트 처리 까지.
 *
 *
 *      headerLayout        -> 프로필, 프로필 사진, 이메일 등등... 윗부분만 디자인한 부분.. 여기에 대한 레이아웃 xml 파일을 만들어주고 -> NavigationView 에다가 이 윗부분을 위한 레이아웃 xml 파일을 따로 알려주면 됨.
 *
 *      menu                -> 아이콘 + 설정, 도움말, 홈, 등의 (메뉴)... 이러한 항목들의 목록 . 메뉴 눌렀을 때 이벤트 처리도 해야함. => menu.xml 파일 하나 만들어 놓고 필요한 항목 갯수따라 item 태그 준비
 *                                                                                                                       그리고 NavigationView 에 그 xml 파일만 알려주면 목록 화면이 되겠지. => RecyclerView 이런거 만들 필요 없이 간단히 가능.
 *      **이렇게 각각에 따른 xml파일을 작성하고, NavigationView에 해당 xml파일들을 알려주면 -> NavigationView 가 화면에 뿌려주게 된다.
 *      * NavigationView 는 이런 류의 화면을 만드는 데에 상당히 편한 경우
 *
 *
 *              //NavigationView 를 쓸 경우, NavigationView ↓ 쓴 다음에, 메뉴 xml (menu.xml) 파일 하나 만들어서
 *              <com.google.android.material.navigation.NavigationView
 *                  android:id="@+id/main_drawer_view"
 *                  android:layout_width="wrap_content"
 *                  android:layout_height="match_parent"
 *                  android:layout_gravity="start"
 *                  app:headerLayout="@layout/navigation_header"    //윗부분에 대한 레이아웃 xml 파일 하나 만들어서, headerLayout 부분에 지정.
 *                  app:menu="@menu/menu_navigation" />             //menu.xml 파일 => 여기에 menu 란 속성에 지정
 *
 *              //이렇게만 해도, 화면은 전페이지 있는 것 처럼 나오게 된다.
 *
 *
 *
 *
 *
 *
 *
 */