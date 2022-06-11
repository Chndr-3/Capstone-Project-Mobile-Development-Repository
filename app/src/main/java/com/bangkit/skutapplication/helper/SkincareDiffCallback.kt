package com.dicoding.picodiploma.mynoteapps.helper

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.skutapplication.model.DailyTreatmentItem


class SkincareDiffCallback(private val mOldItem: List<DailyTreatmentItem>, private val mNewItem: List<DailyTreatmentItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldItem.size
    }

    override fun getNewListSize(): Int {
        return mNewItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldItem[oldItemPosition].id == mNewItem[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldItem[oldItemPosition]
        val newItem = mNewItem[newItemPosition]
        return oldItem.id == newItem.id && oldItem.id == newItem.id
    }
}