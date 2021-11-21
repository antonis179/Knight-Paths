package com.example.knight.features.knightmovement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.knight.R
import com.example.knight.databinding.FragmentKnightBinding
import com.example.knight.ui.BaseFragment
import com.example.knight.ui.UiState
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.adapters.KnightAdapter
import com.example.knight.ui.LoadingState
import com.example.knight.utils.Do
import com.example.knight.utils.gone
import com.example.knight.utils.viewBinding
import com.example.knight.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class KnightFragment : BaseFragment(R.layout.fragment_knight) {

    private val binding by viewBinding(FragmentKnightBinding::bind)

    private val viewModel: KnightViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Do.safe({ viewModel.loadData() }, { Timber.e(it) })
            }
        }
    }

    private fun setupViewModel() {
        viewModel.data.observe(viewLifecycleOwner, { refreshUI(it) })
        viewModel.uiState.observe(viewLifecycleOwner, { refreshState(it) })
    }

    private fun setupUI() {
        val adapter = KnightAdapter(
            { boardSizeSelection ->
                lifecycleScope.launch {
                    Do.safe({ viewModel.updateBoardSizeSelection(boardSizeSelection) }, { Timber.e(it) })
                }
            },
            { maxMoves ->
                lifecycleScope.launch {
                    Do.safe({ viewModel.updateMaxMovesSelection(maxMoves) }, { Timber.e(it) })
                }
            },
            { sourceNode, destinationNode ->
                lifecycleScope.launch {
                    Do.safe({ viewModel.updateBoardSelection(sourceNode, destinationNode) }, { Timber.e(it) })
                }
            },
            { path ->
                lifecycleScope.launch {
                    Do.safe({ viewModel.highlightPath(path) }, { Timber.e(it) })
                }
            }
        )
        
        binding.rvView.adapter = adapter
    }

    private fun refreshState(state: UiState) {
        with(binding.rlLoading.root) { if (LoadingState == state) visible() else gone() }
    }

    private fun refreshUI(items: List<Data>) {
        val adapter = binding.rvView.adapter as KnightAdapter?
        adapter?.let { it.items = items }
    }

    companion object {
        fun newInstance() = KnightFragment()
    }


}