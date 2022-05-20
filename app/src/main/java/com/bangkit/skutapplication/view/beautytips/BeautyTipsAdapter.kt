package com.bangkit.skutapplication.view.beautytips

import android.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.BeautyTipsItem


class BeautyTipsAdapter(private val listTips: List<BeautyTipsItem>) : RecyclerView.Adapter<BeautyTipsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.beauty_tips_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tips = listTips[position]
        viewHolder.number.text = tips.number.toString()
        viewHolder.beautyTips.text = tips.beautyTips
        viewHolder.beautyDescription.text = tips.beautyDescription
        val isVisible: Boolean = tips.isVisible
        viewHolder.beautyDescription.visibility = if(isVisible) View.VISIBLE else View.GONE
        viewHolder.card.setOnClickListener{
            tips.isVisible = !tips.isVisible
            notifyItemChanged(position)
        }
        if(isVisible) {
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }else{
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.itemNumber)
        val beautyTips: TextView = view.findViewById(R.id.beautyTips)
        val beautyDescription: TextView = view.findViewById(R.id.beautyDescription)
        val card: CardView = view.findViewById(R.id.card)
        val dropDown: ImageView = view.findViewById(R.id.dropDown)
    }

    override fun getItemCount() = listTips.size


}