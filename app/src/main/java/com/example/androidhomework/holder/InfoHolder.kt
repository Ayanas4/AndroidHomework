package com.example.androidhomework.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhomework.R
import com.example.androidhomework.utils.SaleProgressView

class InfoHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val spvSale: SaleProgressView = itemView.findViewById(R.id.saleProgressView)
}