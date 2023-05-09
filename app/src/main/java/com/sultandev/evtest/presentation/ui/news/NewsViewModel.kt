package com.sultandev.evtest.presentation.ui.news

import android.annotation.SuppressLint
import com.sultandev.evtest.presentation.common.BaseViewModel
import com.sultandev.evtest.presentation.common.ViewAction
import com.sultandev.evtest.presentation.common.ViewIntent
import com.sultandev.evtest.presentation.common.ViewState
import com.sultandev.evtest.data.repository.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(private val newsRepository: NewsRepository) :
    BaseViewModel<HomeIntent, HomeAction, HomeState>() {

    override fun intentToAction(intent: HomeIntent): HomeAction {
        return when (intent) {
            is HomeIntent.LoadAllNews -> HomeAction.AllNews
        }
    }

    @SuppressLint("CheckResult")
    override fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.AllNews -> {
                newsRepository.getNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { mState.postValue(HomeState.LoadingState) }
                    .subscribe({ news ->
                        mState.postValue(HomeState.LoadedState(news))
                    }, { error ->
                        HomeState.ErrorState(error.message ?: "Unknown error")
                    })
            }
        }
    }
}

sealed class HomeIntent : ViewIntent {
    object LoadAllNews : HomeIntent()
}

sealed class HomeAction : ViewAction {
    object AllNews : HomeAction()
}

sealed class HomeState : ViewState {
    object LoadingState : HomeState()
    class LoadedState<T>(val data: T) : HomeState()
    class ErrorState(val message: String) : HomeState()
}