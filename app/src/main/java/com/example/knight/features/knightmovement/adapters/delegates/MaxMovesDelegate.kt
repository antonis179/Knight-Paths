package com.example.knight.features.knightmovement.adapters.delegates

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.knight.databinding.RowMaxMovesBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.MaxMovesViewData
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class MaxMovesDelegate(
    val callback: (maxMoves: Int) -> Unit
) {
    val viewType = hashCode()

    fun make() = adapterDelegateViewBinding<MaxMovesViewData, Data, RowMaxMovesBinding>(
        { layoutInflater, root -> RowMaxMovesBinding.inflate(layoutInflater, root, false) }
    ) {

        binding.etMoves.setOnEditorActionListener { view, action, _ ->
            if (EditorInfo.IME_ACTION_DONE == action && !view.text.isNullOrBlank()) {
                val inm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                view.clearFocus()
                callback(Integer.parseInt(view.text.toString()))
            }
            true
        }

        bind {
            binding.etMoves.setText("${item.moves}")
        }

    }

}