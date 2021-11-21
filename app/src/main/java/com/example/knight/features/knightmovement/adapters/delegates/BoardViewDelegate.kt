package com.example.knight.features.knightmovement.adapters.delegates

import android.content.Context
import android.view.Gravity.CENTER
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import com.example.knight.R
import com.example.knight.databinding.RowBoardViewBinding
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.data.BoardViewData
import com.example.knight.features.knightmovement.models.Move
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.utilities.IndexToLetterUtility.indexToLetter
import com.example.knight.utils.setBackground
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import timber.log.Timber

class BoardViewDelegate(
    private val callback: (sourceNode: Node?, destinationNode: Node?) -> Unit
) {
    val viewType = hashCode()
    private var recyclerWidth: Int = 0
    private val cellClickListener = CellClickListener()

    fun make() = adapterDelegateViewBinding<BoardViewData, Data, RowBoardViewBinding>(
        { layoutInflater, rv ->
            recyclerWidth = rv.width
            RowBoardViewBinding.inflate(layoutInflater, rv, false)
        }
    ) {

        bind {
            cellClickListener.clickFunc = { v ->
                val node = v.tag as Node
                Timber.v("Node clicked: ${node.y} : ${node.x}")

                val update = when {
                    item.sourceNode == null && item.destinationNode == null -> { //start selection
                        item.sourceNode = node
                        true
                    }
                    item.sourceNode != null && item.destinationNode == null -> { //end selection
                        item.destinationNode = node
                        true
                    }
                    else -> false
                }

                if (update) {
                    setCellBackground(item, v, node)

                    toggleResetButton()
                    callback(item.sourceNode, item.destinationNode)
                }
            }

            binding.btnReset.setOnClickListener {
                binding.tlBoard.reset(item)
                item.reset()
                it.visibility = GONE
                callback(null, null)
            }

            val table = binding.tlBoard
            val rows = item.size.height
            val columns = item.size.width

            val width = recyclerWidth - table.marginStart - table.marginEnd
            val rowHeight = width / (rows + 1)
            val cellSize = width / (columns + 1)

            val rowParams = TableRow.LayoutParams(MATCH_PARENT, rowHeight)
            val cellParams = TableRow.LayoutParams(cellSize, cellSize)

            table.removeAllViews()
            //Populate board
            for (r in 0..rows) {
                makeRow(context, rowParams).apply {
                    for (c in 0..columns) {
                        when {
                            r == 0 && c == 0    -> addEmptyCell(cellParams)
                            r == 0              -> addHeader(cellParams, indexToLetter(c))
                            r != 0 && c == 0    -> addHeader(cellParams, "$r")
                            else                -> addCell(cellParams, r, c, item)
                        }
                    }
                    table.addView(this)
                }
            }

            toggleResetButton()

        }

    }

    private fun AdapterDelegateViewBindingViewHolder<BoardViewData, RowBoardViewBinding>.toggleResetButton() {
        binding.btnReset.visibility = if (item.sourceNode != null) VISIBLE else GONE
    }

    private fun setCellBackground(
        item: BoardViewData,
        v: View,
        node: Node
    ) {
        when {
            item.sourceNode == node && item.destinationNode == node -> {
                v.setBackground(R.drawable.bg_board_cell_end)
            }
            item.sourceNode == node -> {
                v.setBackground(R.drawable.bg_board_cell_start)
            }
            item.destinationNode == node -> {
                v.setBackground(R.drawable.bg_board_cell_end)
            }
            else -> {
                val isPartOfPath = item.highlightPath?.moves?.contains(node) ?: false
                val bg = if (isPartOfPath) R.drawable.bg_board_cell_highlighted else R.drawable.bg_board_cell_inactive
                v.setBackground(bg)
            }
        }
    }

    private fun List<Move>.contains(n: Node): Boolean {
        return !filter { move -> !move.nodes.filter { it == n }.isNullOrEmpty() }.isNullOrEmpty()
    }

    private fun View.resetBackground() {
        setBackground(R.drawable.bg_board_cell_inactive)
    }

    private fun makeRow(
        ctx: Context,
        rowParams: TableRow.LayoutParams
    ): TableRow {
        val row = TableRow(ctx).apply {
            layoutParams = rowParams
            gravity = CENTER
        }
        return row
    }

    private fun TableRow.addHeader(params: TableRow.LayoutParams, txt: String) {
        addView(
            TextView(context).apply {
                layoutParams = params
                textSize = 18f
                gravity = CENTER
                text = txt
            }
        )
    }

    private fun TableRow.addEmptyCell(params: TableRow.LayoutParams) {
        addView(
            View(context).apply {
                layoutParams = params
            }
        )
    }

    private fun TableRow.addCell(params: TableRow.LayoutParams, row: Int, column: Int, item: BoardViewData) {
        addView(
            View(context).apply {
                val node = Node(row - 1, column - 1)
                layoutParams = params
                tag = node
                setCellBackground(item, this, node)
                setOnClickListener(cellClickListener)
            }
        )
    }

    private fun BoardViewData.reset() {
        sourceNode = null
        destinationNode = null
    }

    private fun TableLayout.reset(item: BoardViewData) {
        item.sourceNode?.resetCell(this)
        item.destinationNode?.resetCell(this)
        item.highlightPath?.moves?.forEach { it.nodes.forEach { node -> node.resetCell(this) } }
    }

    private fun Node.resetCell(view: TableLayout) {
        val row = view[y+1] as ViewGroup
        val cell = row[x+1]
        cell.resetBackground()
    }


    internal class CellClickListener : View.OnClickListener {
        lateinit var clickFunc: (v: View) -> Unit
        override fun onClick(v: View) = clickFunc(v)
    }

}