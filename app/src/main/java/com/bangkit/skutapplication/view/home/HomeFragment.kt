package com.bangkit.skutapplication.view.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentHomeBinding
import com.bangkit.skutapplication.model.ViewPagerItem
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.view.beautytips.BeautyTipsActivity
import com.bangkit.skutapplication.view.dailytreatment.DailyTreatmentActivity
import com.bangkit.skutapplication.view.history.FaceScanHistoryActivity
import java.time.LocalDate
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val list = ArrayList<ViewPagerItem>()
    private var historyData: ArrayList<ListHistoryFaceItem> = arrayListOf()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list.addAll(listViewPagerItem)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


    }
    private val listViewPagerItem: ArrayList<ViewPagerItem>
        get() {
            val skinDisease = resources.getStringArray(R.array.skinDisease)
            val skinDiseaseDescription = resources.getStringArray(R.array.skinDiseaseDescription)
            val skinDiseaseIcon = resources.obtainTypedArray(R.array.skinDiseaseIcon)
            val listTanyaJawab = ArrayList<ViewPagerItem>()
            for (i in skinDisease.indices) {
                val hero = ViewPagerItem(skinDisease[i],skinDiseaseDescription[i],skinDiseaseIcon.getResourceId(i, -1))
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
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showViewPager()

        binding.buttonBeautyTips.setOnClickListener {
            startActivity( Intent(activity, BeautyTipsActivity::class.java))
        }
        binding.buttonDailyTreatment.setOnClickListener {

            startActivity(Intent(activity, DailyTreatmentActivity::class.java))
        }
        binding.buttonCamera.setOnClickListener {
            it.findNavController().navigate(R.id.cameraNav)
        }
        binding.buttonHistory.setOnClickListener {
            val intent = Intent(activity, FaceScanHistoryActivity::class.java)
            intent.putExtra("historyData", historyData)
            startActivity(intent)
        }
        val trivia = resources.getStringArray(R.array.daily_trivia)
        val daysSinceEpoch = LocalDate.now().toEpochDay()
        val triviaIndex = (daysSinceEpoch % trivia.size).toInt()
        binding.dailyTriviaValue.text = trivia[triviaIndex]


    }


}