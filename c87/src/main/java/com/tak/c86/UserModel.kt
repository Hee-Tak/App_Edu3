package com.tak.c86

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("first_name")           //fist_name : 실제 json 데이터의 키 값
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String
)   //이게 하나의 유저 정보라고 보면 된다.
    //그런데, 이 유저 정보 여러 개가, 다른 어떤 페이지 정보나 이런 걸로 json 이 구성이 되어 있다.
    //그래서 이걸 포함시킬 수 있는 또 VO 객체를 만들자. -> UserListModel

/**
 * 서버로부터 데이터를 받을 모델 클래스, DTO 클래스.
 * 이걸 만들어 줘야 자신들이 알아서 파싱하고 컨버팅 시켜서 담아준다.
 *
 * 저기 변수명이 실제 데이터로 넘어오는 변수명과 동일하지 않으면 어노테이션을 붙여줘야한다.
 */