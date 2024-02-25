package com.tak.c86

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


/**
 * 86강 ) Retrofit 구조
 *
 * * 안드로이드에서 네트워킹하기 위해서 가장 유명한 라이브러리 혹은 프레임워크
 *
 *
 *
 * <Retrofit2>
 *
 * - Retrofit2 는 Square사에서 만든 HTTP 통신을 위한 라이브러리(혹은 프레임워크) (내부적으로는 okHttp를 이용하고 있다. 선택사항이긴 하지만, okHttp를 이용해서 설정 정보를 줄 수도 있다.)
 *
 *                      Interface
 *                         a()
 *                         b()
 *                          ↓
 *                      \         /
 *                ------          ----------------------
 *                |                                     |
 *                |         Retrofit2                   |
 *                |                                     |
 *                -------------------------         ----
 *                                        /    ↓    \
 *                                           a(){ } ------> Call        => Call 객체가 리턴된다. 이 Call 객체가 네트워킹이 가능한 객체. 이 Call 객체를 받은 다음에, enqueue 하면은 그 순간에 네트워킹이 되는 것.
 *                                           b(){ } ------> Call                => 우리가 준 함수명과 동일한 api로 함수가 선언되어 있다. 그리고 실제 네트워킹과 관련된 코드가 들어가 있는 것.
 *                                         Service
 *
 * * 실제 네트워킹을 하기 위해서는 원래 네트웍과 관련되어 있는 프로그램 코드가 작성이 되어야 한다. 근데 이 프로그램 코드는 우리가 작성하지 않는다. 우리는 이 네트워킹을 위한 정보만 준다. (정보 -> url 정보, data-request, data-response)
 * * 이러한 정보만 구성해서 Retrofit 에게 넘기면 이 정보를 참조해서, 이 네트워킹을 하기 위한 실제 프로그램 코드는 자동으로 만들어 준다.
 * * 정보는 어떻게 주느냐? 어노테이션으로 정보를 준다. 그러면 이 프레임워크에서 어노테이션 정보를 파악해서 실제 네트워킹을 위한 코드가 만들어 진다. 라는 거다.
 * * 개발자는 네트워킹을 위해서 인터페이스만 만들어주면 된다. 그 다음 이 인터페이스를, Retrofit 에 등록을 시킨다. 그러면 Retrofit 에서 이 인터페이스를 구현한 클래스를 자동으로 만들어 줘버린다.
 * * 실제 네트웍시에는 그 인터페이스만 콜하면 된다. 그 인터페이스를 보고 실제 네트워킹과 관련되어 있는 코드가 자동으로 들어간다.
 *
 *
 * * Retrofit 을 이용하기 위해서는 라이브러리 dependency 관계를 걸어줘야한다.
 *
 *
 * - com.squareup.retrofit2:retrofit - Retrofit 필수 라이브러리
 * - JSON, XML 파싱을 위한 라이브러리
 * - JSON 혹은 XML 의 데이터를 모델(VO 클래스)객체로 변환 라이브러리
 *
 *          implementation 'com.squareup.retrofit2:retrofit:2.9.0'
 *          implementation 'com.google.code.gson:gson:2.8.6'                //얘는 gson이라는 꽤 유명한 parser 를 등록해 놓은 것. (꼭 얘를 쓰라는건 아님)
 *          implementation 'com.squareup.retrofit2:converter-gson:2.9.0'    // gson을 지원해주는 컨버터(converter) 사용 등록.
 *
 *
 *
 *          our         ------------------------------> Server
 *          App         <----------data----------------
 *                                (XML
 *                                (JSON
 *
 *               이걸 받아서 프로그램에서 이용할 때는 거의 대부분 VO클래스 혹은 DTO클래스 로 변환해서 쓴다.
 *               데이터를 받은 후에, 이 XML을 파싱하거나, JSON을 하싱해서 데이터를 추출. (안드로이드 표준 라이브러리에 xml parser 나 json parser 가 있다.) (외부라이브러리 이용해도 됨)
 *
 * * 이 Retrofit 에서, 서버에서 넘어오는 xml 과 json을 파싱해서, 이쪽에 데이터를 추출해서, 우리가 준비해 놓은 DTO 객체를 생성해서, DTO 객체에 데이터를 담아주는 것 까지 자동으로 해준다.
 * * 그래서 이 때 이용할 파서(parser) 가 뭐냐? -> 개발자가 알아서 지정하라고 함
 *
 *
 *
 * * 현재 이용할 수 있는 파서들
 * - Gson       : com.squareup.retrofit2:converter-gson
 * - Jackson    : com.squareup.retrofit2:converter-jackson
 * - Moshi      : com.squareup.retrofit2:converter-moshi
 * - Protobuf   : com.squareup.retrofit2:converter-protobuf
 * - Wire       : com.squareup.retrofit2:converter-wire
 * - Simple XML : com.squareup.retrofit2:converter-simplexml
 * - JAXB       : com.squareup.retrofit2:converter-jaxb
 * - Scalars (primitives, boxed, and String) : com.squareup.retrofit2:converter-scalars
 *
 *
 *
 * - 모델 클래스란, 서버와 주고 받는 데이터를 표현하는 클래스       (VO 클래스, DTO 클래스, 모델 클래스) (뭐라고 부르던, 모두 다 데이터를 담기 위한 클래스이다.)
 * - JSON, XML 데이터를 파싱해 모델 클래스 객체에 담아주는 것을 자동화
 *
 *          {
 *                  "id": 7,
 *                  "email": "michael.lawson@reqres.in"
 *                  "first_name": "Michael",
 *                  "last_name": "Lawson",
 *                  "avatar": "https://reqres.in/img/faces/7-image.jpg"
 *          }
 *
 *
 *
 *
 *          data class UserModel(               //개발자가 이렇게다가 클래스를 하나 만든다. data class 로 해도되고 그냥해도되고
 *                                              //서버사이드에서 넘어온 JSON 데이터를 이쪽에서 파싱해서, 이 객체를 생성해서 여기에 담아준다는 것. 근데 JSON 데이터가 여러개 있다? 그럼 이 데이터를 어느 변수에 담는지를 어떻게 판단하는가? 기본 값이 JSON key-value로 키값과 동일한 변수에 담는다.
 *              var id: String,                 //당연히 json의 키 값과 변수명을 맞춰주는 것이 편하다. 안맞는 경우에는 어노테이션을 등록해주면 된다.
 *              @SerializedName("first_name")   //이게 어노테이션
 *              var firstName: String,
 *              //@SerializedName("last_name")
 *              var lastName: String,
 *              var avatar: String,
 *
 *              var avatarBitmap: Bitmap
 *          )
 *
 */

