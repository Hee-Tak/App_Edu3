package com.tak.c86

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {

    @GET("api/users")                                            //이 함수를 호출했을 때, 서버 연동하기 위한 url 정보 주기. "api/users" 이 path 로 서버에 요청을 해라. //어딘가에 baseUrl이 선언되어 있을거고, 그 baseUrl 뒤에 들어가는 path 정보가 된다.
    fun doGetUserList(@Query("page")page: String): Call<UserListModel>   //실제 네트워킹이 필요할 때, 이 함수를 콜하겠다는 것. 결과값은 네트워킹이 가능하게 해주는 콜 객체. 제네릭으로 어떤 타입의 VO객체가 전달해야 되는지 알려주고.
                                                                        //부가적인 정보가 들어가야 된다. page: String 이대로 냅두면 네트웍시에 전혀 이용하지 않는다. Retrofit에 알려줘야한다. page:String 을 서버에 넘겨라. -> 어노테이션을 붙여줘야 한다.
}


//@Query("page")page: String            이 상태로 서버에 넘어간다는 것.
//         키     value


//이제 mainActivity 로 넘어가서 Retrofit 객체를 초기화시키는것부터.