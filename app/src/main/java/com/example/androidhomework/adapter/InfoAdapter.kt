package com.example.androidhomework.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhomework.R
import com.example.androidhomework.`interface`.ItemElementClickListener
import com.example.androidhomework.holder.InfoHolder
import java.lang.Exception
import java.util.ArrayList

class InfoAdapter : RecyclerView.Adapter<InfoHolder>(){
    lateinit var mContext: Context
    lateinit var itemElementClickListener: ItemElementClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder {
        mContext = parent.context

        val v: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_info, parent, false)
        return InfoHolder(v)


    }

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        try {
            holder.spvSale.setTotalAndCurrentCount(200, 50)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }


    fun addOnClickListener(itemElementClickListener: ItemElementClickListener) {
        this.itemElementClickListener = itemElementClickListener
    }

}