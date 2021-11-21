package com.example.knight.features.knightmovement.adapters.delegates

import com.example.knight.R
import com.example.knight.databinding.RowPathBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.PathViewData
import com.example.knight.features.knightmovement.models.Path
import com.example.knight.features.knightmovement.utilities.IndexToLetterUtility.indexToLetter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class PathViewDelegate(
    private val onPathSelected: (path: Path) -> Unit
) {

    val viewType = hashCode()

    fun make() = adapterDelegateViewBinding<PathViewData, Data, RowPathBinding>(
        { layoutInflater, root -> RowPathBinding.inflate(layoutInflater, root, false) }
    ) {

        binding.btnDisplay.setOnClickListener { onPathSelected(item.path) }

        bind {
            val sb = StringBuilder()
            val arrow = context.getString(R.string.rightArrow)
            item.path.moves.forEachIndexed { moveIndex, move ->
                move.nodes.forEachIndexed { index, node ->
                    if (index > 0) sb.append(arrow)
                    sb.append("${indexToLetter(node.x+1)}${node.y+1}")
                }

                if (moveIndex < (item.path.moves.size-1)) sb.append("\n")
            }

            binding.tvPath.text = sb.toString()

        }

    }

}