package com.example.knight.features.knightmovement.transformers

import com.example.knight.R
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.ui.transformers.ViewDataTransformer
import com.example.knight.features.knightmovement.KnightDataModel
import com.example.knight.features.knightmovement.adapters.data.*
import com.example.knight.utils.UniqueIdGenerator.retrieveUniqueId
import javax.inject.Inject


class KnightViewDataTransformer @Inject constructor() : ViewDataTransformer<KnightDataModel, Data> {

    override fun transform(model: KnightDataModel): List<Data> {
        val data = mutableListOf<Data>()

        val titleRes = if (!model.haveBoard()) R.string.selectBoardSize else R.string.selectTiles
        data += TitleViewData(titleRes, retrieveUniqueId())

        data += BoardSizeViewData(
            model.boardSizes,
            model.boardSizes.indexOf(model.selectedBoardSize),
            retrieveUniqueId()
        )

        if (model.haveBoard()) {
            data += MaxMovesViewData(model.maxMoves, retrieveUniqueId())
        }

        model.selectedBoardSize?.let {
            data += BoardViewData(
                it,
                model.sourceNode,
                model.destinationNode,
                model.highlightPath,
                retrieveUniqueId(),
                false
            )
        }

        if (model.sourceNode != null && model.destinationNode != null && model.paths.isEmpty()) {
            data += UiMessageData(
                R.string.errorNoPath,
                retrieveUniqueId()
            )
        }

        model.paths.forEach {
            data += PathViewData(
                it,
                retrieveUniqueId()
            )
        }

        return data
    }

    private fun KnightDataModel.haveBoard() = selectedBoardSize != null

}