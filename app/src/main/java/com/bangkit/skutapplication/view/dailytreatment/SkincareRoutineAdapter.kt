package com.bangkit.skutapplication.view.dailytreatment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.helper.SkincareDiffCallback
import com.bangkit.skutapplication.model.DailyTreatmentItem
import java.util.ArrayList

class SkincareRoutineAdapter : RecyclerView.Adapter<SkincareRoutineAdapter.DailyTreatmentViewHolder>() {
    private val listItem = ArrayList<DailyTreatmentItem>()
    fun setListItem(listItem: List<DailyTreatmentItem>) {
        val diffCallback = SkincareDiffCallback(this.listItem, listItem)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listItem.clear()
        this.listItem.addAll(listItem)
        diffResult.dispatchUpdatesTo(this)
    }
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DailyTreatmentViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.daily_treatment_item, viewGroup, false)
        )


    override fun onBindViewHolder(holder: DailyTreatmentViewHolder, position: Int) {
        val item = listItem[position]
        holder.productName.text = item.product_name
        holder.time.text = item.time
        holder.exitButton.setOnClickListener { onItemClickCallback.onItemClicked(listItem[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class DailyTreatmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName : TextView =view.findViewById(R.id.productNm)
        val time: TextView = view.findViewById(R.id.time)
        val exitButton: ImageView = view.findViewById(R.id.exitIcon)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DailyTreatmentItem)
    }
}