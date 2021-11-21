package com.example.knight.features.knightmovement.adapters.delegates

import com.example.knight.databinding.RowTitleBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.TitleViewData
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class TitleDelegate {

    val viewType = hashCode()

    fun make() = adapterDelegateViewBinding<TitleViewData, Data, RowTitleBinding>(
        { layoutInflater, root -> RowTitleBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.tvTitle.text = context.getString(item.title)
        }
    }

}