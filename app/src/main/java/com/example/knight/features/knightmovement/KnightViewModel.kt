package com.example.knight.features.knightmovement

import androidx.lifecycle.*
import com.example.knight.di.ViewModelModule.Companion.CALC_DISPATCHER
import com.example.knight.di.ViewModelModule.Companion.IO_DISPATCHER
import com.example.knight.ui.ContentState
import com.example.knight.ui.LoadingState
import com.example.knight.ui.UiState
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.ui.transformers.ViewDataTransformer
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.models.Path
import com.example.knight.features.knightmovement.repositories.BoardSizeRepository
import com.example.knight.features.knightmovement.repositories.KnightDataDbRepository
import com.example.knight.features.knightmovement.usecases.PathCalculationUsecase
import com.example.knight.utils.Do
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class KnightViewModel @Inject internal constructor(
    private val state: SavedStateHandle,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
    @Named(CALC_DISPATCHER) private val calcDispatcher: CoroutineDispatcher,
    private val transformer: ViewDataTransformer<KnightDataModel, Data>,
    private val boardSizeRepo: BoardSizeRepository,
    private val pathCalculationUsecase: PathCalculationUsecase,
    private val knightDbRepo: KnightDataDbRepository
) : ViewModel() {

    private val model: MutableLiveData<KnightDataModel>
        get() = state.getLiveData("model")
    private fun setModel(model: KnightDataModel) = state.set("model", model)

    val data: LiveData<List<Data>>
        get() = state.getLiveData("data")
    private fun setData(items: List<Data>) = state.set("data", items)

    private val _uiState: MutableLiveData<UiState> by lazy { MutableLiveData() }
    val uiState: LiveData<UiState> by lazy { _uiState }

    @Throws(CancellationException::class)
    suspend fun loadData() {
        withContext(ioDispatcher) {
            //Saved state
            data.value?.let { updateView(it) } ?: fetch()
        }
    }

    @Throws(CancellationException::class)
    suspend fun refreshData() {
        withContext(calcDispatcher) {
            val items = model.value?.let { transformer.transform(it) } ?: return@withContext
            ensureActive()
            updateView(items)
        }
    }

    @Throws(CancellationException::class)
    private suspend fun fetch() {
        refreshUiState(LoadingState)
        withContext(ioDispatcher) {
            var modelInst = Do.safe(
                { knightDbRepo.retrieveKnightDataModel() },
                { null }
            )

            if (modelInst == null) {
                modelInst = KnightDataModel(
                    maxMoves = DEFAULT_MAX_MOVES,
                    boardSizes = boardSizeRepo.retrieveBoardSizes().toMutableList()
                )
                knightDbRepo.insertKnightDataModel(modelInst)
            }

            saveAndRefresh(modelInst)
            refreshUiState(ContentState)
        }
    }

    private suspend fun updateView(items: List<Data>) {
        withContext(Dispatchers.Main) {
            ensureActive()
            setData(items)
        }
    }

    suspend fun updateBoardSizeSelection(selectedBoard: Int) {
        withContext(ioDispatcher) {
            val modelInst = model.value ?: return@withContext
            val boardSizes = modelInst.boardSizes

            modelInst.clearPaths()

            if (boardSizes.isEmpty() || selectedBoard < 0 || selectedBoard >= boardSizes.size) {
                modelInst.apply {
                    selectedBoardSize = null
                    clearNodes()
                }
            } else {
                modelInst.apply {
                    if (boardSizes[selectedBoard] != selectedBoardSize) {
                        clearNodes()
                    }
                    selectedBoardSize = boardSizes[selectedBoard]
                }
            }

            saveAndRefresh(modelInst)
            modelInst.updateDb()
        }
    }

    suspend fun updateBoardSelection(sourceNode: Node?, destinationNode: Node?) {
        withContext(calcDispatcher) {
            val modelInst = model.value ?: return@withContext
            modelInst.sourceNode = sourceNode
            modelInst.destinationNode = destinationNode

            if (sourceNode != null && destinationNode != null) {
                refreshUiState(LoadingState)
            }

            recalculatePathsAndUpdateDbAndUi(modelInst)
        }
    }

    suspend fun updateMaxMovesSelection(maxMoves: Int) {
        withContext(calcDispatcher) {
            val modelInst = model.value ?: return@withContext
            modelInst.maxMoves = maxMoves

            if (modelInst.sourceNode != null && modelInst.destinationNode != null) {
                refreshUiState(LoadingState)
            }

            recalculatePathsAndUpdateDbAndUi(modelInst)
        }
    }

    suspend fun highlightPath(path: Path) {
        withContext(calcDispatcher) {
            val modelInst = model.value ?: return@withContext
            modelInst.highlightPath = path
            saveAndRefresh(modelInst)
            refreshUiState(ContentState)
            modelInst.updateDb()
        }
    }

    private suspend fun saveAndRefresh(modelInst: KnightDataModel) {
        withContext(Dispatchers.Main) {
            ensureActive()
            setModel(modelInst)
            refreshData()
        }
    }

    private suspend fun refreshUiState(uiState: UiState) {
        withContext(Dispatchers.Main) {
            _uiState.value = uiState
        }
    }

    private suspend fun recalculatePathsAndUpdateDbAndUi(modelInst: KnightDataModel) {
        withContext(calcDispatcher) {
            modelInst.clearPaths()
            modelInst.calculatePaths()
            saveAndRefresh(modelInst)
            refreshUiState(ContentState)
            modelInst.updateDb()
        }
    }

    private fun KnightDataModel.calculatePaths() {
        if (sourceNode != null && destinationNode != null && selectedBoardSize != null) {
            paths += pathCalculationUsecase.calculate(
                sourceNode!!,
                destinationNode!!,
                selectedBoardSize!!,
                maxMoves
            )
        }
    }

    private fun KnightDataModel.clearPaths() {
        paths.clear()
        highlightPath = null
    }

    private fun KnightDataModel.updateDb() {
        viewModelScope.launch(ioDispatcher) { knightDbRepo.updateKnightDataModel(this@updateDb) }
    }


    companion object {
        const val DEFAULT_MAX_MOVES = 3
    }
}