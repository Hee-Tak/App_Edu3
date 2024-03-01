package com.tak.c92

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tak.c92.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()
        for(i in 1..10){
            datas.add("item ${i}")
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(datas)

        //항목과 항목간의 구분 선
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}

/**
 * 파트25) JetPack의 View 를 활용해 상용앱 수준의 화면을 만들자.
 *
 * C92) RecyclerView
 *
 * <RecyclerView>
 * - 목록화면을 만들기 위해 사용되는 뷰
 *
 * * 우리가 이미 봤던 뷰가 있음 : ListView   -> Platform 에서 제공되고 있는 API (표준 API).  얘는 어댑터뷰니까 어댑터가 있어야 함. Adapter 가지고 리스트뷰에 화면을 구성하는. 그런 구조가 된다.
 * * RecyclerView 로도 구현할 수 있다.      -> androidx 에서 제공 (JetPack) 얘도 당연하지만 어댑터뷰다 보니까 어댑터가 있어야 함. / ViewHolder 라고 하는 역할자가 있어야 함. / LayoutManager 가 있어야 함.
 *
 *          LitView                             RecyclerView        -> RecyclerView 는 구성요소가 좀 많다. => 목록화면 커스터마이징이 용이하도록
 *          - platform                          - androidx
 *          - Adapter                           - Adapter           -> 필수 구성 요소
 *                                              - ViewHolder        -> 필수 구성 요소
 *                                              - LayoutManager     -> 필수 구성 요소
 *                                              - ItemDecoration    -> optional (선택적) 구성 요소
 *                                              - ItemAnimation     -> optional 구성 요소
 *
 *
 * * 왜 실제 개발에서도 RecyclerView 를 쓰냐? 왜 RecyclerView 인가?
 *      -> 리스트뷰가 목록 화면을 만들긴 만드는데, 이 목록 화면에 대한 커스터마이징이 너무 어렵다.
 *      -> 목록화면을 뿌리고 해당 목록의 서브 항목들이 있을텐데 여기에다가 들여쓰기 하듯이 또 뿌려지고..
 *      -> 항목이 몇개가 나오는데 구분 짓기 위해서 좀 떨궈서 뿌려주고...
 *      이런 식으로 다양하게 출력하고 싶은데 ListView는 이런게 거의 불가능하다는 것.
 *      그래서 RecyclerView 를 쓴다.
 *
 *
 * - RecyclerView 를 위한 구성요소
 *      > Adapter         : (필수) 항목을 구성하는 역할
 *      > ViewHolder      : (필수) 항목에 필요한 뷰 객체를 가지는 역할       => 항목을 구성하기 위해서는 View 객체가 있어야하겠지. 그 View 객체를 가지는 역할자를 따로 두자는 것.
 *      > LayoutManager   : (필수) 항목을 배치하는 역할                    => 배치를 조정할 수 있음 (단순 위아래가 아닌, 다른방법을 이용한 자유로운 배치 가능)
 *      > ItemDecoration  : (옵션) 항목을 꾸미기 위한 역할                 => 어댑터에 의해서 나오는 항목을 추가적으로 꾸밀 수 있다. 테두리를 준다던가..특정항목만 들여쓰기 추가해준다던가...
 *
 *
 *      [ViewHolder]                      [Adapter]                     [LayoutManager]
 *      - ImageView
 *      - TextView1
 *      - TextView2             => (리스트의 항목을 구성)        =>    (리스트의 여러 항목들을 배치)   => RecyclerView
 *      - TextView3
 *      - .... etc
 *
 *
 *
 * - ViewHolder 는 RecyclerView.ViewHolder 를 상속받아 작성
 *      class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
 *                 ↓
 *          * 원래 이 안에다가 View 를 선언하고, (View 를 위한 변수를 선언하고)
 *            findViewById() 가지고 View 객체를 얻어서, 가지고 있기만 하면 됨.                                                                item_main.xml  -> 여기에 있는 View 를 다 갖고있는 객체 겠네.
 *                                                                                                                                   ↗
 *          => 근데 뷰 바인딩을 배웠잖아! 그래서 이 부분의 코드는 안들어가도 됨. 대신에, 그 ViewBinding 객체만 유지하고 있으면 됨.(val binding: ItemMainBinding  부분) -> 이 ViewBinding 객체인 binding 에 뷰가 다 등록되어 있는 것.
 *
 *
 *
 *
 *
 * - Adapter 는 RecyclerView.Adapter 를 상속받아 작성 (아래의 3개를 override)
 *      > getItemCount()        : 항목 개수를 판단하기 위해 자동 호출 (적절하게 항목 갯수를 리턴시켜 줘야함)
 *      > onCreateViewHolder()  : 항목의 뷰를 가지는 ViewHolder 를 준비하기 위해 자동 호출     (뷰 홀더가 별도의 클래스로 만들어 지는데, 그 뷰 홀더 클래스를 준비하게끔. 뷰 홀더는 있어야 하니까.)
 *      > onBindViewHolder()    : ViewHolder 의 뷰에 데이터를 출력하기 위해서 자동 호출 (실제 항목을 구성해주는... 그래서 그 항목의 데이터를 출력하거나 이벤트를 걸거나 등의 작업들이 들어간다.)
 *
 *      class MyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {      //Adpater 를 상속받은 개발자 클래스 MyAdapter
 *          override fun getItemCount(): Int {
 *              TODO("Not yet implemented")
 *          }
 *                                                                                                                                                                                (실제 항목을 꾸미기 위한)
 *          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {    //항목을 위한 뷰를 갖고있는 뷰 홀더 객체를 생성해서 리턴시켜 주면 됨. 여기서 리턴시킨 뷰홀더 객체... 얘가 밑에 onBindViewHolder 여기에 매개변수로 전달이 된다.
 *              TODO("Not yet implemented")
 *          }
 *                                          //holder : RecyclerView.ViewHolder => 위의 onCreateViewHolder 로 부터 받아오는거임.
 *          override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
 *              TODO("Not yet implemented")
 *
 *              //여기서 열심히 데이터를 구성해주고 이벤트 등록하고,.... 이런 식의 프로그램을 작성하면 된다.
 *          }
 *      }
 *
 *
 *
 * - RecyclerView 에 Adapter 와 LayoutManager 를 등록시켜 화면에 출력
 *      binding.recycler.layoutManager = LinearLayoutManager(this)          //등록... //기본적으로 LinearLayoutManager 를 이용하면 세로 방향으로 배치된다. 근데 이러면서도 가로방향으로 줄 수가 있음. 그럼 동일한 항목이 화면에 가로방향으로 나온다.
 *      binding.recycler.adapter = MyAdapter(datas)
 *
 *
 * * 어댑터에 의해 항목이 몇 개 준비가 되어있다고 가정하고, 이제 이걸 레이아웃 매니저로 배치를 해줘야 함..
 *
 *
 *
 *
 *
 * - RecyclerView 에 배치해주는 역할이 LayoutManager
 *      > LinearLayoutManager       : 가로 혹은 세로방향으로 항목 배치
 *      > GridLayoutManager         : Grid 로 항목 배치
 *      > StaggeredGridLayoutManager: 높이가 불규칙한 Grid 로 항목 배치
 *
 *
 *
 */