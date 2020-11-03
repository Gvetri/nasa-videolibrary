package com.gvetri.kotlin.videolibrary.home.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvetri.kotlin.videolibrary.home.databinding.LayoutItemNasaBinding
import com.gvetri.kotlin.videolibrary.model.NasaResultItem

class NasaListAdapter : RecyclerView.Adapter<NasaItemListViewHolder>() {

    private val nasaItemList: MutableList<NasaResultItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaItemListViewHolder =
        NasaItemListViewHolder(
            LayoutItemNasaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: NasaItemListViewHolder, position: Int) {
        holder.bind(nasaItemList[position])
    }

    override fun getItemCount(): Int = nasaItemList.size

    fun submitList(list: List<NasaResultItem>) {
        nasaItemList.clear()
        nasaItemList.addAll(list)
        notifyDataSetChanged()
    }

}
