package com.sultandev.evtest.presentation.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.evtest.R
import com.sultandev.evtest.presentation.adapter.PhotosAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sultandev.evtest.data.model.News
import com.sultandev.evtest.databinding.ScreenNewsBinding
import com.sultandev.evtest.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class NewsScreen : Fragment(R.layout.screen_news) {

    private val binding: ScreenNewsBinding by viewBinding()
    private val adapter: PhotosAdapter by lazy(LazyThreadSafetyMode.NONE) { PhotosAdapter() }
    private val viewModel: NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRv.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.LoadingState -> {
                    setLoading(true)
                }
                is HomeState.LoadedState<*> -> {
                    setLoading(false)
                    val data = state.data as News
                    adapter.submitList(data.articles)
                }

                is HomeState.ErrorState -> {
                    setLoading(false)
                    toast(requireContext(), state.message)
                }
            }
        }

        adapter.setOnClickListener { url ->
            findNavController().navigate(NewsScreenDirections.actionScreenNewsToWebViewScreen(url))
        }

        viewModel.dispatchIntent(HomeIntent.LoadAllNews)

    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            loading.isVisible = isLoading
            mainRv.isVisible = !isLoading
        }
    }
}