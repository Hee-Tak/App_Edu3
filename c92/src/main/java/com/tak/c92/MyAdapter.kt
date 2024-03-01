package com.tak.c92

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tak.c92.databinding.ItemMainBinding

//item_main.xml
class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)


// 어댑터가 이용이 될려면 ViewHolder 가 있어야 한다.
class MyAdapter(val datas: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }

    //여기까지 해서 RecyclerView 를 위한 Adapter 를 준비.


}

