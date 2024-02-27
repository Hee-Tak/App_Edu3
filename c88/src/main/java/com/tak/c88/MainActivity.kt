package com.tak.c88

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.resultView)

        Glide.with(this)
            .load("https://www.google.co.kr/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
            .override(200, 200)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView)
    }
}

/**
 * c88 ) Glide 로 이미지 다운로드
 *
 *
 * <Glide>
 * : 라이브러리 (image : 바이트 데이터. 이걸 서버로 부터 이미지 데이터를 다운로드 받아서 화면에 뿌리거나 그럴때 이 이미지 다운받는걸 어떻게 프로그램 짤거냐? 가 주제가 되겠다.)
 *
 * - 구글에서 만든 이미지 핸들링을 위한 라이브러리 (네트워킹만을 목적으로 하지는 않음.)          //dependency 관계 설정하고 쓰기.
 * - 리소스 이미지, 파일 이미지, 네트워크 이미지 획득
 * - 이미지의 크기 조정
 * - 로딩 이미지 표시
 * - 에러 이미지 표시
 *          implementation 'com.github.bumptech.glide:glide:4.11.0'
 *
 *
 *
 * - 이미지를 ImageView 에 출력
 *
 *          Glide.with(this)
 *              .load(R.drawable.seoul)                 //이미지
 *              .into(resultView)                       //<<-- findViewBy...
 *
 *          Glide.with(this)
 *              .load(url)                              //네트웍
 *              .into(resultView)
 *
 *
 * - 특정 크기로 이미지가 로딩
 *
 *          Glide.with(this)
 *              .load(R.drawable.seoul)
 *              .override(200, 200)
 *              .into(resultView)
 *
 *
 *
 * - 로딩, 에러 이미지 출력
 *
 *          Glide.with(this)
 *              .load(url)
 *              .override(200, 200)
 *              .placeholder(R.drawable.loading)
 *              .error(R.drawable.error)
 *              .into(resultView)
 *
 *
 *
 *
 */