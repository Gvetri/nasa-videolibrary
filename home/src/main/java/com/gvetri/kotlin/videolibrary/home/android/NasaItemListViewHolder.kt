package com.gvetri.kotlin.videolibrary.home.android

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gvetri.kotlin.videolibrary.home.databinding.LayoutItemNasaBinding
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem

class NasaItemListViewHolder(private val binding: LayoutItemNasaBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nasaItem: NasaResultItem, onItemClicked: (NasaResultItem) -> Unit) {
        binding.apply {
            itemTitle.text = nasaItem.dataList.firstOrNull()?.title
            itemDescription.text = nasaItem.dataList.firstOrNull()?.description
            val imageUrl = nasaItem.nasaLinkModels.filter { it.render == NasaMediatype.IMAGE }
            previewImage.load(imageUrl.firstOrNull()?.href)
            cardItemContainer.setOnClickListener { onItemClicked(nasaItem) }
        }
    }
}
