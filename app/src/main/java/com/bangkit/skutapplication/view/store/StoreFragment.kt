package com.bangkit.skutapplication.view.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentStoreBinding
import com.bangkit.skutapplication.model.DailyRoutine


class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData(listAcneProduct)
        binding.acne.setOnClickListener{
            setData(listAcneProduct)
        }
        binding.rosacea.setOnClickListener {
            setData(listRosaceaProduct)
        }
        binding.eczema.setOnClickListener{
            setData(listEczemaProduct)
        }
    }

    private fun setData(item: ArrayList<DailyRoutine>){
        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
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
}