/**
 * C87) Retrofit 활용
 *
 *
 * * 먼저, Retrofit 을 사용한다라고 하면, 인터페이스를 하나 만들어야됨 => 이걸 Retrofit 에 전달 => 해당 인터페이스 타입의 실제 네트워킹이 가능한 클래스의 객체가 만들어져서 실제는 이 객체를 이용하는 것. (class 객체)
 *
 * <서비스 인터페이스>
 *
 * - 네트웍 통신에 이용될 인터페이스
 * - 네트웍 통신이 필요한 순간 호출할 함수를 선언                              (실제 서버에서 넘어온 데이터를 자기네가 파싱해서 Converter 로 Converting 시켜서 우리의 DTO 객체를 생성해서 전달해준다는 것)
 *                                                                      이 제네릭 타입이 우리가 만든 DTO 객체라고 보면 된다.    어떤 DTO를 이용을 해야하는지를 우리가 제네릭 타입으로 알려주면 된다. 그러면 데이터 파싱하고 객체 생성하고 데이터 담아주는 것까지 자동으로 해준다는 것
 *          interface INetworkService {                                     ↑
 *              @GET("api/users")                                           |           //이렇게 어노테이션으로 정보를 줘야한다. @GET("api/users") => HTTP GET 방식으로 서버랑 연동해달라는 거고, "api/users" 이게 서버 연동시켜주는 url 혹은 url 뒤에있는 path 값. 이러한 어노테이션으로 동작할 수 있는 정보가 만들어 진다는 거다.
 *              fun doGetUserList(@Query("page") page: String): Call<UserListModel>     //Call 객체 : 실제 네트워킹이 가능한 객체 . 이 Call 객체를 받아서 -> enqueue -> 이렇게 해서 네트워킹이 된다.
 *          }                       ↓
 *                                  //서버에 리퀘스트할 때 넘겨야하는 데이터가 있다면 이렇게 매개변수를 주면 된다. @Query 로 어노테이션 붙여서. 여기선 "page" 이게 키 값이 될 것. page: String 이 그 순간의 값. 이런 키-값이 서버에 넘어가는것도 자동으로 해준다.
 *          //실제 네트워킹이 필요할때는 doGetUserList() 이 함수만 콜하겠다 라는 거다.
 *
 *
 *
 *
 * * Retrofit을 이용하기 전에 초기설정이 필요하다. 어플리케이션이 실행되면서 최초의 딱 한 번만 실행되는 곳에다가 작성하는 것이 일반적.
 *
 * <Retrofit 객체>
 *
 * - Retrofit 의 초기 설정을 목적       -> Retrofit 객체를 초기화 시켜야한다. -> Retrofit 객체가 움직여야 될 초기설정 정도.
 * - baseUrl() 함수를 이용해 서버 연동을 위한 URL 을 설정
 * - addConverterFactory() 함수를 이용해 converter 지정
 *
 *          val retrofit: Retrofit
 *              get() = Retrofit.Builder()              //이 Retrofit.Builder() 가지고 초기화를 하는 건데, 여기 뒤로 가장 기초적인 정보를 주는 것.                  interface A {
 *                  .baseUrl("https://reqres.in/")                          //여기에 baseUrl을 주고, 실제 네트웍시에 호출 될 곳에다가 대충 주면 --> 그러니까    ---->   @GET("/aaa")    이렇게 주면 이 baseUrl 뒤에 들어가는 path가 된다. //일단 거의 대부분 서버랑 연동을 여러번 한다 생각하면 서버랑 연동 시에 도메인까지는 거의 동일할텐데,
 *                  .addConverterFactory(GsonConverterFactory.create())     //어떤 컨버터를 이용할거냐? 라는걸 지정                                                fun a()                                                   //매번 서버 요청시에, 이 함수를 콜 할때 풀도메인을 쓰는게 너무 귀찮을거다. 공통적인 도메인까지는 baseUrl에 주면 편함.
 *                  .build()                                                                                                                              }                                                             //이렇게 해두었다 하더라도 특정 서버를 연동한다는게 도메인부터 바뀔 수 있는데 이때는 baseUrl이 있다 하더라도 @PATH 에 풀 도메인을 달아줘서 바꾸면 된다.
 *                  //원한다면 여기에 로그 관련 설정, 보안 관련 설정 등이 들어갈 수도 있다.
 *                  //아무튼 이런식으로 네트워킹을 하기 위한 최초의 어떤 초기설정 이런 것들을 등록하면서 Retrofit 객체를 만들어 주면 된다.
 *
 *
 *
 *
 *
 *
 * <인터페이스 구현 객체 획득>
 *
 * - Retrofit 객체를 이용해 서비스 인터페이스를 구현한 클래스의 객체를 획득
 *
 *          var networkService: INetworkService = retrofit.create(INetworkService::class.java)      //우리의 인터페이스 타입으로 전달되어지긴 하지만, 실제로는 라이브러리가 만든 객체라고 보면 된다.
 *
 * * 실제 네트워킹을 하기 위해서는 인터페이스를 구현한 객체가 필요한데, 그래서 아까 설정한 Retrofit 객체에 create, 우리가 만든 인터페이스 명 (INetworkService) -> 여기에 a(), b() 함수가 등록되어 있다고 가정. 이 두 함수에 각각 어노테이션이 붙은걸 보고, 그대로 실제 네트워킹 가능한 코드가 만들어진,... 이러한 객체가 전달이 된다.
 * * 그러고 여기서 a() 함수를 call 하면 Call 객체가 리턴되고, 그런것.
 * * 경우에 따라서 인터페이스를 하나만 만들란 법은 없다. 네트워킹 종류에 따라 여러 개를 만들것. 그럼 몇번이고 retrofit.create 쓰면 된다. (상품정보와 관련된 네트워킹, 회원정보와 관련된 네트워킹 따로 인터페이스를 분리시켜서 create 함수 가지고 객체를 얻어서 사용하면 된다.)
 *
 *
 *
 * <네트워킹>
 *
 *          val userListCall = networkService.doGetUserList("1")        //객체를 얻은 다음에, 거기에 선언되어 있는 우리가 만든 함수를 콜 해버린다. doGetUserList. 매개변수있으면 매개변수 주고. "1"
 *                                                                      // 그러면 이렇게 된 정보를 가지고 네트워킹을 할 수 있는 Call 객체가 전달이 된다. userListCall
 *
 *          userListCall.enqueue(object : Callback<UserListModel> {                                         //실제 네트워킹은 이 Call 객체 enqueue 하는 순간 네트워킹이 된다고 보면 된다. 그럼 이제 여기서 결과값을 얻어서 우리가 이용을 해야 하는데, 결과값이 전달된 다음에 그걸 어떻게 이용하느냐는 우리의 알고리즘이고 상이한 것. 그래서 매개변수에 Callback 을 전달하는 것
 *              override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {     //서버로 부터 데이터를 정상적으로 받은 순간.
 *                                                                                                          //매개변수로 실제 우리가 제네릭타입으로 준 데이터 UserListModel, 그러니까 넘어온 데이터를 우리가 만든 DTO 객체에다가 담아준다. 이게 (Response<UserListModel>) 서버데이터로 매개변수로 전달되고
 *                  val userList = response.body()      //이 서버 데이터를 받아내서, 여기서 자체적인 화면에 뿌리든, 연산을 수행하든, 등의 이러한 로직을 실행시키면 된다.
 *
 *
 *              }
 *
 *              override fun onFailure(call: Call<UserListModel>, t: Throwable) {   //네트워킹 실패시 자동으로 call
 *                  call.cancel()
 *              }
 *         })
 *
 *
 *
 *
 *
 *
 */