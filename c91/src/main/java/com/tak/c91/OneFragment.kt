package com.tak.c91

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class OneFragment : Fragment() {        //프레그먼트 상속받아 만듦.



    override fun onCreateView(          //라이프사이클 함수인데,
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false) //onCreateView 쪽에서 xml 파일에 있는대로 화면을 출력 : R.layout.fragment_one
    }


}