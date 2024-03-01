package com.tak.c93

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.tak.c93.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //루트 객체를 화면 출력


        binding.viewpager.adapter = MyAdapter(this) //어댑터에 의해서 항목 결정

    }
}


/**
 * 파트25) JetPack 의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * c93) ViewPager2
 *
 * * 워낙 유명한 화면이고, 실제 개발자 분들이 많이 쓰는 뷰.
 *
 *
 * <ViewPager2>
 *
 * - ViewPager 는 스와이프 이벤트 (손으로 화면을 미는 이벤트) 에 의한 화면 전환을 목적
 * - androidx.viewpager 라이브러리와 별개로 2019년 androidx.viewpager2 가 새롭게 발표
 * - androidx.viewpager2 구현할 것을 권장
 *
 *          implementation 'androidx.viewpager2:viewpager2:1.0.0'           //dependency 관계 설정 하고 사용.
 *
 * * ViewPager 에서 중요한 개념은, 뷰 페이저에게 화면을 여러 장 등록해 줘야 하는데, 이 화면 하나 하나를 뷰 페이저는 항목으로 본다.
 *      단지, 이 항목을 화면 전체로 뿌려 주고, 스와이프 이벤트에 의해서 전환되는 기법을 제공해 주는 것.
 *      ***즉, ViewPager 가 AdapterView 라는 것.*****
 *      단지, 우리가 봤던 RecyclerView 라던가 ListView 처럼 항목을 여러 개 나열하는 건 맞는데,
 *      그 항목을 화면 한 장으로 뿌려주는 AdapterView 이다.
 *
 * * 그래서 이 ViewPager 를 이용하기 위해서는, Adapter 가 있어야 한다.
 *
 *
 *
 * - ViewPager2 에 사용할 수 있는 Adapter 는 두 가지가 제공           //생각해보면 한 화면 전체가아니라 특정뷰(화면의 일부분)에서만 스와이프 이벤트가 발생하도록 생각해볼 수 있음.
 *      > RecyclerView.Adapter
 *      > FragmentStateAdapter
 *
 *              <androidx.viewpager2.widget.ViewPager2 xmlns:android="http://schemas.android.com/apk/res/android"
 *                  android:id="@+id/viewpager"
 *                  android:layout_width="match_parent"
 *                  android:layout_height="match_parent"/>
 *
 *      //그러나 이 자체만으로는 아무것도 안나온다. 이렇게 준비한 다음에, 코드에서 어댑터를 준비하고, 어댑터를 등록해야 화면에 나온다.
 *
 *
 *
 *
 *
 *
 * - RecyclerView.Adapter 는 RecyclerView 에서 살펴 보았던 내용과 차이가 없다.
 *
 *          class MyPagerViewHolder(val binding: ItemPagerBinding): RecyclerView.ViewHolder(binding.root)               // 이 ViewHolder 를 하나를 준비를 해줘야 함. (뷰를 가지는 역할자)
 *
 *          class MyPagerAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // RecyclerView.Adapter 를 상속받아서 개발자 어댑터를 만들면서 항목 개수를 판단하기 위해
 *              override fun getItemCount(): Int {
 *                  return datas.size
 *              }
 *
 *              //뷰 홀더를 준비하기 위해서
 *              override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyPagerViewHolder(ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
 *
 *              //항목 하나하나를 꾸미기 위해서
 *              override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
 *
 *              }
 *
 *          }
 *          // 근데 이 경우에는 가능은 한데, 간단한 경우에만 이용해 달라.
 *
 *          * 왜냐하면 이 뷰페이저가,... 항목이 RecyclerView 같은 경우에는 항목이 한꺼번에 여러개가 나온다. 그런 항목 하나하나가 사실 그렇게까지 복잡할 순 없다.
 *              뷰페이저는 만약에 들어간다면 화면 전체가 항목 하나하나인건데, 그러면 이 화면 전체가 상당히 복잡해진다. 그렇게 만들어질수밖에 없다. 그 다음에, 이런 항목이 여러개가 있따는 거지 (왜냐하면 넘겨야할 화면들이 있으니까)
 *              그러면 대부분 어떻게 만드냐? 이 복잡한 화면 한 장을 위해서 만드는 것이 프레그먼트 인데, 그래서 대부분 만약에 뷰 페이지를 위해서 손가락 끌면서 나와야 되는 화면이 3장이다? 그럼 프레그먼트도 3개가 준비가 되어있어야 한다는 거지. 그리고 이 프레그먼트를 각 항목으로 주는 것.
 *              그래서 손가락을 끌면서 프레그먼트가 교체되게끔. 처리하는건데.... 이 뿌려지는게 뷰는 뷰이지만, 프레그먼트임. 이 프레그먼트와 관련된걸 준비해주는 어댑터가 있다.
 *              대부분의 경우에는, 간단한 경우에는, 그냥 RecyclerView 의 Adapter 를 이용할 수도 있겠지만, 대부분의 경우에는 복잡하다 보니까 항목이 프레그먼트로 만들어 질꺼고, 이 프레그먼트를 위한 어댑터를 쓰는 것이 조금 더 일반적이다.
 *
 *
 *
 *
 * - FragmentStateAdapter 이용                                    // -> 이 FragmentStateAdapter 클래스를 상속받아서 개발자 어댑터를 만들어 주면 된다.
 * - 항목이 프레그먼트로 작성되었다면 FragmentStateAdapter 를 이용      //항목하나하나가 프레그먼트로 작성 됐을 때 사용하는 것.      // 이 프레그먼트를 교체하려면 프레그먼트 매니저의 프레그먼트 트랜잭션을 이용해서 add, remove, replace 를 해줘야되는데, 그게 이미 구현이 돼 있다는 거지.
 * - FragmentStateAdapter 를 상속받아 Adapter 를 작성               // 그럼 우린 여기서. 지금 순간 나와야되는 프레그먼트만 정해주면 된다!!      -> 나머지 프레그먼트를 조정하는 것은 상위 클래스(FragmentStateAdapter)에서 자동으로 이미 구현이 돼 있다는 것.
 * - getItemCount() 함수와 createFragment() 함수를 오버라이드          //getItemCount() : 화면 갯수, createFragment() : 이 함수가 자동 콜 됨
 * - createFragment() 함수에서 리턴시킨 Fragment 객체가 각 항목에 출력   //createFragment() : 리턴값이 프래그먼트인데, 이 순간 나와야 되는 프래그먼트를 결정해서 리턴시켜주면 된다.
 *
 *          class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {      //개발자 Adapter 만드는데, FragmentStateAdapter 상속 받아서 만들어주고,
 *              val fragments: List<Fragment>
 *              init { //프래그먼트 준비
 *                  fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
 *                  Log.d("tak", "fragments size : ${fragments.size}")
 *              }
 *
 *              override fun getItemCount(): Int = fragments.size       //항목 갯수
 *
 *              override fun createFragment(position: Int): Fragment = fragments[position]      //화면 인덱스값. 나와야되는 프래그먼트만 결정해서 리턴시켜주면, 해당 프래그먼트가 끌리면서 나오게 된다는 구조.
 *
 *          }
 *
 *
 *
 *
 * - 화면 방향도 조정
 * - 기본으로 가로 방향이 적용         (가로방향 화면 전환)
 * - 세로방향으로 나오게 하려면 orientation 속성값을 조정
 *
 *          binding.viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL         //수직으로 설정
 *
 *
 *
 *
 *
 */