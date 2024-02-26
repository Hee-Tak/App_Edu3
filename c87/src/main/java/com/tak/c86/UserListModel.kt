package com.tak.c86

data class UserListModel (
    var data: List<UserModel>?
)

//실제 전체 json 데이터, 거기에 유저 정보 여러 건이 있는데 그게 여기에 들어간다고 보면 된다.


//여기까지 UserModel 과 UserListModel 로 데이터가 저장될 VO 클래스는 준비가 됐음.
//이제 Retrofit 가지고 함수 콜 하면서 네트워킹을 준비해줘야 한다. 가장 먼저 준비할 건 네트워킹시에 호출할 함수를 가지는 인터페이스 부터 만들어야함.