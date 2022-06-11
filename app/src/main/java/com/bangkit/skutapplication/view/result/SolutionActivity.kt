package com.bangkit.skutapplication.view.result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivitySolutionBinding
import com.bangkit.skutapplication.model.DailyRoutine
import com.bangkit.skutapplication.view.store.StoreCategoryAdapter
import com.google.android.material.appbar.MaterialToolbar


class SolutionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySolutionBinding

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.result_title)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val text: String

        when (intent.extras?.getString("extra_disease")) {
            "Acne" -> {
                text = getString(R.string.solution_text) + " " + getString(R.string.acne)
                binding.tvText.text = text
                setData(listAcneProduct)
            }
            "eksim" -> {
                text = getString(R.string.solution_text) + " " + getString(R.string.eczema)
                binding.tvText.text = text
                setData(listEczemaProduct)
            }
            "rosacea" -> {
                text = getString(R.string.solution_text) + " " + getString(R.string.rosacea)
                binding.tvText.text = text
                setData(listRosaceaProduct)
            }
        }
    }

    private fun setData(item: ArrayList<DailyRoutine>){
        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvProduct.layoutManager = layoutManager
        binding.rvProduct.adapter = StoreCategoryAdapter(item)
    }

    private val listAcneProduct: java.util.ArrayList<DailyRoutine>
        get() {
            val acneProduct = resources.getStringArray(R.array.acneProduct)
            val acneProductImg = resources.obtainTypedArray(R.array.acneProductImg)
            val acneProductPrice = resources.getStringArray(R.array.acneProductPrice)
            val acneProductUrl = resources.getStringArray((R.array.acneProductUrl))
            val listAcneProduct = java.util.ArrayList<DailyRoutine>()
            for (i in acneProduct.indices) {
                val tips = DailyRoutine(acneProduct[i],acneProductImg.getResourceId(i, -1), acneProductPrice[i], acneProductUrl[i])
                listAcneProduct.add(tips)
            }
            return listAcneProduct
        }

    private val listRosaceaProduct: java.util.ArrayList<DailyRoutine>
        get() {
            val rosaceaProduct = resources.getStringArray(R.array.rosaceaProduct)
            val rosaceaProductImg = resources.obtainTypedArray(R.array.rosaceaProductImg)
            val rosaceaProductPrice = resources.getStringArray(R.array.rosaceaProductPrice)
            val rosaceaProductUrl = resources.getStringArray((R.array.rosaceaProductUrl))
            val listRosaceaProduct = java.util.ArrayList<DailyRoutine>()
            for (i in rosaceaProduct.indices) {
                val tips = DailyRoutine(rosaceaProduct[i],rosaceaProductImg.getResourceId(i, -1), rosaceaProductPrice[i], rosaceaProductUrl[i])
                listRosaceaProduct.add(tips)
            }
            return listRosaceaProduct
        }

    private val listEczemaProduct: java.util.ArrayList<DailyRoutine>
        get() {
            val eczemaProduct = resources.getStringArray(R.array.eczemaProduct)
            val eczemaProductImg = resources.obtainTypedArray(R.array.eczemaProductImg)
            val eczemaProductPrice = resources.getStringArray(R.array.eczemaProductPrice)
            val eczemaProductUrl = resources.getStringArray((R.array.eczemaProductUrl))
            val listEczemaProduct = java.util.ArrayList<DailyRoutine>()
            for (i in eczemaProduct.indices) {
                val tips = DailyRoutine(eczemaProduct[i],eczemaProductImg.getResourceId(i, -1), eczemaProductPrice[i], eczemaProductUrl[i])
                listEczemaProduct.add(tips)
            }
            return listEczemaProduct
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}