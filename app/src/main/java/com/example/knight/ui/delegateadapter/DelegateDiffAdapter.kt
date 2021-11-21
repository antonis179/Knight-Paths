package com.example.knight.ui.delegateadapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import java.util.*


open class DelegateDiffAdapter<D: Data>(
    itemCallback: DiffUtil.ItemCallback<D>? = null
) : AsyncListDifferDelegationAdapter<D>(
    itemCallback ?: DiffItemCallBack<D>()
) {

    protected fun addDelegate(
        viewType: Int,
        delegate: AdapterDelegate<List<D>>,
        allowReplacingDelegate: Boolean = false
    ) {
        delegatesManager.addDelegate(viewType, allowReplacingDelegate, delegate)
    }

    /**
     * This method clones the list items and submits them to super setItems in order to calculate
     * the diff result.
     */
    override fun setItems(items: List<D>?) {
        val newDataList: List<D>? = if (items != null) ArrayList(items) else null
        differ.submitList(newDataList) { }
    }

}