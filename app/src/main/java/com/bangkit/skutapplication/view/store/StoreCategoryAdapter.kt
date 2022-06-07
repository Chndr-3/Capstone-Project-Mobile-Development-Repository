package com.bangkit.skutapplication.view.store

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.DailyRoutine

class StoreCategoryAdapter(private val listProduct: List<DailyRoutine>) : RecyclerView.Adapter<StoreCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.product_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = listProduct[position]
        viewHolder.productTitle.text = product.dailyRoutine
        viewHolder.image.setImageResource(product.dailyRoutineIcon)
        viewHolder.productPrice.text = product.productPrice

        viewHolder.itemView.setOnClickListener {
            val uri: Uri = Uri.parse(product.url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.productIcon)
        val productTitle: TextView = view.findViewById(R.id.tvProduct)
        val productPrice: TextView = view.findViewById(R.id.tvProductPrice)
    }

    override fun getItemCount() = listProduct.size
}