package com.bangkit.skutapplication.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentHomeBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.ViewPagerItem
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.view.beautytips.BeautyTipsActivity
import com.bangkit.skutapplication.view.camera.CameraFragment
import com.bangkit.skutapplication.view.dailytreatment.DailyTreatmentActivity
import com.bangkit.skutapplication.view.history.FaceScanHistoryActivity
import com.bangkit.skutapplication.view.login.LoginActivity

import com.bangkit.skutapplication.view.profile.ProfileViewModel
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val list = ArrayList<ViewPagerItem>()
    private  lateinit var viewModel: HomeViewModel
    private val Context.dataStore by preferencesDataStore(name = "profile")
    private var historyData: ArrayList<ListHistoryFaceItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showViewPager()
        viewModel.getUser().observe(viewLifecycleOwner){
            viewModel.getItem("Bearer ${it.token}")
        }
        viewModel.name.observe(viewLifecycleOwner){
            viewModel.saveUser(it)
        }
        binding.buttonBeautyTips.setOnClickListener {
            startActivity( Intent(activity, BeautyTipsActivity::class.java))
        }
        binding.buttonDailyTreatment.setOnClickListener {

            startActivity(Intent(activity, DailyTreatmentActivity::class.java))
        }
        binding.buttonCamera.setOnClickListener {
            it.findNavController().navigate(R.id.cameraNav)
        }
        viewModel.listHistory.observe(viewLifecycleOwner){
            for (data in it){
                historyData.add(data)
            }
        }
        binding.buttonHistory.setOnClickListener {
            val intent = Intent(activity, FaceScanHistoryActivity::class.java)
            intent.putExtra("historyData", historyData)
            startActivity(intent)
        }

        val trivia = resources.getStringArray(R.array.daily_trivia)
        val daysSinceEpoch = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().toEpochDay()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val random = Random(daysSinceEpoch)
        binding.dailyTriviaValue.text = trivia[random.nextInt(trivia.size)]
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(context.dataStore))
        )[HomeViewModel::class.java]
        viewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
//                binding.nameTextView.text = getString(R.string.greeting, user.name)
                Log.d("token", user.token)

//                mainViewModel.getUserStories(user.token)
//
//                mainViewModel.isError.observe(this) {
//                    showMessage(it)
//                }
            } else {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }

    }

}