package com.example.knight.features.knightmovement.adapters.delegates

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.knight.R
import com.example.knight.databinding.RowBoardSizeBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.BoardSizeViewData
import com.example.knight.features.knightmovement.models.BoardSize
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import timber.log.Timber

class BoardSizeDelegate(
    val callback: (selectedBoard: Int) -> Unit
) {
    val viewType = hashCode()

    fun make() = adapterDelegateViewBinding<BoardSizeViewData, Data, RowBoardSizeBinding>(
        { layoutInflater, root -> RowBoardSizeBinding.inflate(layoutInflater, root, false) }
    ) {

        binding.spSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val position = if (hasPreselectionItem()) pos - 1 else pos
                if (item.selectedItem == position) return

                if (pos != preselectionPosition()) {
                    Timber.e("Position $position selected: ${item.options[position]}")
                    callback.invoke(position)
                } else {
                    callback.invoke(-1)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        bind {
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                makeItems(
                    item.options,
                    if (hasPreselectionItem()) context.getString(R.string.boardSelectSize) else null
                )
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spSize.adapter = adapter
            binding.spSize.setSelection(if (item.selectedItem >= 0) item.selectedItem else 0)
        }
    }

    private fun AdapterDelegateViewBindingViewHolder<BoardSizeViewData, RowBoardSizeBinding>.preselectionPosition() =
        if (hasPreselectionItem()) 0 else -1

    private fun AdapterDelegateViewBindingViewHolder<BoardSizeViewData, RowBoardSizeBinding>.hasPreselectionItem() =
        item.selectedItem == -1

    private fun makeItems(options: List<BoardSize>, preselection: String?): Array<String> {
        val mappedList = options
            .map { "${it.width} x ${it.height}" }
            .toMutableList()
        preselection?.let { mappedList.add(0, it) }
        return mappedList.toTypedArray()
    }

}