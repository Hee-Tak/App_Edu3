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