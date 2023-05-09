package com.sultandev.evtest.data.repository.impl

import android.annotation.SuppressLint
import android.util.Log
import com.sultandev.evtest.data.api.NewsApi
import com.sultandev.evtest.data.repository.NewsRepository
import com.sultandev.evtest.data.model.News
import com.sultandev.evtest.utils.Const
import com.sultandev.evtest.utils.getDate
import io.reactivex.rxjava3.core.Single

class NewsRepositoryImpl(private val photosApi: NewsApi) : NewsRepository {

    @SuppressLint("CheckResult")
    override fun getNews(): Single<News> {
        val date = getDate()
        val key = Const.API_KEY
        return photosApi.getNews(date = date, key = key)
    }
}