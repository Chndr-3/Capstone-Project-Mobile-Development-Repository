package com.bangkit.skutapplication.view.beautytips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityBeautyTipsBinding
import com.bangkit.skutapplication.model.BeautyTipsItem
import java.util.ArrayList

class BeautyTipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeautyTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty_tips)
        binding = ActivityBeautyTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData(listBeautyTips)
    }
    private fun setData(item : ArrayList<BeautyTipsItem>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvBeautyTips.layoutManager = layoutManager
        val listTipsAdapter = BeautyTipsAdapter(item)
        binding.rvBeautyTips.adapter = listTipsAdapter

    }
    private val listBeautyTips: ArrayList<BeautyTipsItem>
        get() {
            var number = 1
            val beautyTips = resources.getStringArray(R.array.beautyTips)
            val beautyDescription = resources.getStringArray(R.array.beautyDescription)
            val listTips = ArrayList<BeautyTipsItem>()
            for (i in beautyTips.indices) {
                val tips = BeautyTipsItem(number++,beautyTips[i],beautyDescription[i])
                listTips.add(tips)
            }
            return listTips
        }
}