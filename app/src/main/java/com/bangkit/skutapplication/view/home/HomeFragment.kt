package com.bangkit.skutapplication.view.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.skutapplication.R
import java.util.ArrayList
import com.bangkit.skutapplication.databinding.FragmentHomeBinding
import com.bangkit.skutapplication.model.ViewPagerItem
import com.bangkit.skutapplication.view.beautytips.BeautyTipsActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val list = ArrayList<ViewPagerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        list.addAll(listViewPagerItem)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.buttonBeautyTips.setOnClickListener {
            val intent = Intent(context, BeautyTipsActivity::class.java)
            startActivity(intent)
        }

    }
    private val listViewPagerItem: ArrayList<ViewPagerItem>
        get() {
            val dataQuestion = resources.getStringArray(R.array.data_question)
            val dataAnswer = resources.getStringArray(R.array.data_answer)
            val listTanyaJawab = ArrayList<ViewPagerItem>()
            for (i in dataQuestion.indices) {
                val hero = ViewPagerItem(dataQuestion[i],dataAnswer[i])
                listTanyaJawab.add(hero)
            }
            return listTanyaJawab
        }
    private fun showViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(list)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator.setViewPager(binding.viewPager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.getRoot();
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showViewPager()
    }

    companion object {

    }
}