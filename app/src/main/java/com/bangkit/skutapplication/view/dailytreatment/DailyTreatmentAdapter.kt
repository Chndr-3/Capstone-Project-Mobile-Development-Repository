package com.bangkit.skutapplication.view.dailytreatment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.databinding.DailyTreatmentItemBinding
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.dicoding.picodiploma.mynoteapps.helper.NoteDiffCallback
import java.util.ArrayList

class DailyTreatmentAdapter : RecyclerView.Adapter<DailyTreatmentAdapter.DailyTreatmentViewHolder>() {
    private val listItem = ArrayList<DailyTreatmentItem>()
    fun setListItem(listItem: List<DailyTreatmentItem>) {
        val diffCallback = NoteDiffCallback(this.listItem, listItem)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listItem.clear()
        this.listItem.addAll(listItem)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTreatmentViewHolder {
        val binding = DailyTreatmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyTreatmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyTreatmentViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class DailyTreatmentViewHolder(private val binding: DailyTreatmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyTreatmentItem) {
            with(binding) {
                productImg.setImageURI(item.product_image?.toUri())
                productNm.text = item.product_name
                time.text = item.time
            }
        }
    }
}