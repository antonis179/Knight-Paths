package com.example.knight.features.knightmovement.adapters.delegates

import com.example.knight.databinding.RowUiMessageBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.UiMessageData
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class UiMessageDelegate {

    val viewType = hashCode()

    fun make() = adapterDelegateViewBinding<UiMessageData, Data, RowUiMessageBinding>(
        { layoutInflater, root -> RowUiMessageBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.tvMessage.text = context.getString(item.message)
        }
    }

}